/**
 * A very practical dummy screen, for use in nothing.
 * So, we don't have to create a screen for other stuff.
 * <p>
 * THEFIREY33
 */

package net.thefirey33.sep.client.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.thefirey33.sep.client.SepClient;

/**
 * The Dummy Screen.
 */
public class DummyScreen extends Screen {
    public DummyScreen(Text title) {
        super(title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(textRenderer, "DUMMY SCREEN FOR TRANSITION TESTING.", 0, 0, Colors.WHITE, true);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        SepClient.CONNECT_TO_CREATED_SERVER = true;
        return true;
    }
}
