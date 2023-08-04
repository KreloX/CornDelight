package krelox.corndelight.block;

import com.nhoryzon.mc.farmersdelight.block.FeastBlock;
import com.nhoryzon.mc.farmersdelight.block.WildCropBlock;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import krelox.corndelight.CornDelight;
import krelox.corndelight.item.CornDelightItems;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CornDelightBlocks {

    public static final Block CORN_CROP = registerBlock("corn_crop",
            new CornCropBlock(AbstractBlock.Settings.copy(Blocks.WHEAT), CornDelightItems.CORN_SEEDS));
    public static final Block WILD_CORN = registerBlockWithItem("wild_corn", new WildCropBlock());
    public static final Block CORN_CRATE = registerBlockWithItem("corn_crate",
            new Block(AbstractBlock.Settings.copy(BlocksRegistry.CARROT_CRATE.get())));
    public static final Block CORN_KERNEL_BAG = registerBlockWithItem("corn_kernel_bag",
            new Block(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)));
    public static final Block NACHOS_BLOCK = registerBlockWithItem("nachos_block", new FeastBlock(
            AbstractBlock.Settings.copy(Blocks.CAKE), CornDelightItems.NACHOS_BOWL, true) {
        private static final VoxelShape PLATE_SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 2.0D, 15.0D);
        private static final VoxelShape NACHOS_SHAPE = VoxelShapes.combine(PLATE_SHAPE,
                Block.createCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 5.0D, 14.0D), BooleanBiFunction.OR);

        @Override
        public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return state.get(SERVINGS) == 0 ? PLATE_SHAPE : NACHOS_SHAPE;
        }

        @Override
        public ItemStack getServingStack(BlockState state) {
            return new ItemStack(CornDelightItems.NACHOS_BOWL);
        }
    });
    public static final Block POPCORN_BOX = registerBlockWithItem("popcorn_box", new PopcornBoxBlock());

    private static Block registerBlockWithItem(String name, Block block) {
        CornDelightItems.registerItem(name, new BlockItem(block, new Item.Settings()));
        return registerBlock(name, block);
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(CornDelight.MODID, name), block);
    }

    public static void registerBlocks() {
    }
}
