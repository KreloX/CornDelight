package krelox.corndelight.block;

import krelox.corndelight.CornDelight;
import krelox.corndelight.item.CornDelightItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CornDelightBlocks {

    public static final Block CORN_CROP = registerBlock("corn_crop", new CornCropBlock(AbstractBlock.Settings.copy(Blocks.WHEAT),
            CornDelightItems.items.get("corn_seeds")));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(CornDelight.MODID, name), block);
    }

    public static void registerBlocks() {
    }
}
