/**
 * IDEA By: THEFIREY33
 * Add the OneShot Logo to the scissorStack.
 */


package net.thefirey33.sep.mixin.client.gui_changes;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.thefirey33.sep.Sep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

@Mixin(ClickableWidget.class)
public class OneShotLogoButton {
    @Unique
    private static final String ONESHOT_STRING_REFERENCE = "oneshot";


    @Unique
    private static final Identifier ONESHOT_LOGO_IMAGE_REFERENCE = Identifier.of(Sep.SEP_MOD_ID, "textures/gui/oneshot_logo_text_button.png");

    /**
     * @author Thefirey33
     * @reason Add the OneShot logo if the text references the OneShot logo to be referenced.
     */
    @Overwrite
    public static void drawScrollableText(DrawContext context, TextRenderer textRenderer, Text text, int left, int top, int right, int bottom, int color) {
        int textWidth = textRenderer.getWidth((StringVisitable)text);
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
            // If the target string contains the term OneShot, then display the OneShot bulb.
            String origText = text.getString().toLowerCase();
            if (origText.contains(ONESHOT_STRING_REFERENCE))
                context.drawTexture(ONESHOT_LOGO_IMAGE_REFERENCE, left, top, 1, 1, 20, 18, 20, 20);
            context.drawCenteredTextWithShadow(textRenderer, text, (left + right) / 2, yPosition, color);
        }

    }

    @Unique
    private static double getLeftScroll(int textWidth, int innerButtonWidth) {
        int l = textWidth - innerButtonWidth;
        double currentTime = (double) Util.getMeasuringTimeMs() / 1000.0;
        double maxScrollValue = Math.max((double)l * 0.5, 3.0);
        // WHO THE FUCK IN THE Mojang DEVELOPMENT TEAM DECIDED THAT THIS FUCKING THING WAS A GOOD IDEA????
        double scrollCalculation = Math.sin(1.5707963267948966 * Math.cos(6.283185307179586 * currentTime / maxScrollValue)) / 2.0 + 0.5;
        return MathHelper.lerp(scrollCalculation, 0.0, (double)l);
    }
}
