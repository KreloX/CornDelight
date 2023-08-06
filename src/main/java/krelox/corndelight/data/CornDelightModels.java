package krelox.corndelight.data;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import krelox.corndelight.CornDelight;
import krelox.corndelight.block.CornCropBlock;
import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CornDelightModels extends FabricModelProvider {
    public CornDelightModels(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        registerTallCrop(blockStateModelGenerator, CornDelightBlocks.CORN_CROP, CornCropBlock.UPPER, CornCropBlock.AGE);

        blockStateModelGenerator.registerTintableCross(CornDelightBlocks.WILD_CORN, BlockStateModelGenerator.TintType.NOT_TINTED);

        TextureMap cornCrateMap = new TextureMap()
                .put(TextureKey.SIDE, new Identifier(CornDelight.MODID, "block/corn_crate_side"))
                .put(TextureKey.BOTTOM, new Identifier(FarmersDelightMod.MOD_ID, "block/crate_bottom"))
                .put(TextureKey.TOP, new Identifier(CornDelight.MODID, "block/corn_crate_top"));
        blockStateModelGenerator.registerSingleton(CornDelightBlocks.CORN_CRATE, cornCrateMap,
                TexturedModel.CUBE_BOTTOM_TOP.get(CornDelightBlocks.CORN_CRATE).getModel());

        TextureMap kernelBagMap = new TextureMap()
                .put(TextureKey.PARTICLE, new Identifier(CornDelight.MODID, "block/corn_kernel_bag_top"))
                .put(TextureKey.DOWN, new Identifier(FarmersDelightMod.MOD_ID, "block/rice_bag_bottom"))
                .put(TextureKey.UP, new Identifier(CornDelight.MODID, "block/corn_kernel_bag_top"))
                .put(TextureKey.NORTH, new Identifier(FarmersDelightMod.MOD_ID, "block/rice_bag_side_tied"))
                .put(TextureKey.SOUTH, new Identifier(FarmersDelightMod.MOD_ID, "block/rice_bag_side_tied"))
                .put(TextureKey.EAST, new Identifier(FarmersDelightMod.MOD_ID, "block/rice_bag_side"))
                .put(TextureKey.WEST, new Identifier(FarmersDelightMod.MOD_ID, "block/rice_bag_side"));
        blockStateModelGenerator.registerSingleton(CornDelightBlocks.CORN_KERNEL_BAG, kernelBagMap, Models.CUBE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (Item item : CornDelightItems.items) {
            if (!(item instanceof BlockItem))
                itemModelGenerator.register(item, Models.GENERATED);
        }
        itemModelGenerator.register(CornDelightItems.CORN_SEEDS, Models.GENERATED);
        itemModelGenerator.register(CornDelightItems.NACHOS, Models.GENERATED);
        itemModelGenerator.register(CornDelightBlocks.POPCORN_BOX.asItem(), new Model(Optional.of(new Identifier(CornDelight.MODID,
                "block/popcorn_box_stage0")), Optional.empty()));
    }

    public final void registerTallCrop(BlockStateModelGenerator blockStateModelGenerator, Block crop, Property<Boolean> booleanProperty, Property<Integer> ageProperty) {
        BlockStateVariantMap blockStateVariantMap = BlockStateVariantMap.create(ageProperty, booleanProperty).register((integer, upper) -> {
            Identifier identifier = blockStateModelGenerator.createSubModel(crop, (upper ? "_top" : "") + "_stage" + integer, Models.CROP, TextureMap::crop);
            return BlockStateVariant.create().put(VariantSettings.MODEL, identifier);
        });
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop).coordinate(blockStateVariantMap));
    }
}
