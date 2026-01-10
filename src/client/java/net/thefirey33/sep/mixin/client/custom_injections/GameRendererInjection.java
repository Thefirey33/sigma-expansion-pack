package net.thefirey33.sep.mixin.client.custom_injections;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Colors;
import net.minecraft.util.math.MathHelper;
import net.thefirey33.sep.client.HelperFunctions;
import net.thefirey33.sep.client.SepClient;
import net.thefirey33.sep.client.flowey.FloweyBossFightScreen;
import net.thefirey33.sep.client.network_manager.NetworkEventHandler;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(GameRenderer.class)
public abstract class GameRendererInjection {


    @Shadow
    @Final
    MinecraftClient client;

    @Shadow
    @Final
    private BufferBuilderStorage buffers;

    @Unique
    @Final
    private static final Integer BLACK_BAR_HEIGHT = 20;

    @Unique
    private static float BLACK_BAR_POP_IN = 0.0F;

    // I DON'T GIVE A SHIT
    // IT'S CALLED LERP
    // SO SHUT IT
    @SuppressWarnings("SpellCheckingInspection")
    @Unique
    private static final float LERP_MULTI = 0.05F;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/toast/ToastManager;draw(Lnet/minecraft/client/gui/DrawContext;)V"), method = "render")
    public void renderInjection(float tickDelta, long startTime, boolean tick, CallbackInfo ci, @Local DrawContext drawContext){
        // Developer tool for setting the flowey boss fight screen.
        if (HelperFunctions.isKeyPressed(GLFW.GLFW_KEY_F1) && SepClient.IS_DEVELOPMENT) {
            this.client.setScreen(new FloweyBossFightScreen());
        }

        BLACK_BAR_POP_IN = MathHelper.lerp(client.getLastFrameDuration() * LERP_MULTI, BLACK_BAR_POP_IN, NetworkEventHandler.PLAY_RELAXING_SONG ? BLACK_BAR_HEIGHT : 0);
        if (NetworkEventHandler.PLAY_RELAXING_SONG)
        {
            Window window = this.client.getWindow();
            int bottomPositionY = window.getScaledHeight();
            drawContext.fill(0, 0, window.getScaledWidth(), (int) BLACK_BAR_POP_IN, Colors.BLACK);
            drawContext.fill(0, bottomPositionY, window.getScaledWidth(), bottomPositionY - (int) BLACK_BAR_POP_IN, Colors.BLACK);
        }
    }

    @Mixin(WorldRenderer.class)
    public static class SaturationEffect {
        @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk;getOrigin()Lnet/minecraft/util/math/BlockPos;"), method = "renderLayer")
        public void renderLayerInjection(RenderLayer renderLayer, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f positionMatrix, CallbackInfo ci, @Local ShaderProgram shaderProgram){
            /*
                TODO:
                Saturate the game.
                It doesn't work.
                Fuck GLSL.
             */
        }
    }
}
