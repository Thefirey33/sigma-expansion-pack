package net.thefirey33.sep.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.thefirey33.sep.SoundHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(HoeItem.class)
public class HoeItemInjection extends MiningToolItem {
    public HoeItemInjection(float attackDamage, float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Inject(at = @At("RETURN"), method = "useOnBlock")
    public void onUseOnBlockInjection(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
        if (cir.getReturnValue() != ActionResult.PASS)
            SoundHelper.PlaySoundAtWorld(this.getMaterial(), context.getPlayer(), context.getWorld());
    }
}