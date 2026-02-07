package krelox.corndelight.block;

import krelox.corndelight.CornDelight;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class PopcornBoxBlock extends FeastBlock {

    private static final Identifier SERVING_ID = Identifier.of(CornDelight.MODID, "caramel_popcorn");

    public PopcornBoxBlock(AbstractBlock.Settings settings) {
        // (settings, serving item supplier, hasLeftovers)
        super(settings, () -> Registries.ITEM.get(SERVING_ID), false);
    }

    @Override
    public ActionResult onUseWithItem(ItemStack heldStack, BlockState state, World world, BlockPos pos,
                                      PlayerEntity player, Hand hand, BlockHitResult hit) {

        if (world.isClient()) {
            int servings = state.get(SERVINGS);
            if (servings > 0 && heldStack.isEmpty()) return ActionResult.SUCCESS;
            if (servings == 0) return ActionResult.SUCCESS;
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }

        return this.takeServing(world, pos, state, player);
    }

    public ActionResult takeServing(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        int servings = state.get(SERVINGS);

        if (servings == 0) {
            world.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
            world.removeBlock(pos, true);
            return ActionResult.SUCCESS;
        }

        ItemStack serving = new ItemStack(Registries.ITEM.get(SERVING_ID));
        ItemStack heldStack = player.getMainHandStack();

        if (!heldStack.isEmpty()) {
            player.sendMessage(Text.translatable(CornDelight.MODID + ".block.popcorn.barehand"), true);
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
        }

        world.setBlockState(pos, state.with(SERVINGS, servings - 1), 3);

        if (!player.getInventory().insertStack(serving)) {
            player.dropItem(serving, false);
        }

        if (world.getBlockState(pos).get(SERVINGS) == 0) {
            ItemStack paper = new ItemStack(Items.PAPER);
            if (!player.getInventory().insertStack(paper)) {
                player.dropItem(paper, false);
            }
            world.removeBlock(pos, false);
        }

        world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.value(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        return ActionResult.SUCCESS;
    }

    @Override
    public ItemStack getServingItem(BlockState state) {
        return new ItemStack(Registries.ITEM.get(SERVING_ID));
    }
}
