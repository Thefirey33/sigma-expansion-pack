package net.thefirey33.sep.client;

import net.fabricmc.api.ClientModInitializer;
import net.thefirey33.sep.client.dialogue_loaders.BeginningGasterLoader;

public class SepClient implements ClientModInitializer {
    public static long WINDOW_HANDLE;

    /*
        ATTENTION DEVELOPERS!
        After you are done developing and are ready for a release, or to test normal user features,
        turn this flag back OFF!!!
        DO NOT FORGET!
     */
    public static final Boolean IS_DEVELOPMENT = false;

    @Override
    public void onInitializeClient() {

    }
}
