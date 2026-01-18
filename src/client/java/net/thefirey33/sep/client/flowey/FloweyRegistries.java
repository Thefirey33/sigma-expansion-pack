package net.thefirey33.sep.client.flowey;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.client.HelperFunctions;
import net.thefirey33.sep.client.SepClient;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

public class FloweyRegistries {
    public static final Identifier PLAYER_SOUL = register("player_soul.png");

    public static ArrayList<Integer> KEYS = new ArrayList<>();

    /**
     * Register the specified identifier.
     *
     * @param registryName The registry name.
     * @return Correctly formatted identifier.
     */
    public static Identifier register(String registryName) {
        return Identifier.of(Sep.SEP_MOD_ID, "flowey_boss_battle/%s".formatted(registryName));
    }

    public interface CustomRenderableController {
        void render(DrawContext drawContext);
    }

    /**
     * The PlayerObject that we will be controlling for this boss-battle.
     */
    public static class PlayerObject implements CustomRenderableController {

        public PlayerObject(int x, int y){
            this.x = x;
            this.y = y;
        }
        /**
         * The size of the soul.
         */
        private static final int SOUL_SIZE = 8;
        /**
         * The speed of the soul.
         */
        private static final int SOUL_SPEED = 4;
        /**
         * The X-Position of the SOUL.
         */
        private int x = 0;
        /**
         * The Y-Position of the SOUL.
         */
        private int y = 0;

        public boolean keyboardPressWrapper(int keycode){
            return KEYS.contains(keycode);
        }

        public float getAxis(int positiveAxis, int negativeAxis)
        {
            return (this.keyboardPressWrapper(positiveAxis) ? SOUL_SPEED : this.keyboardPressWrapper(negativeAxis) ? -SOUL_SPEED : 0) * MinecraftClient.getInstance().getLastFrameDuration();
        }

        public void setPosition(int x, int y){
            this.x = x;
            this.y = y;
        }

        public void handleControllers() {
            // WASD Controls.
            float horizontalAxis = getAxis(GLFW.GLFW_KEY_D, GLFW.GLFW_KEY_A);
            float verticalAxis = getAxis(GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_W);
            this.x += (int) horizontalAxis;
            this.y += (int) verticalAxis;
        }

        @Override
        public void render(DrawContext drawContext) {
            // Handle player controls.
            this.handleControllers();
            HelperFunctions.DrawTextureInScale(drawContext, FloweyRegistries.PLAYER_SOUL, this.x, this.y, SOUL_SIZE, SOUL_SIZE);
        }
    }
}
