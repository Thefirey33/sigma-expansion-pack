package net.thefirey33.sep;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import net.thefirey33.sep.other_controllers.DiamondIdeaController;
import net.thefirey33.sep.registries.ModBlocks;
import net.thefirey33.sep.registries.ModItems;

public class Sep implements ModInitializer {
    /**
     * When someone holds the sigma block, when they "sigma", it gives them diamonds with a rate-limit.
     */
    public static final DiamondIdeaController DIAMOND_IDEA_CONTROLLER;

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
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SepGlobalConstants.SIGMA_ORE_FEATURE_KEY);
        DIAMOND_IDEA_CONTROLLER.RegisterModController();
    }

    static {
        DIAMOND_IDEA_CONTROLLER = new DiamondIdeaController();
    }
}
