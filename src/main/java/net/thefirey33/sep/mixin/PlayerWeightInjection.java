package net.thefirey33.sep.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.thefirey33.sep.SepGlobalConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerWeightInjection {
    @Unique
    public float Weight;


    @Unique
    public final Integer WEIGHT_REMOVAL_MULTIPLIER = 1000;

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    public void readDataFromNbtInjection(NbtCompound nbt, CallbackInfo ci){
        this.Weight = nbt.getFloat(SepGlobalConstants.WEIGHT_NBT_IDENTIFIER);
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    public void writeDataFromNbtInjection(NbtCompound nbt, CallbackInfo ci){
        nbt.putFloat(SepGlobalConstants.WEIGHT_NBT_IDENTIFIER, this.Weight);
    }

    @Inject(at = @At("HEAD"), method = "eatFood")
    public void eatFoodInjection(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir){
        Item item = stack.getItem();

        if (item.isFood())
        {
            FoodComponent foodComponent = item.getFoodComponent();
            assert foodComponent != null;
            this.Weight += foodComponent.getHunger();
        }
    }

    @Inject(at = @At("TAIL"), method = "tick")
    public void tickInjection(CallbackInfo ci){
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        World world = playerEntity.getWorld();
        MinecraftServer server = world.getServer();

        if (server != null && Thread.currentThread() == server.getThread()) {
            this.Weight = Math.max(0, this.Weight - server.getTickTime() / WEIGHT_REMOVAL_MULTIPLIER);
            SepGlobalConstants.LOGGER.info(String.valueOf(this.Weight));
        }
    }

}
