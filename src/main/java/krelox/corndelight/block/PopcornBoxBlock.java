package krelox.corndelight.block;

import com.nhoryzon.mc.farmersdelight.block.FeastBlock;
import krelox.corndelight.CornDelight;
import krelox.corndelight.item.CornDelightItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PopcornBoxBlock extends FeastBlock {
    public PopcornBoxBlock() {
        super(AbstractBlock.Settings.copy(Blocks.YELLOW_WOOL), CornDelightItems.CARAMEL_POPCORN, true);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) {
            if (this.takeServing(world, pos, state, player, hand).isAccepted()) {
                return ActionResult.SUCCESS;
            }
        }
        return this.takeServing(world, pos, state, player, hand);
    }

    public ActionResult takeServing(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand) {
        int servings = state.get(SERVINGS);

        if (servings == 0) {
            world.playSound(null, pos, SoundEvents.BLOCK_WOOL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
            world.removeBlock(pos, true);
            return ActionResult.SUCCESS;
        }

        ItemStack serving = new ItemStack(CornDelightItems.CARAMEL_POPCORN);
        ItemStack heldStack = player.getStackInHand(hand);

        if (servings > 0) {
            if (heldStack.isEmpty()) {
                world.setBlockState(pos, state.with(SERVINGS, servings - 1), 3);
                if (!player.getInventory().insertStack(serving)) {
                    player.dropItem(serving, false);
                }
                if (world.getBlockState(pos).get(SERVINGS) == 0) {
                    if (!player.getInventory().insertStack(new ItemStack(Items.PAPER))) {
                        player.dropItem(new ItemStack(Items.PAPER), false);
                    }
                    world.removeBlock(pos, false);
                }
                world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            } else {
                player.sendMessage(Text.translatable(CornDelight.MODID + ".block.popcorn.bearhand", serving.getItem().getRecipeRemainder().getName()), true);
            }
        }
        return ActionResult.PASS;
    }
}
