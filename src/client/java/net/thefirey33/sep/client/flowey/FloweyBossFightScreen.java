package net.thefirey33.sep.client.flowey;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

/*
    TODO:
    This boss-fight screen is currently left for setup.
    Until we move onto the finishing phase of the mod,
    this screen should stay UNTOUCHED!

    Work on this later...

    THEFIREY33
 */

public class FloweyBossFightScreen extends Screen
{
    public static Boolean IS_FINAL_BOSSFIGHT = false;
    public FloweyRegistries.PlayerObject playerObject;

    public FloweyBossFightScreen() {
        super(Text.empty());
        Window window = MinecraftClient.getInstance().getWindow();
        this.playerObject = new FloweyRegistries.PlayerObject(window.getScaledWidth() / 2, window.getScaledHeight() / 2);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, Colors.BLACK);
        // Render the playerObject.
        playerObject.render(context);
    }
}
