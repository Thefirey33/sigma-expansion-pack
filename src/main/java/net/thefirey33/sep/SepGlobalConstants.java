package net.thefirey33.sep;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SepGlobalConstants {
    /**
     * Weight NBT Identifier.
     */
    public static final String WEIGHT_NBT_IDENTIFIER = "Weight";
    /**
     * The MOD ID.
     */
    public static final String SEP_MOD_ID = "sep";
    /**
     * This is the identifier for the testing texture.
     */
    public static final Identifier TEXTURE_TEST_IDENTIFIER = Identifier.of(SEP_MOD_ID, "texture_test.png");
    /**
     * The packet identified to start playing the music.
     */
    public static final Identifier START_PLAY_MUSIC_PACKET = Identifier.of(SEP_MOD_ID, "start_play_music_packet");
    /**
     * Mod logger.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(Sep.class);
    /**
     * Sigma Ore generation key.
     */
    public static final RegistryKey<PlacedFeature> SIGMA_ORE_FEATURE_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("sep", "sigma_ore_gen"));


}
