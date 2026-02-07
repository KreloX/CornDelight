package krelox.corndelight.block;

import krelox.corndelight.item.CornDelightItems;
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

    public static final BooleanProperty UPPER = BooleanProperty.of("upper");

    private static final VoxelShape[] SHAPE_TO_AGE = new VoxelShape[] {
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    private static final VoxelShape[] UPPER_SHAPE_TO_AGE = new VoxelShape[] {
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
    };

    public CornCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(AGE, 0).with(UPPER, false));
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return CornDelightItems.CORN_SEEDS;
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
        return state.get(UPPER)
                ? UPPER_SHAPE_TO_AGE[state.get(this.getAgeProperty())]
                : SHAPE_TO_AGE[state.get(this.getAgeProperty())];
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        // Custom rules for the upper half.
        if (state.get(this.getUpperProperty())) {
            BlockPos down = pos.down();
            BlockState below = world.getBlockState(down);
            if (!below.isOf(this)) return false;
            if (below.get(this.getUpperProperty())) return false; // below must be base
            return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos))
                    && this.getAge(below) >= this.getGrowUpperAge();
        }

        // Base uses vanilla CropBlock placement.
        return super.canPlaceAt(state, world, pos);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !state.get(this.getUpperProperty());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(this.getUpperProperty())) return; // safety: upper doesn't tick

        // Ensure upper exists once base reaches the threshold (no RNG delay)
        ensureUpper(world, pos, state);

        if (world.getBaseLightLevel(pos, 0) < 9) return;

        float moisture = getAvailableMoisture(this, world, pos);
        // If upper exists, simulate 2 tickers by making 2 independent growth rolls.
        int rolls = hasUpper(world, pos) ? 2 : 1;

        for (int i = 0; i < rolls; i++) {
            // re-fetch base state in case it changed during the first roll
            BlockState baseState = world.getBlockState(pos);
            int baseAge = this.getAge(baseState);

            // If the whole plant is already fully mature, stop rolling
            if (baseAge >= this.getMaxAge()) {
                BlockState upper = world.getBlockState(pos.up());
                if (!(upper.isOf(this) && upper.get(this.getUpperProperty()) && !this.isMature(upper))) {
                    return;
                }
            }

            if (random.nextInt((int)(25.0F / moisture) + 1) == 0) {
                applyDistributedGrowth(world, pos, baseState, 1, random);
            }
        }
    }

    private boolean hasUpper(WorldView world, BlockPos basePos) {
        BlockState up = world.getBlockState(basePos.up());
        return up.isOf(this) && up.get(this.getUpperProperty());
    }

    private void ensureUpper(ServerWorld world, BlockPos basePos, BlockState baseState) {
        if (baseState.get(this.getUpperProperty())) return; // base only
        if (this.getAge(baseState) < this.getGrowUpperAge()) return;

        BlockPos up = basePos.up();
        if (!world.isAir(up)) return;

        BlockState upper = this.getDefaultState()
                .with(this.getUpperProperty(), true)
                .with(this.getAgeProperty(), 0);

        if (upper.canPlaceAt(world, up)) {
            world.setBlockState(up, upper, 3);
        }
    }

    private void applyDistributedGrowth(ServerWorld world, BlockPos basePos, BlockState baseState, int steps, Random random) {
        int max = this.getMaxAge();

        for (int i = 0; i < steps; i++) {
            int baseAge = this.getAge(baseState);

            // Phase 1: grow base up to the threshold first
            if (baseAge < this.getGrowUpperAge()) {
                int next = Math.min(baseAge + 1, max);
                baseState = baseState.with(this.getAgeProperty(), next).with(this.getUpperProperty(), false);
                world.setBlockState(basePos, baseState, 3);

                // If we just crossed the threshold, spawn upper right away
                ensureUpper(world, basePos, baseState);
                continue;
            }

            // Phase 2: ensure upper exists
            ensureUpper(world, basePos, baseState);

            BlockPos up = basePos.up();
            BlockState upperState = world.getBlockState(up);
            boolean upperOk = upperState.isOf(this) && upperState.get(this.getUpperProperty());

            // If upper can't exist (blocked), just keep base growing normally.
            if (!upperOk) {
                if (baseAge < max) {
                    int next = Math.min(baseAge + 1, max);
                    baseState = baseState.with(this.getAgeProperty(), next).with(this.getUpperProperty(), false);
                    world.setBlockState(basePos, baseState, 3);
                }
                continue;
            }

            int upperAge = this.getAge(upperState);

            boolean baseDone = baseAge >= max;
            boolean upperDone = upperAge >= max;

            // If one side is done, grow the other.
            if (baseDone && !upperDone) {
                upperState = upperState.with(this.getAgeProperty(), Math.min(upperAge + 1, max)).with(this.getUpperProperty(), true);
                world.setBlockState(up, upperState, 3);
                continue;
            }
            if (!baseDone && upperDone) {
                baseState = baseState.with(this.getAgeProperty(), Math.min(baseAge + 1, max)).with(this.getUpperProperty(), false);
                world.setBlockState(basePos, baseState, 3);
                continue;
            }

            // Both still growing: weighted by remaining so they mature around the same time.
            int baseRemaining = max - baseAge;
            int upperRemaining = max - upperAge;

            int roll = random.nextInt(baseRemaining + upperRemaining);
            boolean growUpper = roll < upperRemaining;

            if (growUpper) {
                upperState = upperState.with(this.getAgeProperty(), Math.min(upperAge + 1, max)).with(this.getUpperProperty(), true);
                world.setBlockState(up, upperState, 3);
            } else {
                baseState = baseState.with(this.getAgeProperty(), Math.min(baseAge + 1, max)).with(this.getUpperProperty(), false);
                world.setBlockState(basePos, baseState, 3);
            }
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        BlockPos basePos = state.get(this.getUpperProperty()) ? pos.down() : pos;
        BlockState base = world.getBlockState(basePos);
        if (!base.isOf(this) || base.get(this.getUpperProperty())) return false;

        BlockState upper = world.getBlockState(basePos.up());
        if (upper.isOf(this) && upper.get(this.getUpperProperty())) {
            return !this.isMature(upper);
        }

        return !this.isMature(base) || this.getAge(base) >= this.getGrowUpperAge();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }
    
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos basePos = state.get(this.getUpperProperty()) ? pos.down() : pos;
        BlockState base = world.getBlockState(basePos);
        if (!base.isOf(this) || base.get(this.getUpperProperty())) return;

        int steps = this.getGrowthAmount(world);
        applyDistributedGrowth(world, basePos, base, steps, random);
    }
}

