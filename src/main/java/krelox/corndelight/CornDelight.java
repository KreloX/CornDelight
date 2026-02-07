package krelox.corndelight;

import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import krelox.corndelight.itemGroup.CornDelightItemGroups;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;

public class CornDelight implements ModInitializer {
    public static final String MODID = "corndelight";

    @SuppressWarnings("unused")
    public static final ItemGroup CORN_DELIGHT = Registry.register(Registries.ITEM_GROUP, Identifier.of(MODID, "corn_delight"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.corndelight"))
                    .icon(() -> new ItemStack(CornDelightItems.CORN)).entries(((displayContext, entries) -> CornDelightItems.ITEMS.forEach(entries::add))).build());

    @Override
    public void onInitialize() {
        CornDelightItems.registerItems();
        CornDelightBlocks.registerBlocks();
        CornDelightItemGroups.register();

        registerCompostables();

        BiomeModifications.addFeature(context -> context.getBiome().getTemperature() > 0f && context.getBiome().getTemperature() <= 1f,
                GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MODID, "patch_wild_corn")));
    }

    public static void registerCompostables() {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(CornDelightItems.CORN_SEEDS, 0.3F);

        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(CornDelightItems.CORN, 0.65F);
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(CornDelightBlocks.WILD_CORN.asItem(), 0.65F);
    }
}