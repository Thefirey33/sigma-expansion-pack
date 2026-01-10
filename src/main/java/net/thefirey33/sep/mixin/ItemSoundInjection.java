package net.thefirey33.sep.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.SoundHelper;
import net.thefirey33.sep.registries.ModSounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


public class ItemSoundInjection {

    @Mixin(HoeItem.class)
    public static class HoeItemInjection extends MiningToolItem {
        public HoeItemInjection(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
            super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
        }

        @Inject(at = @At("RETURN"), method = "useOnBlock")
        public void onUseOnBlockInjection(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
            if (cir.getReturnValue() != ActionResult.PASS)
                SoundHelper.PlaySoundAtWorld(this.getMaterial(), context.getPlayer(), context.getWorld());
        }
    }

}
