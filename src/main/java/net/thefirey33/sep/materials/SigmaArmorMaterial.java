/**
 * IDEA By: nikodev
 * Sigma ARMOR / TOOLS
 */


package net.thefirey33.sep.materials;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.thefirey33.sep.registries.ModItems;

public class SigmaArmorMaterial implements ArmorMaterial {
    public static final SigmaArmorMaterial INSTANCE = new SigmaArmorMaterial();

    @Override
    public int getDurability(ArmorItem.Type type) {
        int DURABILITY_MULTIPLIER = 10;
        return switch (type) {
            case BOOTS -> 13 * DURABILITY_MULTIPLIER;
            case LEGGINGS -> 15 * DURABILITY_MULTIPLIER;
            case CHESTPLATE -> 16 * DURABILITY_MULTIPLIER;
            case HELMET -> 11 * DURABILITY_MULTIPLIER;
        };
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return switch (type) {
            case BOOTS, HELMET -> 3;
            case LEGGINGS -> 6;
            case CHESTPLATE -> 8;
        };
    }

    @Override
    public int getEnchantability() {
        return 6;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.SIGMA_INGOT);
    }

    @Override
    public String getName() {
        return "sigma";
    }

    @Override
    public float getToughness() {
        return 2.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.5F;
    }
}
