package net.thefirey33.sep.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.thefirey33.sep.client.dialogue_loaders.BeginningGasterLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class LanguageManagerOnInitialize  {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/realms/RealmsClient;createRealmsClient(Lnet/minecraft/client/MinecraftClient;)Lnet/minecraft/client/realms/RealmsClient;"), method = "<init>")
    public void onLanguageManagerReadyInjection(RunArgs args, CallbackInfo ci){
        BeginningGasterLoader.LoadDialogue((MinecraftClient) (Object) this);
    }
}
