package net.thefirey33.sep.client.vessel_screen_dialogue_manager;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Colors;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.client.dialogue_loaders.BeginningGasterLoader;

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
     * How many characters in off-set before the next line of dialogue.
     */
    public static final Integer DIALOGUE_OFFSET = 20;

    /**
     * If the dialogue is finished.
     */
    public static Boolean DialogueFinished = false;

    /**
     * The current dialogue index we are ticking away at.
     */
    public static Integer CurrentDialogueIndex = 0;

    /**
     * This makes it so the dialogue is slowly displayed character by character to the screen,
     * Then after it finishes this character set, it goes over to the next set of characters.
     */
    public static Float CurrentDialogueTicker = 0.0F;

    /**
     * Attempt to get the current dialogue.
     * @return The current dialogue by the indx.
     */
    public static String GetCurrentDialogue(){
        try {
            return BeginningGasterLoader.LANGUAGE_REFERENCED_DIALOGUE.get(CurrentDialogueIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Float OpacityOfText = 1.0F;
    /**
     * Move the current dialogue state forward.
     */
    public static void ForwardDialogueState(){
        CurrentDialogueIndex++;
        CurrentDialogueTicker = 0.0F;
        OpacityOfText = 1.0F;
    }
    /**
     * Attempt to draw to the screen.
     * @param textRenderer The text renderer in-context.
     * @param drawContext The drawContext, responsible for drawing the text.
     * @param deltaTime the delta time.
     * @return If the dialogue has finished or not.
     */
    public static boolean DrawDialogueToScreen(TextRenderer textRenderer, Screen currentScreenInstance, DrawContext drawContext, float deltaTime){
        // NOTE: Keep this like this.
        // The game is supposed to crash if the user tries going back.
        CurrentDialogueTicker += deltaTime * DIALOGUE_SPEED;
        String CurrentDialogue = GetCurrentDialogue();
        int topDialogueLength = CurrentDialogue.length() + DIALOGUE_OFFSET;
        if (CurrentDialogueTicker > topDialogueLength) ForwardDialogueState();
        else if (CurrentDialogueTicker > CurrentDialogue.length())
            OpacityOfText -= deltaTime * DIALOGUE_FADE_SPEED;
        if (CurrentDialogueIndex >= BeginningGasterLoader.LANGUAGE_REFERENCED_DIALOGUE.size())
            return true;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, OpacityOfText);
        drawContext.drawCenteredTextWithShadow(textRenderer, CurrentDialogue.substring(0, (int) Math.min(CurrentDialogue.length(), CurrentDialogueTicker)), currentScreenInstance.width / 2, currentScreenInstance.height / 2, Colors.WHITE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        return false;
    }
}
