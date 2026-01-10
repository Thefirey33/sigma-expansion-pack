package net.thefirey33.sep.mixin.client.gui_changes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import net.thefirey33.sep.client.SoundInstances;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public class VillagerEgyptOverride {

    @Unique
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
    @Unique
    private static final SoundManager SOUND_MANAGER = CLIENT.getSoundManager();

    @Inject(at = @At("TAIL"), method = "init")
    public void initInjection(CallbackInfo ci) {
        if (!SOUND_MANAGER.isPlaying(SoundInstances.PROPERTY_OF_EGYPT_INSTANCE))
            SOUND_MANAGER.play(SoundInstances.PROPERTY_OF_EGYPT_INSTANCE);
    }

}
