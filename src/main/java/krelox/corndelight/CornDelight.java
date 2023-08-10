package krelox.corndelight;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;

public class CornDelight implements ModInitializer {

    public static final String MODID = "corndelight";

    @Override
    public void onInitialize() {
        CornDelightItems.registerItems();
        CornDelightBlocks.registerBlocks();

        ItemGroupEvents.modifyEntriesEvent(FarmersDelightMod.ITEM_GROUP).register(entries -> CornDelightItems.items.forEach(entries::add));

        BiomeModifications.addFeature(context -> context.getBiome().getTemperature() > 0f && context.getBiome().getTemperature() <= 1f,
                GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MODID, "patch_wild_corn")));
    }
}