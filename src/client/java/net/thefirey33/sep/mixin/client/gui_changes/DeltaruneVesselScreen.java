/**
 * IDEA By: THEFIREY33
 * The DELTARUNE Parody Vessel Select Screen.
 */


package net.thefirey33.sep.mixin.client.gui_changes;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.util.Window;
import net.minecraft.sound.MusicSound;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.SepRegistries;
import net.thefirey33.sep.client.SepClient;
import net.thefirey33.sep.client.vessel_screen_dialogue_manager.VesselSelectScreenDialogueManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(TitleScreen.class)
public class DeltaruneVesselScreen extends Screen {

    @Unique
    @Final
    private static final Identifier BEGINNING_IMAGE_DEPTH = Identifier.of(Sep.SEP_MOD_ID, "textures/gui/beginning/image_depth.png");
    @Unique
    @Final
    private static final Float FINAL_ZOOM = 400.0f;
    @Unique
    @Final
    private static final Float ZOOM_SENSITIVITY = 50.0F;
    @Unique
    @Final
    private static final Float SHADER_MAX_COLOR = 0.5F;
    @Unique
    @Final
    private static final Integer MAXIMUM_PERMITTED_BACKGROUNDS = 50;
    @Unique
    private static final Window CURRENT_WINDOW_INSTANCE = MinecraftClient.getInstance().getWindow();
    @Unique
    private static ArrayList<Float> RepeatingBackgroundEffect = new ArrayList<>(List.of(0.0F));
    @Unique
    private static Float TICKS = 0.0F;


    protected DeltaruneVesselScreen(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    public void init(CallbackInfo info) {
        Sep.LOGGER.info("Overriding Normal Titlescreen!");
        info.cancel();
    }

    @Override
    public @Nullable MusicSound getMusic() {
        return SepRegistries.WING_DING_GASTER_MM_MUSIC;
    }

    @Unique
    private void renderBlackBackground(DrawContext drawContext) {
        drawContext.fill(0, 0, CURRENT_WINDOW_INSTANCE.getWidth(), CURRENT_WINDOW_INSTANCE.getHeight(), Colors.BLACK);
    }

    @Unique
    private void renderRepeatingEffectBackground(DrawContext drawContext, float delta) {
        // Start the blending feature.
        RenderSystem.enableBlend();
        RenderSystem.assertOnRenderThread();
        for (int i = 0; i < RepeatingBackgroundEffect.size(); i++) {
            float aFloat = RepeatingBackgroundEffect.get(i);
            float calc = (aFloat - FINAL_ZOOM) / FINAL_ZOOM;
            int iCalc = (int) (calc * ZOOM_SENSITIVITY);
            int iCalc2 = Math.multiplyExact(iCalc, 2);
            RenderSystem.setShaderColor(SHADER_MAX_COLOR, SHADER_MAX_COLOR, SHADER_MAX_COLOR, Math.min(0.8F, calc));
            drawContext.drawTexture(BEGINNING_IMAGE_DEPTH,
                    -iCalc,
                    -iCalc,
                    0,
                    0,
                    this.width + iCalc2,
                    this.height + iCalc2,
                    this.width + iCalc2,
                    this.height + iCalc2);
            RepeatingBackgroundEffect.set(i, aFloat + delta * 5);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
        if (RepeatingBackgroundEffect.size() > MAXIMUM_PERMITTED_BACKGROUNDS)
            RepeatingBackgroundEffect.remove(0);

        TICKS += delta;
        if (TICKS > 30) {
            RepeatingBackgroundEffect.add(0.0F);
            TICKS = 0.0F;
        }
        // Start drawing that repeating background effect we see in deltarune.
        RenderSystem.disableBlend();
    }

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.renderBlackBackground(drawContext);
        this.renderRepeatingEffectBackground(drawContext, delta);
        // If the dialogue is over, then display the select.
        assert this.client != null;
        if (VesselSelectScreenDialogueManager.DrawDialogueToScreen(textRenderer, this, drawContext, delta) || SepClient.IS_DEVELOPMENT) {
            assert this.client != null;
            this.client.setScreen(new SelectWorldScreen(this));
        }
        ci.cancel();
    }
}
