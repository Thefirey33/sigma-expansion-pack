package net.thefirey33.sep.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.networking.NetworkingRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class IsPlayerHighUpInjection {
    @Unique
    private static final Integer SONG_PLAY_Y_LIMIT = 100;
    @Unique
    private static final Integer RELAXATION_TIME_IN_SECONDS = 200; // IN TICKS

    @Unique
    public int RelaxationTimer = 0;

    @Unique
    public boolean IsCurrentlyRelaxing = false;

    @Unique
    public void SendPacket(ServerPlayerEntity serverPlayerEntity, Boolean valueToSet) {
        assert NetworkingRegistries.START_PLAY_MUSIC_PACKET != null;
        PacketByteBuf packetByteBuf = PacketByteBufs.create();
        Sep.LOGGER.info("Making this biatch relaxed: {}.. SENDING PACKET", serverPlayerEntity.getName());
        packetByteBuf.writeBoolean(valueToSet);
        ServerPlayNetworking.send(serverPlayerEntity, NetworkingRegistries.START_PLAY_MUSIC_PACKET, packetByteBuf);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    public void tickInjection(CallbackInfo ci){
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) (Object)this;
        // If the Y position is over it, tell the client to start playing the song.
        if (serverPlayerEntity.getY() < SONG_PLAY_Y_LIMIT){
            if (IsCurrentlyRelaxing) {
                SendPacket(serverPlayerEntity, false);
                IsCurrentlyRelaxing = false;
            }
            return;
        }
        RelaxationTimer++;
        if (RelaxationTimer > RELAXATION_TIME_IN_SECONDS && !IsCurrentlyRelaxing) {
            // Create a packet bytebuffer so we can tell the client to start playing the music.
            SendPacket(serverPlayerEntity, true);
            RelaxationTimer = 0;
            IsCurrentlyRelaxing = true;
        }
    }
}
