package krelox.corndelight;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class CornDelight implements ModInitializer {

    public static final String MODID = "corndelight";

    @Override
    public void onInitialize() {
        CornDelightItems.registerItems();
        CornDelightBlocks.registerBlocks();
        ItemGroupEvents.modifyEntriesEvent(FarmersDelightMod.ITEM_GROUP).register(entries -> CornDelightItems.items.forEach(entries::add));
    }
}