package net.planar_artifice.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class AuraBasinBlock extends Block implements BlockEntityProvider{
	
	public AuraBasinBlock(Settings settings){
		super(settings);
	}
	
	public BlockEntity createBlockEntity(BlockView world){
		return new AuraBasinBlockEntity();
	}
	
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit){
		ItemStack playerStack = player.getStackInHand(hand);
		BlockEntity entity = world.getBlockEntity(pos);
		if(entity instanceof AuraBasinBlockEntity){
			AuraBasinBlockEntity basin = (AuraBasinBlockEntity)entity;
			boolean noBasinItem = basin.stack.isEmpty();
			boolean noPlayerItem = playerStack.isEmpty();
			// If someone has an item
			if(noBasinItem != noPlayerItem){
				if(noBasinItem){
					// put the playerStack in the basin
					if(!player.abilities.creativeMode)
						playerStack.decrement(1);
					basin.stack = playerStack.copy();
					basin.stack.setCount(1);
				}else{
					// take the basin's stack and give to player
					player.setStackInHand(hand, basin.stack);
					basin.stack = ItemStack.EMPTY;
				}
			}
		}
		return ActionResult.CONSUME;
	}
}