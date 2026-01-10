package net.thefirey33.sep.client.network_manager;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.sound.SoundManager;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.client.SoundInstances;
import net.thefirey33.sep.networking.NetworkingRegistries;

public class NetworkEventHandler {

    public static Boolean PLAY_RELAXING_SONG = false;

    public static void RegisterRelaxingSong() {
        assert NetworkingRegistries.START_PLAY_MUSIC_PACKET != null;
        ClientPlayNetworking.registerGlobalReceiver(NetworkingRegistries.START_PLAY_MUSIC_PACKET, (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> {
            // If the server has sent a request to play the relaxing song to client, then do it.
            // Please JAVA, I NEED THIS
            // I NEED TO PLAY RELAXING MUSIC
            Sep.LOGGER.info("Relaxing music packet received");
            PLAY_RELAXING_SONG = packetByteBuf.readBoolean();
            SoundManager soundManager = minecraftClient.getSoundManager();

            if (PLAY_RELAXING_SONG){
                minecraftClient.getMusicTracker().stop();
                minecraftClient.getMusicTracker().play(SoundInstances.RELAXING_SONG_MUSIC_SOUND);
            }
            else
                minecraftClient.getMusicTracker().stop();
        });
    }

    /**
     * Register all the network handlers.
     */
    public static void Register() {
        RegisterRelaxingSong();
    }
}
