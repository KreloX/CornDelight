package krelox.corndelight;

import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class CornDelight implements ModInitializer {

    public static final String MODID = "corndelight";

    public static ConfiguredFeature<?, ?> WILD_CORN_CONFIGURED_FEATURE = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
            new Identifier(MODID, "patch_wild_corn"), new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                    new RandomPatchFeatureConfig(64, 2, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(CornDelightBlocks.WILD_CORN))))));

    public static PlacedFeature WILD_CORN_PLACED_FEATURE = Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(MODID, "patch_wild_corn"),
            new PlacedFeature(RegistryEntry.of(WILD_CORN_CONFIGURED_FEATURE), List.of(RarityFilterPlacementModifier.of(70),
                    SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of())));

    @Override
    public void onInitialize() {
        CornDelightItems.registerItems();
        CornDelightBlocks.registerBlocks();

        BiomeModifications.addFeature(context -> context.getBiome().getTemperature() > 0f && context.getBiome().getTemperature() <= 1f,
                GenerationStep.Feature.VEGETAL_DECORATION, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MODID, "patch_wild_corn")));
    }
}