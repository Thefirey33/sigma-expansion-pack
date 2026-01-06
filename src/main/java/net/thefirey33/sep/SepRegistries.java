package net.thefirey33.sep;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SepRegistries {
    public static final SoundEvent WING_DING_GASTER_MM = SoundEvent.of(Identifier.of(Sep.SEP_MOD_ID, "another_wing_ding"));
    public static final RegistryEntry<SoundEvent> WING_DING_GASTER_MM_ENTRY = RegistryEntry.of(WING_DING_GASTER_MM);
    public static final MusicSound WING_DING_GASTER_MM_MUSIC = new MusicSound(WING_DING_GASTER_MM_ENTRY, 0, 192, true);
}
