/**
 * IDEA By: THEFIREY33
 * Add gaster dialogue.
 */


package net.thefirey33.sep.client.dialogue_loaders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Language;
import net.thefirey33.sep.Sep;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * This class loads the beginning gaster dialogue.
 */
public class BeginningGasterLoader {
    public static final String DIALOGUE_REFERENCE = "dialog_client/gaster_beginning_speech_dialog.json";

    public static String LANGUAGE_REFERENCE;
    public static List<String> LANGUAGE_REFERENCED_DIALOGUE;

    public static void LoadDialogue(MinecraftClient minecraftClient){
        // Pre-Init of the loader.
        ClassLoader loader = Sep.class.getClassLoader();
        LANGUAGE_REFERENCE = minecraftClient.getLanguageManager().getLanguage();
        Sep.LOGGER.info("Loading {}... USING LANGUAGE: {}", DIALOGUE_REFERENCE, LANGUAGE_REFERENCE);
        // Try to load the file, if it doesn't work, throw a runtime exception.
        try (InputStream gasterDialogueSpeechLoader = loader.getResourceAsStream(DIALOGUE_REFERENCE))
        {
            // Try to load the gaster dialogue with Gson.
            if (gasterDialogueSpeechLoader == null) throw new FileNotFoundException("%s wasn't found.".formatted(DIALOGUE_REFERENCE));
            Gson googleJsonReader = new Gson();
            // Load the dialogue from the input stream, then put it into Gson.
            String readJsonData = new String(gasterDialogueSpeechLoader.readAllBytes(), StandardCharsets.UTF_8);
            Sep.LOGGER.info("Loaded Gaster Dialogue: {}", readJsonData);
            TypeToken<Map<String, List<String>>> jsonReaderTypeToken = new TypeToken<>() {
            };
            Map<String, List<String>> readData = googleJsonReader.fromJson(readJsonData, jsonReaderTypeToken);
            // Attempt to fetch the language. If it doesn't exist, then try to get the default.
            try {
                // Get en_us if the target language doesn't exist.
                // Then, set the static LANGUAGE_REFERENCED_DIALOGUE to it.
                LANGUAGE_REFERENCED_DIALOGUE = readData.get(readData.containsKey(LANGUAGE_REFERENCE) ? LANGUAGE_REFERENCE : Language.DEFAULT_LANGUAGE);
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
