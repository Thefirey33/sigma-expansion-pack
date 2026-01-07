package net.thefirey33.sep.mixin.client.custom_injections;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.QuickPlayLogger;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.report.ReporterEnvironment;
import net.minecraft.client.util.Session;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourceReload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.QueueingWorldGenerationProgressListener;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;
import net.minecraft.util.ApiServices;
import net.minecraft.util.UserCache;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.level.storage.LevelStorage;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.client.SepClient;
import net.thefirey33.sep.client.screens.DummyScreen;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@Mixin(MinecraftClient.class)
public abstract class GenerateWorldFirstMixin {
    @Shadow
    @Final
    public File runDirectory;
    @Shadow
    private @Nullable IntegratedServer server;
    @Shadow
    private @Nullable ClientConnection integratedServerConnection;
    @Shadow
    private @Nullable Supplier<CrashReport> crashReportSupplier;
    @Shadow
    private Profiler profiler;
    @Shadow
    private boolean integratedServerRunning;
    @Shadow
    @Final
    private QuickPlayLogger quickPlayLogger;
    @Shadow
    @Final
    private AtomicReference<WorldGenerationProgressTracker> worldGenProgressTracker;
    @Shadow
    @Final
    private Queue<Runnable> renderTaskQueue;
    @Shadow
    @Final
    private YggdrasilAuthenticationService authenticationService;


    @Shadow
    protected abstract void render(boolean tick);

    @Shadow
    public abstract void ensureAbuseReportContext(ReporterEnvironment environment);

    @Shadow
    public abstract void disconnect();

    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    @Shadow
    public abstract LevelStorage getLevelStorage();

    @Inject(at = @At(value = "HEAD"), method = "onInitFinished", cancellable = true)
    public void onInitFinishedInjection(RealmsClient realms, ResourceReload reload, RunArgs.QuickPlay quickPlay, CallbackInfo ci) {
        if (!SepClient.IS_DEVELOPMENT) {
            LevelStorage levelStorage = this.getLevelStorage();

            levelStorage.getLevelList().forEach(levelSave -> {
                try {
                    Path path = levelSave.path();
                    Sep.LOGGER.info("DELETING WORLD, CUZ ONLY ONESHOT: {}", path);
                    FileUtils.deleteDirectory(path.toFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            CreateWorldScreen.create(MinecraftClient.getInstance(), new DummyScreen(Text.of("DUMMY")));
            ci.cancel();
        }
    }

    /**
     * @author Thefirey33
     * @reason New weirder world generator.
     */
    @Inject(at = @At("HEAD"), method = "startIntegratedServer", cancellable = true)
    public void startIntegratedServer(String levelName, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, boolean newWorld, CallbackInfo ci) {
        /*
          This is the integrated server started,
          slightly modified for use in the IMMEDIATE WORLD GENERATOR purposes.

          THEFIREY33
         */


        if (!SepClient.IS_DEVELOPMENT) {
            this.disconnect();
            this.setScreen(new TitleScreen(false));
            this.worldGenProgressTracker.set(null);
            MinecraftClient currentClient = (MinecraftClient) (Object) this;
            try {
                session.backupLevelDataFile(saveLoader.combinedDynamicRegistries().getCombinedRegistryManager(), saveLoader.saveProperties());
                ApiServices apiServices = ApiServices.create(this.authenticationService, this.runDirectory);
                apiServices.userCache().setExecutor(currentClient);
                SkullBlockEntity.setServices(apiServices, currentClient);
                UserCache.setUseRemote(false);
                this.server = MinecraftServer.startServer((thread) -> new IntegratedServer(thread, currentClient, session, dataPackManager, saveLoader, apiServices, (spawnChunkRadius) -> {
                    WorldGenerationProgressTracker worldGenerationProgressTracker = new WorldGenerationProgressTracker(spawnChunkRadius);
                    this.worldGenProgressTracker.set(worldGenerationProgressTracker);
                    Queue<Runnable> var10001 = this.renderTaskQueue;
                    Objects.requireNonNull(var10001);
                    return QueueingWorldGenerationProgressListener.create(worldGenerationProgressTracker, var10001::add);
                }));
                this.integratedServerRunning = true;
                this.ensureAbuseReportContext(ReporterEnvironment.ofIntegratedServer());
                this.quickPlayLogger.setWorld(QuickPlayLogger.WorldType.SINGLEPLAYER, levelName, saveLoader.saveProperties().getLevelName());
            } catch (Throwable var12) {
                CrashReport crashReport = CrashReport.create(var12, "Starting integrated server");
                CrashReportSection crashReportSection = crashReport.addElement("Starting integrated server");
                crashReportSection.add("Level ID", levelName);
                crashReportSection.add("Level Name", () -> saveLoader.saveProperties().getLevelName());
                throw new CrashException(crashReport);
            }

            while (this.worldGenProgressTracker.get() == null) {
                Thread.yield();
            }
            this.profiler.push("waitForServer");
            while (true) {
                assert this.server != null;
                if (this.server.isLoading()) break;
                this.render(false);

                try {
                    Thread.sleep(16L);
                } catch (InterruptedException var11) {
                }

                if (this.crashReportSupplier != null) {
                    return;
                }
            }
            this.profiler.pop();
            assert this.server.getNetworkIo() != null;
            SepClient.SERVER_ADDRESS = this.server.getNetworkIo().bindLocal();
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void ConnectToCreatedServer(boolean tick, CallbackInfo ci) {
        if (SepClient.CONNECT_TO_CREATED_SERVER && !SepClient.IS_DEVELOPMENT) {
            // Tell the client to connect to the integrated server.
            MinecraftClient client = MinecraftClient.getInstance();
            ClientConnection clientConnection = ClientConnection.connectLocal(SepClient.SERVER_ADDRESS);
            clientConnection.setPacketListener(new ClientLoginNetworkHandler(clientConnection, client, null, null, true, Duration.ZERO, (status) -> {
            }));
            clientConnection.send(new HandshakeC2SPacket(SepClient.SERVER_ADDRESS.toString(), 0, NetworkState.LOGIN));
            clientConnection.send(new LoginHelloC2SPacket(client.getSession().getUsername(), Optional.ofNullable(client.getSession().getUuidOrNull())));
            this.integratedServerConnection = clientConnection;
            SepClient.CONNECT_TO_CREATED_SERVER = false;
            SepClient.ALREADY_WORLD_GENERATED = true;
        }
    }

}
