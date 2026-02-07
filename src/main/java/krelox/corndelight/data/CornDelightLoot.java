package krelox.corndelight.data;

import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.CropBlock;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryWrapper;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.concurrent.CompletableFuture;

public class CornDelightLoot extends FabricBlockLootTableProvider {
    public CornDelightLoot(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        var WITH_SHEARS = createWithShearsCondition();

        LootCondition.Builder cornBuilder = BlockStatePropertyLootCondition.builder(CornDelightBlocks.CORN_CROP)
                .properties(StatePredicate.Builder.create().exactMatch(CropBlock.AGE, 7));
        addDrop(CornDelightBlocks.CORN_CROP, block -> cropDrops(block, CornDelightItems.CORN, CornDelightItems.CORN_SEEDS, cornBuilder));

        addDrop(CornDelightBlocks.WILD_CORN, block -> applyExplosionDecay(block, LootTable.builder()
                .pool(LootPool.builder().conditionally(WITH_SHEARS.invert()).conditionally(RandomChanceLootCondition.builder(0.2F))
                        .with(ItemEntry.builder(CornDelightItems.CORN)))
                .pool(LootPool.builder()
                        .with(ItemEntry.builder(CornDelightBlocks.WILD_CORN).conditionally(WITH_SHEARS)
                                .alternatively(ItemEntry.builder(CornDelightItems.CORN_SEEDS))))));

        addDrop(CornDelightBlocks.CORN_CRATE, this::drops);
        addDrop(CornDelightBlocks.CORN_KERNEL_BAG, this::drops);

//        BlockStatePropertyLootCondition.Builder nachosBuilder = BlockStatePropertyLootCondition.builder(CornDelightBlocks.NACHOS_BLOCK)
//                .properties(StatePredicate.Builder.create().exactMatch(FeastBlock.SERVINGS, 4));
//        addDrop(CornDelightBlocks.NACHOS_BLOCK, block -> applyExplosionDecay(block, LootTable.builder()
//                .pool(LootPool.builder().with(ItemEntry.builder(block).conditionally(nachosBuilder)
//                        .alternatively(ItemEntry.builder(Items.BOWL))))));
    }
}
