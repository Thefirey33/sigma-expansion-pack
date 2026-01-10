package net.thefirey33.sep.mixin.client.gui_changes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.OrderedTextTooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.client.SepClient;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OrderedTextTooltipComponent.class)
public abstract class SixtySevenTooltipInjection {

    @Unique
    private static final String CHECK_CONTAIN = "sigma";
    @Unique
    private static final Integer LIGHT_VALUE = 15728880;
    @Unique
    private static final Integer SIN_VALUE = 2;
    @Unique
    private static Double TICKER = 0.0;
    @Shadow
    @Final
    private OrderedText text;

    @Unique
    private String ConvertOrderedTextToText() {
        StringBuilder textBuilder = new StringBuilder();
        this.text.accept((index, style, codePoint) -> {
            textBuilder.appendCodePoint(codePoint);
            return true;
        });
        return textBuilder.toString();
    }

    @Unique
    private boolean CheckIfTextContains() {
        String checkString = ConvertOrderedTextToText();
        return checkString.toLowerCase().contains(CHECK_CONTAIN);
    }

    @Unique
    private static final Identifier KID_67_IDENTIFIER = Identifier.of(Sep.SEP_MOD_ID, "textures/gui/kid_67.png");
    @Unique
    private static final Float OFFSET = 4000.0F;
    /**
     * @author Thefirey33
     * @reason Overwrite the renderer so I can mess with it
     */
    @Overwrite
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        if (!this.CheckIfTextContains())
            // Draw the text as usual if there's nothing to change.
            textRenderer.draw(this.text, (float) x, (float) y, -1, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, LIGHT_VALUE);
        else {
            // Loop-Over all the texts, and render one by one.
            String currentText = ConvertOrderedTextToText();
            char[] charArray = currentText.toCharArray();
            DrawContext drawContext = new DrawContext(MinecraftClient.getInstance(), vertexConsumers);
            // Matrix stack stuff.
            MatrixStack matrixStack = drawContext.getMatrices();
            matrixStack.translate(0.0F, 0.0F, OFFSET);
            // Get the width and height of the text.
            int width = textRenderer.getWidth(currentText),
                height = textRenderer.fontHeight;
            // Draw the 67 kid at the background.
            drawContext.drawTexture(KID_67_IDENTIFIER, x, y, 0, 0, width, height, width, height);
            int leftOffset = 0;
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                // Jump the character up and down.
                OrderedText orderedText = Text.of(String.valueOf(c)).asOrderedText();
                drawContext.drawText(textRenderer, orderedText, x + leftOffset, y + (int) (Math.sin(TICKER + (i * SIN_VALUE)) * SIN_VALUE), Colors.WHITE, true);
                leftOffset += textRenderer.getWidth(orderedText);
            }
            // Reload the matrixStack to normal.
            matrixStack.loadIdentity();
        }
        TICKER = MathHelper.floorMod((TICKER + SepClient.DELTA_TIME / 10), LIGHT_VALUE); // use the stupid light value, cause it's big as fuck
    }

    @Mixin(Screen.class)
    public static class ScreenInjection {
        @Inject(at = @At("TAIL"), method = "render")
        public void renderInjection(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
            SepClient.DELTA_TIME = delta;
        }
    }
}