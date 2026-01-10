package net.thefirey33.sep.client;

import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.LocalRandom;
import net.thefirey33.sep.Sep;
import net.thefirey33.sep.registries.ModSounds;

public class SoundInstances {
    public static final Identifier PROPERTY_OF_EGYPT_IDENTIFIER = Identifier.of(Sep.SEP_MOD_ID, "ibaerie");
    public static final SoundInstance PROPERTY_OF_EGYPT_INSTANCE = new AbstractSoundInstance(PROPERTY_OF_EGYPT_IDENTIFIER, SoundCategory.MASTER, new LocalRandom(1000)) {
        @Override
        public Identifier getId() {
            return PROPERTY_OF_EGYPT_IDENTIFIER;
        }
    };
    public static final MusicSound RELAXING_SONG_MUSIC_SOUND = new MusicSound(RegistryEntry.of(ModSounds.RELAXING_SONG), 0, 100, true);
}
