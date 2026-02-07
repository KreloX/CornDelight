package krelox.corndelight.itemGroup;

import krelox.corndelight.CornDelight;
import krelox.corndelight.block.CornDelightBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CornDelightItemGroups {

    public static final ItemGroup CORNDELIGHT_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            Identifier.of(CornDelight.MODID, "item_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(CornDelightBlocks.CORN_CRATE)) // pick an icon
                    .displayName(Text.translatable("itemGroup." + CornDelight.MODID))
                    .entries((context, entries) -> {
                        // Put your blocks/items here
                        entries.add(CornDelightBlocks.CORN_CRATE);
                        entries.add(CornDelightBlocks.CORN_KERNEL_BAG);
                        entries.add(CornDelightBlocks.POPCORN_BOX);
                        entries.add(CornDelightBlocks.WILD_CORN);
                    })
                    .build()
    );

    public static void register() {
        // class-load hook (optional)
    }
}
