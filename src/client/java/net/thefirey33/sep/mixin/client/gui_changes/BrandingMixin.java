/**
 * IDEA By: THEFIREY33
 * Change the branding of Minecraft to TUFFCraft.
 */


package net.thefirey33.sep.mixin.client.gui_changes;

import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;
import net.thefirey33.sep.Sep;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LogoDrawer.class)
public class BrandingMixin {
    @Unique
    private static final String CAPTION = "TUFFCraft Version 6.7";

    @Shadow
    public static final Identifier LOGO_TEXTURE = new Identifier(Sep.SEP_MOD_ID, "textures/gui/tuff_craft_logo.png");
    @Shadow
    public static final Identifier EDITION_TEXTURE = new Identifier(Sep.SEP_MOD_ID, "textures/gui/tuff_craft_skibidi_edition.png");

    @Mixin(Window.class)
    public static class WindowMixin {
        @Shadow
        @Final
        private long handle;

        @Inject(at = @At("TAIL"), method = "setTitle")
        public void setTitleInjection(String title, CallbackInfo ci){
            GLFW.glfwSetWindowTitle(this.handle, CAPTION);
        }
    }
}
