package krelox.corndelight.data;

import vectorwing.farmersdelight.common.tag.CompatibilityTags;
import vectorwing.farmersdelight.common.tag.ModTags;
import krelox.corndelight.block.CornDelightBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class CornDelightBlockTags extends FabricTagProvider.BlockTagProvider {
    public CornDelightBlockTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        valueLookupBuilder(ModTags.WILD_CROPS).add(CornDelightBlocks.WILD_CORN);

        valueLookupBuilder(CompatibilityTags.SERENE_SEASONS_SUMMER_CROPS_BLOCK).add(CornDelightBlocks.CORN_CROP);
    }
}
