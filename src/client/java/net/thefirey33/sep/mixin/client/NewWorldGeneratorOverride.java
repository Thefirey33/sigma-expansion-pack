package net.thefirey33.sep.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsNotificationsScreen;
import net.minecraft.text.Text;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

@Mixin(TitleScreen.class)
public abstract class NewWorldGeneratorOverride extends Screen {

    @Shadow
    private @Nullable RealmsNotificationsScreen realmsNotificationGui;

    @Shadow
    protected abstract boolean isRealmsNotificationsGuiDisplayed();

    @Shadow
    @Final
    public static Text COPYRIGHT = Text.of("Terribly Coded and Copyrighted by Mojang. Do not DISTRIBUTE!");

    @Shadow
    private @Nullable SplashTextRenderer splashText;

    protected NewWorldGeneratorOverride(Text title) {
        super(title);
    }

    /**
     * @author Thefirey33
     * @reason Remove the button that allows creation a different worlds.
     */
    @Overwrite
    public void init() {
        assert client != null;

        if (this.splashText == null) {
            this.splashText = this.client.getSplashTextLoader().get();
        }

        int copyrightWidth = this.textRenderer.getWidth(COPYRIGHT);
        int xPosition = this.width - copyrightWidth - 2;
        int yPosition = this.height / 4 + 48;
        int screenHalfCalculation = this.width / 2;
        int yButtonGroupPos = 84;
        var levelStorage = this.client.getLevelStorage().getLevelList().levels();
        /*
            Create the new only OneShot button.
            This will force the user to only have one game instance ever.
         */
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.sep.play_single"), button -> {
            // Attempt to make it only "OneShot".
            levelStorage.forEach(levelSave -> {
                try {
                    FileUtils.deleteDirectory(new File(levelSave.path().toUri()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            CreateWorldScreen.create(this.client, this);
        }).dimensions(screenHalfCalculation - 100, yPosition, 200, 20).build());

        // This is the language button, at the bottom of the screen, to the right.
        this.addDrawableChild(new TexturedButtonWidget(screenHalfCalculation - 124, yPosition + yButtonGroupPos, 20, 20, 0, 106, 20, ButtonWidget.WIDGETS_TEXTURE, 256, 256, (button) -> this.client.setScreen(new LanguageOptionsScreen(this, this.client.options, this.client.getLanguageManager())), Text.translatable("narrator.button.language")));
        // This is the options buttons, at the bottom group.
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.options"), (button) -> this.client.setScreen(new OptionsScreen(this, this.client.options))).dimensions(this.width / 2 - 100, yPosition + yButtonGroupPos, 98, 20).build());
        // This is the quit game button, at the bottom group.
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("menu.quit"), (button) -> this.client.scheduleStop()).dimensions(this.width / 2 + 2, yPosition + yButtonGroupPos, 98, 20).build());
        // Accessibility button.
        this.addDrawableChild(new TexturedButtonWidget( screenHalfCalculation + 104, yPosition + yButtonGroupPos, 20, 20, 0, 0, 20, ButtonWidget.ACCESSIBILITY_TEXTURE, 32, 64, (button) -> this.client.setScreen(new AccessibilityOptionsScreen(this, this.client.options)), Text.translatable("narrator.button.accessibility")));
        // Copyright Info, they have to have that copyright for real.
        this.addDrawableChild(new PressableTextWidget(xPosition, this.height - 20, copyrightWidth, 10, COPYRIGHT, (button) -> this.client.setScreen(new CreditsAndAttributionScreen(this)), this.textRenderer));

        this.client.setConnectedToRealms(false);
        if (this.realmsNotificationGui == null) {
            this.realmsNotificationGui = new RealmsNotificationsScreen();
        }

        if (this.isRealmsNotificationsGuiDisplayed()) {
            this.realmsNotificationGui.init(this.client, this.width, this.height);
        }
    }

}
