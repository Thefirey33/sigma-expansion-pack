/**
 * IDEA By: THEFIREY33
 * Add the OneShot Logo to the scissorStack.
 */


package net.thefirey33.sep.mixin.client.gui_changes;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.thefirey33.sep.Sep;
import org.spongepowered.asm.mixin.*;

import java.util.Objects;

@Mixin(ClickableWidget.class)
public class OneShotLogoButton {
    @Shadow
    private int y;
    @Unique
    private static final String ONESHOT_STRING_REFERENCE = "oneshot";

    @Unique
    @Final
    private static final Integer BUTTON_MARGIN = 10;

    @Final
    @Unique
    private static final Integer LOGO_HEIGHT = 40;


    @Unique
    private static final Identifier ONESHOT_LOGO_IMAGE_REFERENCE = Identifier.of(Sep.SEP_MOD_ID, "textures/gui/oneshot_logo_text_button.png");

    /**
     * @author Thefirey33
     * @reason Add the OneShot logo if the text references the OneShot logo to be referenced.
     */
    @Overwrite
    public static void drawScrollableText(DrawContext context, TextRenderer textRenderer, Text text, int left, int top, int right, int bottom, int color) {
        int textWidth = textRenderer.getWidth(text);
        int fullHeight = top + bottom;
        Objects.requireNonNull(textRenderer);
        int yPosition = (fullHeight - 9) / 2 + 1;
        int innerButtonWidth = right - left;
        if (textWidth > innerButtonWidth) {
            double leftScroll = getLeftScroll(textWidth, innerButtonWidth);
            context.enableScissor(left, top, right, bottom);
            context.drawTextWithShadow(textRenderer, text, left - (int) leftScroll, yPosition, color);
            context.disableScissor();
        } else {
            // Draw the OneShot Modified Logo.
            String actualMessage = text.getString();
            int firstCharacterReference = actualMessage.toLowerCase().indexOf(ONESHOT_STRING_REFERENCE);
            if (firstCharacterReference != -1) {
                // Get the actual message.
                String firstSubstr = actualMessage.substring(0, firstCharacterReference - 1);
                int widthOfText = textRenderer.getWidth(firstSubstr);
                int leftPosText = left + BUTTON_MARGIN;
                int imageWidth = Math.min(256, right - (leftPosText + widthOfText));
                context.drawText(textRenderer, firstSubstr, leftPosText, yPosition, color, true);
                context.drawTexture(ONESHOT_LOGO_IMAGE_REFERENCE,
                        leftPosText + widthOfText,
                        yPosition - LOGO_HEIGHT / 2,
                        0.0F,
                        0.0F,
                        imageWidth,
                        LOGO_HEIGHT,
                        imageWidth,
                        LOGO_HEIGHT);
            } else {
                context.drawCenteredTextWithShadow(textRenderer, actualMessage, (left + right) / 2, yPosition, color);
            }
        }

    }

    @Unique
    private static double getLeftScroll(int textWidth, int innerButtonWidth) {
        int l = textWidth - innerButtonWidth;
        double currentTime = (double) Util.getMeasuringTimeMs() / 1000.0;
        double maxScrollValue = Math.max((double) l * 0.5, 3.0);
        // WHO THE FUCK IN THE Mojang DEVELOPMENT TEAM DECIDED THAT THIS FUCKING THING WAS A GOOD IDEA????
        double scrollCalculation = Math.sin(1.5707963267948966 * Math.cos(6.283185307179586 * currentTime / maxScrollValue)) / 2.0 + 0.5;
        return MathHelper.lerp(scrollCalculation, 0.0, l);
    }
}
