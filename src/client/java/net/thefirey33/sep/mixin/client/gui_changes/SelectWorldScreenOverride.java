package net.thefirey33.sep.mixin.client.gui_changes;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.world.level.storage.LevelStorage;
import net.thefirey33.sep.client.SepClient;
import org.apache.commons.io.FileUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(SelectWorldScreen.class)
public abstract class SelectWorldScreenOverride extends Screen {
    @Shadow
    public abstract void worldSelected(boolean buttonsActive, boolean deleteButtonActive);

    @Shadow
    protected TextFieldWidget searchBox;

    protected SelectWorldScreenOverride(Text title) {
        super(title);
    }

    @Unique
    @Final
    private static final Integer ONESHOT_BUTTON_WIDTH = 200;

    private static LogoDrawer TUFFCraftLogoDrawer;

    @Inject(at = @At(value = "HEAD"), method = "init", cancellable = true)
    public void init(CallbackInfo ci) {
        // Show custom world create menu if not development.
        if (!SepClient.IS_DEVELOPMENT) {
        /*
            REMEMBER.
            WE ONLY HAVE ONESHOT!
            SO DELETE ANY OTHER WORLD THAT EXISTS.
         */
            assert this.client != null;
            TUFFCraftLogoDrawer = new LogoDrawer(false);
            LevelStorage levelStorage = this.client.getLevelStorage();

            levelStorage.getLevelList().forEach(levelSave -> {
                try {
                    FileUtils.deleteDirectory(levelSave.path().toFile());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.sep.play_once"), button -> {
                CreateWorldScreen.create(this.client, this);
            }).dimensions(this.width / 2 - ONESHOT_BUTTON_WIDTH / 2, this.height / 2, ONESHOT_BUTTON_WIDTH, 20).build());
            ci.cancel();
        }
    }

    /**
     * @author Thefirey33
     * @reason We only have one button, so no need for a searchBox.
     */
    @Overwrite
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return true;
    }

    /**
     * @author Thefirey33
     * @reason We only have one button, so no need for a searchBox.
     */
    @Overwrite
    public boolean charTyped(char chr, int modifiers) {
        return true;
    }

    /**
     * Render the credit information.
     * @param drawContext The drawContext to use.
     */
    @Unique
    public void renderCreditsWithWrapping(DrawContext drawContext){
        Text translatedText = Text.translatable("gui.sep.mod_credits");
        String translatableText = translatedText.getString();
        String[] splitRegex = translatableText.split("\n");
        int yOffset = 10;

        for(String s : splitRegex){
            drawContext.drawText(textRenderer, s, 0, this.height - yOffset, Colors.WHITE, true);
            yOffset += 10;
        }
    }
    /**
     * @author Thefirey33
     * @reason Since there's only going to be one button.
     */
    @Overwrite
    public void render(DrawContext context, int mouseX, int mouseY, float delta){
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        renderCreditsWithWrapping(context);
        TUFFCraftLogoDrawer.draw(context, this.width, 1.0F);
    }

    /**
     * @author Thefirey33
     * @reason Since there's no searchBox.
     */
    @Overwrite
    public void tick() {

    }

    @Mixin(CreateWorldScreen.class)
    public static abstract class CreateWorldScreenOverride {
        @Shadow
        protected abstract void createLevel();

        @Inject(at = @At("TAIL"), method = "init")
        public void initInjection(CallbackInfo ci){
            this.createLevel();
        }
    }
}
