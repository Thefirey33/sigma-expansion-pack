package net.thefirey33.sep;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    private static final FoodComponent SUSPICIOUS_FOOD_COMPONENT = new FoodComponent.Builder()
            .snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60 * 20), 1.0f)
            .build();

    public static final Item SUSPICIOUS_SUBSTANCE = register(
            // Ignore the food component for now, we'll cover it later in the food section.
            new Item(new FabricItemSettings().food(SUSPICIOUS_FOOD_COMPONENT)),
            "suspicious_substance"
    );

    public static final Item RAW_SIGMA = register(
            new Item(new FabricItemSettings().rarity(Rarity.COMMON)), "raw_sigma"
    );

    public static final Item SIGMA_INGOT = register(
            new Item(new FabricItemSettings().rarity(Rarity.COMMON)), "sigma_ingot"
    );

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(Sep.SEP_MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    };

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> {
                    itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE);
                    itemGroup.add(RAW_SIGMA);
                    itemGroup.add(SIGMA_INGOT);
                });

        FuelRegistry.INSTANCE.add(SUSPICIOUS_SUBSTANCE, 60 * 20);
    }
}
