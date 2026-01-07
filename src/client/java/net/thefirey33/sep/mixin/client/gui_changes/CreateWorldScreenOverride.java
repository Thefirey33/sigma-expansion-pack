package net.thefirey33.sep.mixin.client.gui_changes;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.text.Text;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.client.SepClient;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenOverride extends Screen {
    protected CreateWorldScreenOverride(Text title) {
        super(title);
    }

    @Shadow
    protected abstract void createLevel();

    @Inject(at = @At("TAIL"), method = "init")
    public void initInjection(CallbackInfo ci){
        if (!SepClient.IS_DEVELOPMENT) {
            Sep.LOGGER.info("Immediately forcing the game engine to generate a world.");
            this.createLevel();
        }
    }
}
