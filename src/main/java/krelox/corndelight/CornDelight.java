package krelox.corndelight;

import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.api.ModInitializer;

public class CornDelight implements ModInitializer {

    public static final String MODID = "corndelight";

    @Override
    public void onInitialize() {
        CornDelightItems.registerItems();
        CornDelightBlocks.registerBlocks();
    }
}