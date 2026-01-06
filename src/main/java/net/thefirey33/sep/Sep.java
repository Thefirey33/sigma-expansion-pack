package net.thefirey33.sep;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sep implements ModInitializer {
    public static final String SEP_MOD_ID = "sep";
    public static final Logger LOGGER = LoggerFactory.getLogger(Sep.class);
    public static final RegistryKey<PlacedFeature> SIGMA_ORE_FEATURE_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("sep", "sigma_ore_gen"));

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SIGMA_ORE_FEATURE_KEY);
    }
}
