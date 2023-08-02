package krelox.corndelight.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CornCropBlock extends CropBlock {

    private final ItemConvertible seedItem;

    public static final BooleanProperty UPPER = BooleanProperty.of("upper");

    private static final VoxelShape[] UPPER_SHAPE_BY_AGE = new VoxelShape[]{
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
    };

    public CornCropBlock(Settings settings, ItemConvertible seed) {
        super(settings);
        this.seedItem = seed;
        this.setDefaultState(this.getStateManager().getDefaultState().with(AGE, 0).with(UPPER, false));
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return seedItem;
    }

    public BooleanProperty getUpperProperty() {
        return UPPER;
    }

    public int getGrowUpperAge() {
        return 4;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, UPPER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(UPPER)? UPPER_SHAPE_BY_AGE[state.get(this.getAgeProperty())]: super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos downpos = pos.down();
        if (world.getBlockState(downpos).isOf(this))
            return !world.getBlockState(downpos).get(this.getUpperProperty())
                    && (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos))
                    && this.getAge(world.getBlockState(downpos)) >= this.getGrowUpperAge();
        return super.canPlaceAt(state, world, pos);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = this.getAge(state);
        float f = getAvailableMoisture(this, world, pos);
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            if (age < this.getMaxAge()) {
                if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(age + 1), 2);
                }
            }
        }
        if (state.get(this.getUpperProperty()))
            return;
        if (age >= this.getGrowUpperAge()) {
            if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                if (this.getDefaultState().canPlaceAt(world, pos.up()) && world.isAir(pos.up())) {
                    world.setBlockState(pos, this.withAge(age + 1), 2);
                }
            }
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        BlockState upperState = world.getBlockState(pos.up());
        if (upperState.isOf(this)) {
            return !(this.isMature(upperState));
        }
        if (state.get(this.getUpperProperty())) {
            return !(this.isMature(state));
        }
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int ageGrowth = Math.min(this.getAge(state) + this.getGrowthAmount(world), 15);
        if (ageGrowth <= this.getMaxAge()) {
            world.setBlockState(pos, state.with(AGE, ageGrowth));
        } else {
            world.setBlockState(pos, state.with(AGE, this.getMaxAge()));
            if (state.get(this.getUpperProperty())) {
                return;
            }
            BlockState top = world.getBlockState(pos.up());
            if (top.isOf(this)) {
                Fertilizable growable = (Fertilizable) world.getBlockState(pos.up()).getBlock();
                if (growable.isFertilizable(world, pos.up(), top, false)) {
                    growable.grow(world, world.random, pos.up(), top);
                }
            } else {
                int remainingGrowth = ageGrowth - this.getMaxAge() - 1;
                if (this.getDefaultState().canPlaceAt(world, pos.up()) && world.isAir(pos.up())) {
                    world.setBlockState(pos.up(), this.getDefaultState()
                            .with(this.getUpperProperty(), true)
                            .with(this.getAgeProperty(), remainingGrowth), 3);
                }
            }
        }
    }
}
