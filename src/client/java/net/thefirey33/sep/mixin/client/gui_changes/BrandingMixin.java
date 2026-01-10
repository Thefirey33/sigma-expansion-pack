/**
 * IDEA By: THEFIREY33
 * Change the branding of Minecraft.
 */


package net.thefirey33.sep.mixin.client.gui_changes;

import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.client.SepClient;
import net.thefirey33.sep.client.flowey.FloweyBossFightScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LogoDrawer.class)
public class BrandingMixin {
    @Shadow
    public static final Identifier LOGO_TEXTURE = new Identifier(Sep.SEP_MOD_ID, "textures/gui/tuff_craft_logo.png");
    @Shadow
    public static final Identifier EDITION_TEXTURE = new Identifier(Sep.SEP_MOD_ID, "textures/gui/tuff_craft_skibidi_edition.png");
    @Unique
    private static final String CAPTION = "TUFFCraft Version 6.7";

    @Mixin(Window.class)
    public static class WindowOverride {

        @Shadow
        @Final
        private long handle;

        @Inject(at = @At("TAIL"), method = "setTitle")
        public void setTitle(String title, CallbackInfo ci) {
            GLFW.glfwSetWindowTitle(this.handle, FloweyBossFightScreen.IS_FINAL_BOSSFIGHT ? "Floweycraft" : "TUFFCraft Version 6.7 SHAREWARE EDITION %s".formatted(SepClient.IS_DEVELOPMENT ? "DEVELOPMENT MODE!!!" : ""));
        }


        @Inject(at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwMakeContextCurrent(J)V"), method = "<init>")
        public void retrieveWindowHandle(WindowEventHandler eventHandler, MonitorTracker monitorTracker, WindowSettings settings, String videoMode, String title, CallbackInfo ci) {
            Sep.LOGGER.info("Retrieved Window Handle: {}", this.handle);
            SepClient.WINDOW_HANDLE = this.handle;
        }
    }


    @Mixin(LogoDrawer.class)
    public static class LogoDrawerFix {

        @Final
        @Unique
        private static final Integer TopLogoHeight = 44;
        @Unique
        @Final
        private static final Integer BottomEditionLogoHeight = 16;
        @Unique
        @Final
        private static final Integer TopLogoWidth = 256;
        @Unique
        @Final
        private static final Integer EditionLogoWidth = TopLogoWidth / 2;
        @Shadow
        @Final
        private boolean ignoreAlpha;

        /**
         * @author Thefirey33
         * @reason Fix the logo renderer.
         */
        @Overwrite
        public void draw(DrawContext context, int screenWidth, float alpha, int y) {
            context.setShaderColor(1.0F, 1.0F, 1.0F, this.ignoreAlpha ? 1.0F : alpha);
            // Position the actual Minecraft logo for the GUI.
            int topLogoXPosition = screenWidth / 2 - 128;
            context.drawTexture(LOGO_TEXTURE, topLogoXPosition, y, 0.0F, 0.0F, TopLogoWidth, TopLogoHeight, TopLogoWidth, TopLogoHeight);
            // Position the actual edition text for the Minecraft GUI.
            int editionXPosition = screenWidth / 2 - 64;
            int editionYPosition = y + 44 - 7;
            context.drawTexture(EDITION_TEXTURE, editionXPosition, editionYPosition, 0.0F, 0.0F, EditionLogoWidth, BottomEditionLogoHeight, EditionLogoWidth, BottomEditionLogoHeight);
            // Reset the shader color back to normal.
            context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
