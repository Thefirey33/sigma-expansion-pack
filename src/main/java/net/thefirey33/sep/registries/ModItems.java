package net.thefirey33.sep.registries;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.materials.SigmaArmorMaterial;
import net.thefirey33.sep.materials.SigmaMaterial;

public class ModItems {
    private static final FoodComponent SUSPICIOUS_FOOD_COMPONENT = new FoodComponent.Builder().snack().statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60 * 20), 1.0f).build();

    public static final Item SUSPICIOUS_SUBSTANCE = register(
            // Ignore the food component for now, we'll cover it later in the food section.
            new Item(new FabricItemSettings().food(SUSPICIOUS_FOOD_COMPONENT)), "suspicious_substance");

    public static final Item RAW_SIGMA = register(new Item(new FabricItemSettings().rarity(Rarity.COMMON)), "raw_sigma");

    public static final Item SIGMA_INGOT = register(new Item(new FabricItemSettings().rarity(Rarity.COMMON)), "sigma_ingot");

    public static final Item SIGMA_AXE = register(new AxeItem(SigmaMaterial.INSTANCE, 2, 0.5F, new FabricItemSettings()), "sigma_axe");

    public static final Item SIGMA_SWORD = register(new SwordItem(SigmaMaterial.INSTANCE, 4, 0.1F, new FabricItemSettings()), "sigma_sword");

    public static final Item SIGMA_SHOVEL = register(new ShovelItem(SigmaMaterial.INSTANCE, 1, 0.5F, new FabricItemSettings()), "sigma_shovel");

    public static final Item SIGMA_HOE = register(new HoeItem(SigmaMaterial.INSTANCE, 1, 0.5F, new FabricItemSettings()), "sigma_hoe");

    public static final Item SIGMA_PICKAXE = register(new PickaxeItem(SigmaMaterial.INSTANCE, 2, 0.25F, new FabricItemSettings()), "sigma_pickaxe");

    public static final Item SIGMA_HELMET = register(new ArmorItem(SigmaArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new Item.Settings()), "sigma_helmet");
    public static final Item SIGMA_BOOTS= register(new ArmorItem(SigmaArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new Item.Settings()), "sigma_boots");
    public static final Item SIGMA_LEGGINGS = register(new ArmorItem(SigmaArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new Item.Settings()), "sigma_leggings");
    public static final Item SIGMA_CHESTPLATE = register(new ArmorItem(SigmaArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new Item.Settings()), "sigma_chestplate");

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(Sep.SEP_MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((itemGroup) -> {
            itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE);
            itemGroup.add(RAW_SIGMA);
            itemGroup.add(SIGMA_INGOT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> {
            itemGroup.add(SIGMA_AXE);
            itemGroup.add(SIGMA_SHOVEL);
            itemGroup.add(SIGMA_HOE);
            itemGroup.add(SIGMA_PICKAXE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
            itemGroup.add(SIGMA_SWORD);
            itemGroup.add(SIGMA_HELMET);
            itemGroup.add(SIGMA_CHESTPLATE);
            itemGroup.add(SIGMA_LEGGINGS);
            itemGroup.add(SIGMA_BOOTS);
        });

        FuelRegistry.INSTANCE.add(SUSPICIOUS_SUBSTANCE, 60 * 20);
    }
}
