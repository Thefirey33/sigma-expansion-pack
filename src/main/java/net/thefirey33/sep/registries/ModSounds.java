package net.thefirey33.sep.registries;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.thefirey33.sep.Sep;

public class ModSounds {
    // Cymbal Transition
    public static final Identifier MUS_CYMBAL_IDENTIFIER = Identifier.of(Sep.SEP_MOD_ID, "mus_cymbal");
    // Wing Ding Music.
    public static final SoundEvent WING_DING_GASTER_MM = SoundEvent.of(Identifier.of(Sep.SEP_MOD_ID, "another_wing_ding"));
    public static final RegistryEntry<SoundEvent> WING_DING_GASTER_MM_ENTRY = RegistryEntry.of(WING_DING_GASTER_MM);
    public static final MusicSound WING_DING_GASTER_MM_MUSIC = new MusicSound(WING_DING_GASTER_MM_ENTRY, 0, 192, true);
    // The Tung Hit Sound.
    public static final SoundEvent TUNG_HIT = SoundEvent.of(Identifier.of(Sep.SEP_MOD_ID, "tung_hit"));
    public static final SoundEvent EGYPT = SoundEvent.of(Identifier.of(Sep.SEP_MOD_ID, "ibaerie"));
}
