package krelox.corndelight.data;

import net.minecraft.client.render.model.json.WeightedVariant;
import vectorwing.farmersdelight.FarmersDelight;
import krelox.corndelight.CornDelight;
import krelox.corndelight.block.CornCropBlock;
import krelox.corndelight.block.CornDelightBlocks;
import krelox.corndelight.item.CornDelightItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
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

        blockStateModelGenerator.registerTintableCross(CornDelightBlocks.WILD_CORN, BlockStateModelGenerator.CrossType.NOT_TINTED);

        blockStateModelGenerator.registerSingleton(
                CornDelightBlocks.CORN_CRATE,
                TexturedModel.CUBE_BOTTOM_TOP.andThen(textures -> {
                    textures.put(TextureKey.SIDE,   Identifier.of(CornDelight.MODID, "block/corn_crate_side"));
                    textures.put(TextureKey.BOTTOM, Identifier.of(FarmersDelight.MODID, "block/crate_bottom"));
                    textures.put(TextureKey.TOP,    Identifier.of(CornDelight.MODID, "block/corn_crate_top"));
                })
        );


        TexturedModel.Factory kernelBagFactory = TexturedModel.makeFactory(
                b -> new TextureMap()
                        .put(TextureKey.PARTICLE, Identifier.of(CornDelight.MODID, "block/corn_kernel_bag_top"))
                        .put(TextureKey.DOWN, Identifier.of(FarmersDelight.MODID, "block/rice_bag_bottom"))
                        .put(TextureKey.UP, Identifier.of(CornDelight.MODID, "block/corn_kernel_bag_top"))
                        .put(TextureKey.NORTH, Identifier.of(FarmersDelight.MODID, "block/rice_bag_side_tied"))
                        .put(TextureKey.SOUTH, Identifier.of(FarmersDelight.MODID, "block/rice_bag_side_tied"))
                        .put(TextureKey.EAST, Identifier.of(FarmersDelight.MODID, "block/rice_bag_side"))
                        .put(TextureKey.WEST, Identifier.of(FarmersDelight.MODID, "block/rice_bag_side")),
                Models.CUBE
        );

        blockStateModelGenerator.registerSingleton(CornDelightBlocks.CORN_KERNEL_BAG, kernelBagFactory);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (Item item : CornDelightItems.ITEMS) {
            if (!(item instanceof BlockItem))
                itemModelGenerator.register(item, Models.GENERATED);
        }
        itemModelGenerator.register(CornDelightItems.CORN_SEEDS, Models.GENERATED);
        // itemModelGenerator.register(CornDelightItems.NACHOS_BLOCK, Models.GENERATED);
        itemModelGenerator.register(CornDelightBlocks.POPCORN_BOX.asItem(), new Model(Optional.of(Identifier.of(CornDelight.MODID,
                "block/popcorn_box_stage0")), Optional.empty()));
    }

    public static void registerTallCrop(BlockStateModelGenerator blockStateModelGenerator, Block crop, Property<Boolean> upperProp, Property<Integer> ageProp) {
        BlockStateVariantMap<WeightedVariant> modelMap =
                BlockStateVariantMap.models(ageProp, upperProp).generate((age, upper) -> {
                    Identifier modelId = blockStateModelGenerator.createSubModel(
                            crop,
                            (upper ? "_top" : "") + "_stage" + age,
                            Models.CROP,
                            TextureMap::crop
                    );
                    return BlockStateModelGenerator.createWeightedVariant(modelId);
                });

        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockModelDefinitionCreator.of(crop).with(modelMap)
        );
    }
}
