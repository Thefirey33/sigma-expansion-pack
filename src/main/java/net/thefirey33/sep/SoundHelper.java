package net.thefirey33.sep;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thefirey33.sep.registries.ModSounds;

public class SoundHelper {
    public static void PlaySoundAtWorld(ToolMaterial material, LivingEntity player, World world) {
        if (material == ToolMaterials.WOOD) {
            // Play the sound at the specified sound location.
            BlockPos blockPos = player.getBlockPos();
            world.playSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), ModSounds.TUNG_HIT, SoundCategory.NEUTRAL, 1.0f, 1.0f, true);
        }
    }
}
