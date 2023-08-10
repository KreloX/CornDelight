package krelox.corndelight.data;

import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import krelox.corndelight.block.CornDelightBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class CornDelightBlockTags extends FabricTagProvider.BlockTagProvider {
    public CornDelightBlockTags(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(TagsRegistry.WILD_CROPS).add(CornDelightBlocks.WILD_CORN);
    }
}
