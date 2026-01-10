package net.thefirey33.sep.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

/**
 * Some rendering functions that are shared and constantly get repeated.
 */
public class HelperFunctions {
    public static void DrawDeveloperWarningText(TextRenderer textRenderer, DrawContext drawContext, Text text){
        HelperFunctions.SetOpacity(0.5F);
        drawContext.fill(0, 0, textRenderer.getWidth(text), textRenderer.fontHeight, Colors.BLACK);
        HelperFunctions.SetOpacity(1.0F);
        drawContext.drawText(textRenderer, text, 0, 0, Colors.RED, true);
    }

    public static void SetOpacity(float opacity){
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
    }

    /**
     * Render a texture in scale, CENTERED.
     * @param drawContext The drawing context.
     * @param identifier The identifier of the texture.
     * @param x The X-Position of the texture.
     * @param y The Y-Position of the texture.
     * @param width The width of the texture.
     * @param height The height of the texture.
     */
    public static void DrawTextureInScale(DrawContext drawContext, Identifier identifier, int x, int y, int width, int height){
        drawContext.drawTexture(
                identifier,
                x - width / 2,
                y - height / 2,
                width,
                height,
                0.0F,
                0.0F,
                width,
                height,
                width,
                height
        );
    }

    public static TextRenderer getTextRenderer(){
        return MinecraftClient.getInstance().textRenderer;
    }

    public static int LatestKeyPressed = 0;

    public static boolean isKeyPressed(int key){
        int result = GLFW.glfwGetKey(SepClient.WINDOW_HANDLE, key);
        if (result == GLFW.GLFW_PRESS && key != LatestKeyPressed)
        {
            LatestKeyPressed = key;
            return true;
        }
        else if (result == GLFW.GLFW_RELEASE){
            LatestKeyPressed = 0;
            return false;
        }
        return false;
    }
}
