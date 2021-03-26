package net.planar_artifice.items;

import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.planar_artifice.PlanarArtifice;
import net.planar_artifice.util.RayTrace;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VoidDrillItem extends Item{
	
	public static final Tag<Block> VOID_IMMUNE = TagRegistry.block(new Identifier(PlanarArtifice.MODID, "void_immune"));
	
	public VoidDrillItem(Settings settings){
		super(settings);
	}
	
	public UseAction getUseAction(ItemStack stack){
		return UseAction.BLOCK;
	}
	
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks){
		super.usageTick(world, user, stack, remainingUseTicks);
		if(user instanceof PlayerEntity && !user.isSneaky()){
			PlayerEntity player = (PlayerEntity)user;
			HitResult result = RayTrace.rayTrace(player, false, 6);//user.rayTrace(6, 1, false);
			if(result instanceof BlockHitResult)
				handleBlockHit((BlockHitResult)result, player);
			else if(result instanceof EntityHitResult)
				handleEntityHit((EntityHitResult)result, player);
		}
	}
	
	protected void handleBlockHit(BlockHitResult result, PlayerEntity player){
		BlockPos pos = result.getBlockPos();
		// get all relevant blocks
		getRelevantLocations(pos, getMode(player.getActiveItem()), player.getHorizontalFacing()).forEach(loc -> {
			if(!player.world.getBlockState(loc).getBlock().isIn(VOID_IMMUNE))
				player.world.breakBlock(loc, false, player);
		});
	}
	
	protected void handleEntityHit(EntityHitResult result, PlayerEntity player){}
	
	enum DrillMode{
		SINGLE,
		SQUARE,
		CUBE,
		INVALID_STACK;
		
		static final DrillMode[] modes = values();
	}
	
	public static DrillMode getMode(ItemStack stack){
		// if stack is not a void drill, its invalid
		if(!(stack.getItem() instanceof VoidDrillItem))
			return DrillMode.INVALID_STACK;
		int mode = stack.getOrCreateTag().getInt("mode");
		if(mode > 2)
			return DrillMode.INVALID_STACK;
		else
			return DrillMode.modes[mode];
	}
	
	public static Set<BlockPos> getRelevantLocations(BlockPos pos, DrillMode mode, Direction facing){
		if(mode == DrillMode.SINGLE)
			return Collections.singleton(pos);
		if(mode == DrillMode.SQUARE)
			return Sets.newHashSet(pos, pos.up(), pos.down(), pos.offset(facing.rotateYClockwise()), pos.offset(facing.rotateYCounterclockwise()), pos.offset(facing.rotateYClockwise()).up(), pos.offset(facing.rotateYCounterclockwise()).up(), pos.offset(facing.rotateYClockwise()).down(), pos.offset(facing.rotateYCounterclockwise()).down());
		if(mode == DrillMode.CUBE){
			Set<BlockPos> ret = new HashSet<>();
			BlockPos centre = pos.offset(facing);
			for(int x = -1; x < 2; x++)
				for(int y = -1; y < 2; y++)
					for(int z = -1; z < 2; z++)
						ret.add(centre.up(y).west(x).north(z));
			return ret;
		}else
			return Collections.emptySet();
	}
	
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand){
		ItemStack stack = player.getStackInHand(hand);
		player.setCurrentHand(hand);
		if(player.isSneaky()){
			// get current mode (single block, 3x3, 3x3x3)
			DrillMode mode = getMode(stack);
			if(mode != DrillMode.INVALID_STACK){
				// add one
				int newMode = mode.ordinal() + 1;
				if(newMode > 2)
					newMode = 0;
				stack.getOrCreateTag().putInt("mode", newMode);
				if(!player.world.isClient())
					player.sendMessage(new TranslatableText("item.planar_artifice.void_drill.mode." + newMode), true);
			}
		}
		return TypedActionResult.consume(stack);
	}
	
	public int getMaxUseTime(ItemStack stack){
		return 72000;
	}
	
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context){
		super.appendTooltip(stack, world, tooltip, context);
		DrillMode mode = getMode(stack);
		if(mode != DrillMode.INVALID_STACK)
			tooltip.add(new TranslatableText("item.planar_artifice.void_drill.mode." + mode.ordinal()).formatted(Formatting.AQUA));
	}
}