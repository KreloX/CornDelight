package krelox.corndelight.data;

import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import krelox.corndelight.CornDelightTags;
import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class CornDelightItemTags extends FabricTagProvider.ItemTagProvider {
    public CornDelightItemTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(CornDelightTags.Items.CORN).add(CornDelightItems.CORN);
        getOrCreateTagBuilder(CornDelightTags.Items.CORN_SEEDS).add(CornDelightItems.CORN_SEEDS);
        getOrCreateTagBuilder(CornDelightTags.Items.COOKED_CHICKEN).add(Items.COOKED_CHICKEN);
        getOrCreateTagBuilder(CornDelightTags.Items.COOKED_BEEF).add(Items.COOKED_BEEF);
        getOrCreateTagBuilder(CornDelightTags.Items.COOKED_MUTTON).add(Items.COOKED_MUTTON);
        getOrCreateTagBuilder(CornDelightTags.Items.COOKED_PORK).add(Items.COOKED_PORKCHOP);
        getOrCreateTagBuilder(CornDelightTags.Items.CORN_DELIGHT_MEAT)
                .addTag(CornDelightTags.Items.COOKED_CHICKEN)
                .addTag(CornDelightTags.Items.COOKED_BEEF)
                .addTag(CornDelightTags.Items.COOKED_MUTTON)
                .addTag(CornDelightTags.Items.COOKED_PORK);

        getOrCreateTagBuilder(TagsRegistry.WILD_CROPS_ITEM).add(CornDelightBlocks.WILD_CORN.asItem());
    }
}
