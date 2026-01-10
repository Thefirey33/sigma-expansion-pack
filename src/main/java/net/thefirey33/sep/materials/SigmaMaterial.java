/**
 * IDEA By: nikodev
 * Sigma ARMOR / TOOLS
 */


package net.thefirey33.sep.materials;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.thefirey33.sep.registries.ModItems;

public class SigmaMaterial implements ToolMaterial {
    public static final SigmaMaterial INSTANCE = new SigmaMaterial();

    @Override
    public int getDurability() {
        return 300;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 20.0F;
    }

    @Override
    public float getAttackDamage() {
        return 3.0F;
    }

    @Override
    public int getMiningLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.SIGMA_INGOT);
    }
}
