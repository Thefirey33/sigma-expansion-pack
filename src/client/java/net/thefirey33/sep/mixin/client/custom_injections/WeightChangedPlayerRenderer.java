package net.thefirey33.sep.mixin.client.custom_injections;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.SepGlobalConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class WeightChangedPlayerRenderer {

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void renderInjection(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci){
        NbtCompound nbtCompound = new NbtCompound();
        abstractClientPlayerEntity.writeCustomDataToNbt(nbtCompound);

        float weightValue = nbtCompound.getFloat(SepGlobalConstants.WEIGHT_NBT_IDENTIFIER);
        SepGlobalConstants.LOGGER.info(String.valueOf(weightValue));
    }
}
