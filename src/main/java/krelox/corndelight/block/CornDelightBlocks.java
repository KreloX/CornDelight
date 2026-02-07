package krelox.corndelight.block;

import krelox.corndelight.CornDelight;
import krelox.corndelight.item.CornDelightItems;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.WildCropBlock;

import java.util.function.Function;

public class CornDelightBlocks {

    // --- Blocks (no block-item) ---
    public static final Block CORN_CROP = registerBlock("corn_crop",
            settings -> new CornCropBlock(settings),
            AbstractBlock.Settings.copy(Blocks.WHEAT)
    );

//    public static final Block NACHOS_BLOCK = registerBlock("nachos_block",
//            settings -> new FeastBlock(settings, () -> CornDelightItems.NACHOS_BOWL, true) {
//                private static final VoxelShape PLATE_SHAPE =
//                        Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);
//
//                private static final VoxelShape NACHOS_SHAPE = VoxelShapes.combine(
//                        PLATE_SHAPE,
//                        Block.createCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 5.0D, 14.0D),
//                        BooleanBiFunction.OR
//                );
//
//                @Override
//                public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
//                    return state.get(SERVINGS) == 0 ? PLATE_SHAPE : NACHOS_SHAPE;
//                }
//
//                @Override
//                public net.minecraft.item.ItemStack getServingItem(BlockState state) {
//                    return new net.minecraft.item.ItemStack(CornDelightItems.NACHOS_BOWL);
//                }
//            },
//            AbstractBlock.Settings.copy(Blocks.CAKE)
//    );

    // --- Blocks WITH block-items ---
    public static final Block WILD_CORN = registerBlockWithItem("wild_corn",
            settings -> new WildCropBlock(StatusEffects.SATURATION, 8, settings),
            AbstractBlock.Settings.copy(Blocks.TALL_GRASS),
            baseItem()
    );

    public static final Block CORN_CRATE = registerBlockWithItem("corn_crate",
            Block::new,
            AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD),
            baseItem()
    );

    public static final Block CORN_KERNEL_BAG = registerBlockWithItem("corn_kernel_bag",
            Block::new,
            AbstractBlock.Settings.copy(Blocks.WHITE_WOOL),
            baseItem()
    );


    public static final Block POPCORN_BOX = registerBlockWithItem("popcorn_box",
            PopcornBoxBlock::new,
            AbstractBlock.Settings.copy(Blocks.BARREL),
            baseItem()
    );

    // -------------------------
    // Registration helpers
    // -------------------------

    private static Item.Settings baseItem() {
        return new Item.Settings();
    }

    private static <T extends Block> T registerBlock(String name,
                                                     Function<AbstractBlock.Settings, T> factory,
                                                     AbstractBlock.Settings settings) {
        Identifier id = Identifier.of(CornDelight.MODID, name);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);

        // Critical for 1.21.2+:
        settings.registryKey(key);

        T block = factory.apply(settings);
        return Registry.register(Registries.BLOCK, id, block);
    }

    private static <T extends Block> T registerBlockWithItem(String name,
                                                             Function<AbstractBlock.Settings, T> factory,
                                                             AbstractBlock.Settings blockSettings,
                                                             Item.Settings itemSettings) {
        T block = registerBlock(name, factory, blockSettings);
        registerBlockItem(name, block, itemSettings);
        return block;
    }

    private static void registerBlockItem(String name, Block block, Item.Settings settings) {
        Identifier id = Identifier.of(CornDelight.MODID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

        settings.registryKey(key);
        settings.useBlockPrefixedTranslationKey();

        Registry.register(Registries.ITEM, id, new BlockItem(block, settings));
    }

    public static void registerBlocks() {
        // no-op, just here if you want a clean init call
    }
}
