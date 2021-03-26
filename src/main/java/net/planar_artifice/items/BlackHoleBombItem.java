package net.planar_artifice.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.planar_artifice.entities.BlackHoleBombEntity;

public class BlackHoleBombItem extends Item{
	
	public BlackHoleBombItem(Settings settings){
		super(settings);
	}
	
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){
		if(user != null){
			if(!user.abilities.creativeMode)
				user.getStackInHand(hand).decrement(1);
			BlackHoleBombEntity bhbEntity = new BlackHoleBombEntity(world);
			bhbEntity.updatePosition(user.getX(), user.getEyeY(), user.getZ());
			
			float f = -MathHelper.sin(user.yaw * 0.017453292F) * MathHelper.cos(user.pitch * 0.017453292F);
			float g = -MathHelper.sin(user.pitch * 0.017453292F);
			float h = MathHelper.cos(user.yaw * 0.017453292F) * MathHelper.cos(user.pitch * 0.017453292F);
			
			bhbEntity.addVelocity(f * 2, g * 2, h * 2);
			world.spawnEntity(bhbEntity);
			return TypedActionResult.success(user.getStackInHand(hand));
		}
		return super.use(world, null, hand);
	}
}