package net.thefirey33.sep.client;

import net.fabricmc.api.ClientModInitializer;

import java.net.SocketAddress;

public class SepClient implements ClientModInitializer {
    /**
     * ATTENTION DEVELOPERS!
     * After you are done developing and are ready for a release, or to test normal user features,
     * turn this flag back OFF!!!
     * DO NOT FORGET!
     */
    public static final Boolean IS_DEVELOPMENT = false;
    public static long WINDOW_HANDLE;
    /**
     * The server address to connect to (IntegratedServer).
     */
    public static SocketAddress SERVER_ADDRESS;
    /**
     * If the client should start connecting to the server immediately?
     * (This has been done as there's no way to call functions to mixins.)
     */
    public static Boolean CONNECT_TO_CREATED_SERVER = false;
    /**
     * If the world was already generated, if it was and the user is trying to force a new world,
     * even though it's ONESHOT only.
     */
    public static Boolean ALREADY_WORLD_GENERATED = false;

    @Override
    public void onInitializeClient() {

    }

}
