/**
 * IDEA By: nikodev
 * Sigma ARMOR / TOOLS / BLOCKS
 */


package net.thefirey33.sep.registries;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.thefirey33.sep.Sep;

public class ModBlocks {
    public static final Block SIGMA_BLOCK = register(
            new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)),
            "sigma_block",
            true
    );

    public static final Block SIGMA_ORE = register(
            new Block(AbstractBlock.Settings.create().
                    sounds(BlockSoundGroup.NETHER_ORE).hardness(Blocks.OBSIDIAN.getHardness())), // IDEA by karl -> Obsidian Hardness
            "sigma_ore",
            true
    );

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = new Identifier(Sep.SEP_MOD_ID, name);

        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) ->
            itemGroup.add(SIGMA_BLOCK.asItem()));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) ->
            itemGroup.add(SIGMA_ORE.asItem()));
    }
}
