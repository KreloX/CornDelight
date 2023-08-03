package krelox.corndelight;

import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CornDelight implements ModInitializer {

    public static final String MODID = "corndelight";

    @SuppressWarnings("unused")
    public static final ItemGroup CORN_DELIGHT = Registry.register(Registries.ITEM_GROUP, new Identifier(MODID, "corn_delight"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.corn_delight"))
                    .icon(() -> new ItemStack(CornDelightItems.CORN)).entries(((displayContext, entries) -> CornDelightItems.items.forEach(entries::add))).build());

    @Override
    public void onInitialize() {
        CornDelightItems.registerItems();
        CornDelightBlocks.registerBlocks();
    }
}