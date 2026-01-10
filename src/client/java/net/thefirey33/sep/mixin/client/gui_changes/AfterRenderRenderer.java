package net.thefirey33.sep.mixin.client.gui_changes;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.client.SepClient;
import net.thefirey33.sep.client.SoundInstances;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class AfterRenderRenderer {

    @Shadow
    @Final
    MinecraftClient client;

    @Unique
    private static final Identifier EYE_OF_RAH = Identifier.of(Sep.SEP_MOD_ID, "textures/gui/eye_of_rah.png");

    @Unique
    private static final Integer SHAKE_AMOUNT = 5;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/toast/ToastManager;draw(Lnet/minecraft/client/gui/DrawContext;)V"), method = "render")
    public void renderInjection(float tickDelta, long startTime, boolean tick, CallbackInfo ci, @Local DrawContext drawContext) {
        MatrixStack matrixStack = drawContext.getMatrices();
        matrixStack.translate(0.0F, 0.0F, 400.0F);
        if (SepClient.IS_DEVELOPMENT) {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            Text textContainer = Text.translatable("gui.sep.is_development");
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
            drawContext.fill(0, 0, textRenderer.getWidth(textContainer), textRenderer.fontHeight, Colors.BLACK);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            drawContext.drawText(textRenderer, textContainer, 0, 0, Colors.RED, true);
        }

        SoundManager soundManager = this.client.getSoundManager();
        Window window = this.client.getWindow();
        int widthScreen = window.getScaledWidth(), heightScreen = window.getScaledHeight();
        if (soundManager.isPlaying(SoundInstances.PROPERTY_OF_EGYPT_INSTANCE))
        {
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
            drawContext.drawTexture(EYE_OF_RAH, 0, 0, widthScreen, heightScreen, (float) Math.random() * SHAKE_AMOUNT, (float) Math.random() * SHAKE_AMOUNT, widthScreen, heightScreen, widthScreen, heightScreen);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
        }
        matrixStack.loadIdentity();
    }
}
