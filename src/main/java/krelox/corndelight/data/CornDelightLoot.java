package krelox.corndelight.data;

import com.nhoryzon.mc.farmersdelight.block.FeastBlock;
import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.CropBlock;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.StatePredicate;

public class CornDelightLoot extends FabricBlockLootTableProvider {
    private static final LootCondition.Builder WITH_SHEARS = MatchToolLootCondition.builder(net.minecraft.predicate.item.ItemPredicate.Builder.create().items(Items.SHEARS));
    public CornDelightLoot(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        LootCondition.Builder corn_builder = BlockStatePropertyLootCondition.builder(CornDelightBlocks.CORN_CROP)
                .properties(StatePredicate.Builder.create().exactMatch(CropBlock.AGE, 7));
        addDrop(CornDelightBlocks.CORN_CROP, block -> cropDrops(block, CornDelightItems.CORN, CornDelightItems.CORN_SEEDS, corn_builder));

        addDrop(CornDelightBlocks.WILD_CORN, block -> applyExplosionDecay(block, LootTable.builder()
                .pool(LootPool.builder().conditionally(WITH_SHEARS.invert()).conditionally(RandomChanceLootCondition.builder(0.2F))
                        .with(ItemEntry.builder(CornDelightItems.CORN)))
                .pool(LootPool.builder()
                        .with(ItemEntry.builder(CornDelightBlocks.WILD_CORN).conditionally(WITH_SHEARS)
                                .alternatively(ItemEntry.builder(CornDelightItems.CORN_SEEDS))))));

        addDrop(CornDelightBlocks.CORN_CRATE);
        addDrop(CornDelightBlocks.CORN_KERNEL_BAG);

        BlockStatePropertyLootCondition.Builder nachos_builder = BlockStatePropertyLootCondition.builder(CornDelightBlocks.NACHOS)
                .properties(StatePredicate.Builder.create().exactMatch(FeastBlock.SERVINGS, 4));
        addDrop(CornDelightBlocks.NACHOS, block -> applyExplosionDecay(block, LootTable.builder()
                .pool(LootPool.builder().with(ItemEntry.builder(block).conditionally(nachos_builder)
                        .alternatively(ItemEntry.builder(Items.BOWL))))));
    }
}
