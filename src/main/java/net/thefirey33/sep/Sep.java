package net.thefirey33.sep;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.thefirey33.sep.other_controllers.DiamondIdeaController;
import net.thefirey33.sep.registries.ModBlocks;
import net.thefirey33.sep.registries.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Text;

import java.io.InputStream;

public class Sep implements ModInitializer {
    public static final String SEP_MOD_ID = "sep";
    /**
     * Mod logger.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(Sep.class);
    /**
     * Sigma Ore generation key.
     */
    public static final RegistryKey<PlacedFeature> SIGMA_ORE_FEATURE_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("sep", "sigma_ore_gen"));
    /**
     * When someone holds the sigma block, when they "sigma", it gives them diamonds with a rate-limit.
     */
    public static final DiamondIdeaController DIAMOND_IDEA_CONTROLLER;
    /**
     * This is the identifier for the testing texture.
     */
    public static final Identifier TEXTURE_TEST_IDENTIFIER = Identifier.of(SEP_MOD_ID, "texture_test.png");

    @Override
    public void onInitialize() {
        /*
            "Why is C# better than Java?"
            By Thefirey33

            We all know that C# is always called Microsoft Java,
            but is it really?

            C# has always improved from Java, even more so...
            And Oracle, on their lazy asses, haven't done anything to improve Java.
            Why do we need AtomicReferences everywhere?
            Why can't switch statements have type checks?
            Why can't FUCKING public static void Main(string[] args) be destroyed?

            That's why,
            we hate Java.

            ~ thefirey33
         */
        ModItems.initialize();
        ModBlocks.initialize();
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SIGMA_ORE_FEATURE_KEY);
        DIAMOND_IDEA_CONTROLLER.RegisterModController();
    }

    static {
        DIAMOND_IDEA_CONTROLLER = new DiamondIdeaController();
    }
}
