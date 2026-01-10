/**
 * THE DIALOGUE SYSTEM FOR THE VESSEL SCREEN (Startup)
 * <p>
 * THEFIREY33
 */


package net.thefirey33.sep.client.vessel_screen_dialogue_manager;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.Window;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.LocalRandom;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.client.SepClient;
import net.thefirey33.sep.registries.ModSounds;
import net.thefirey33.sep.client.dialogue_loaders.BeginningGasterLoader;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Unique;

// This class was added because the variable list in the actual DeltaruneVesselSelectScreen was getting huge.
public class VesselSelectScreenDialogueManager {

    /**
     * How fast the dialogue is.
     */
    public static final Float DIALOGUE_SPEED = 0.5F; // 0.5F

    /**
     * How fast the dialogue is to fade.
     */
    public static final Float DIALOGUE_FADE_SPEED = 0.05F;

    /**
     * How fast the dialogue is to fade.
     */
    public static final Float TRANSITION_SPEED = 0.01F;

    /**
     * How many characters in off-set before the next line of dialogue.
     */
    public static final Integer DIALOGUE_OFFSET = 20;
    public static final SoundInstance TRANSITION_INSTANCE = new AbstractSoundInstance(ModSounds.MUS_CYMBAL_IDENTIFIER, SoundCategory.MASTER, new LocalRandom(100)) {
        @Override
        public Identifier getId() {
            return ModSounds.MUS_CYMBAL_IDENTIFIER;
        }
    };
    /**
     * The current dialogue index we are ticking away at.
     */
    public static Integer CurrentDialogueIndex = 0;
    /**
     * This makes it so the dialogue is slowly displayed character by character to the screen,
     * Then after it finishes this character set, it goes over to the next set of characters.
     */
    public static Float CurrentDialogueTicker = 0.0F;
    public static Float OpacityOfText = 1.0F;
    public static Float TRANSITION_START = 0.0F;

    /**
     * Attempt to get the current dialogue.
     * @return The current dialogue by the indx.
     */
    public static String GetCurrentDialogue() {
        try {
            if (CurrentDialogueIndex > BeginningGasterLoader.LANGUAGE_REFERENCED_DIALOGUE.size() - 1)
                return "";
            return BeginningGasterLoader.LANGUAGE_REFERENCED_DIALOGUE.get(CurrentDialogueIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Move the current dialogue state forward.
     */
    public static void ForwardDialogueState() {
        CurrentDialogueIndex++;
        CurrentDialogueTicker = 0.0F;
        OpacityOfText = 1.0F;
    }
    public static int WindowPositionX;
    public static int WindowPositionY;
    /**
     * Attempt to draw to the screen.
     * @param textRenderer The text renderer in-context.
     * @param drawContext The drawContext, responsible for drawing the text.
     * @param deltaTime the delta time.
     * @return If the dialogue has finished or not.
     */
    public static boolean DrawDialogueToScreen(TextRenderer textRenderer, Screen currentScreenInstance, DrawContext drawContext, float deltaTime) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        SoundManager soundManager = minecraftClient.getSoundManager();
        CurrentDialogueTicker += deltaTime * DIALOGUE_SPEED;
        String CurrentDialogue = GetCurrentDialogue();

        // Do dialogue stuff.
        int topDialogueLength = CurrentDialogue.length() + DIALOGUE_OFFSET;
        if (CurrentDialogueTicker > topDialogueLength) ForwardDialogueState();
        else if (CurrentDialogueTicker > CurrentDialogue.length())
            OpacityOfText -= deltaTime * DIALOGUE_FADE_SPEED;


        // Transition Check.
        if (CurrentDialogueIndex > BeginningGasterLoader.LANGUAGE_REFERENCED_DIALOGUE.size() - 1) {
            // Start playing the transition sound when the value is 0.0.
            if (TRANSITION_START <= 0.0) {
                soundManager.play(TRANSITION_INSTANCE);

                Window window = MinecraftClient.getInstance().getWindow();
                WindowPositionX = window.getX();
                WindowPositionY = window.getY();
                minecraftClient.getMusicTracker().stop();
            }

            GLFW.glfwSetWindowPos(SepClient.WINDOW_HANDLE, WindowPositionX + (int) (Math.random() * (TRANSITION_START * 100)), WindowPositionY + (int) (Math.random() * (TRANSITION_START * 100)));
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, TRANSITION_START);
            RenderSystem.enableBlend();
            drawContext.fill(0, 0, currentScreenInstance.width, currentScreenInstance.height, Colors.WHITE);
            RenderSystem.disableBlend();

            // Check if the transition sound has stopped playing.
            if (!soundManager.isPlaying(TRANSITION_INSTANCE)) {
                GLFW.glfwSetWindowPos(SepClient.WINDOW_HANDLE, WindowPositionX, WindowPositionY);
                return true;
            }

            TRANSITION_START += deltaTime * TRANSITION_SPEED;
        } else {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, OpacityOfText);
            drawContext.drawCenteredTextWithShadow(textRenderer, CurrentDialogue.substring(0, (int) Math.min(CurrentDialogue.length(), CurrentDialogueTicker)), currentScreenInstance.width / 2, currentScreenInstance.height / 2, Colors.WHITE);
        }
        // Reset the shader color back to normal.
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        return false;
    }
}
