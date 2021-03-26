package net.planar_artifice.blocks;

import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.planar_artifice.PlanarRegistry;

public class AuraBasinBlockEntity extends BlockEntity implements Inventory, Tickable{
	
	public static final int CHARGE_PER_TICK = 3;
	public static final int BONUS_CHARGE_PER_LEVEL = 1;
	public static final String CHARGE_TAG = "planar_artifice:charge";
	
	ItemStack stack = ItemStack.EMPTY;
	int beaconLevel = -1;
	
	public AuraBasinBlockEntity(){
		super(PlanarRegistry.AURA_BASIN_BLOCK_ENTITY_TYPE);
	}
	
	public void tick(){
		// Check for beacons below
		// The beacon checks for me in the mixin but whatever
		checkForBeacons();
		// If there is a beacon, charge item
		if(!stack.isEmpty() && beaconLevel != -1 && getWorld() != null){
			BeaconBlockEntity entity = (BeaconBlockEntity)getWorld().getBlockEntity(new BlockPos(getPos().getX(), beaconLevel, getPos().getZ()));
			if(entity != null){
				int chargeAdded = entity.getLevel() * BONUS_CHARGE_PER_LEVEL + CHARGE_PER_TICK;
				stack.getOrCreateTag().putInt(CHARGE_TAG, stack.getOrCreateTag().getInt(CHARGE_TAG) + chargeAdded);
				// Check for recipes
				getWorld().getRecipeManager().getFirstMatch(PlanarRegistry.CHARGING_RECIPE_TYPE, this, this.world).ifPresent(recipe -> stack = recipe.craft(this));
			}
		}
	}
	
	protected void checkForBeacons(){
		boolean found = false;
		if(world != null){
			BlockPos.Mutable pos = new BlockPos.Mutable();
			for(int y = getPos().getY(); y > 0; y--){
				pos.set(getPos().getX(), y, getPos().getZ());
				BlockEntity entity = world.getBlockEntity(pos);
				if(entity instanceof BeaconBlockEntity){
					BeaconBlockEntity beacon = (BeaconBlockEntity)entity;
					if(beacon.getLevel() > 0){
						found = true;
						beaconLevel = y;
						break;
					}
				}
			}
		}
		if(!found)
			beaconLevel = -1;
	}
	
	public ItemStack getStack(){
		return stack;
	}
	
	public int size(){
		return 1;
	}
	
	public boolean isEmpty(){
		return stack.isEmpty();
	}
	
	public ItemStack getStack(int slot){
		return slot == 1 ? stack : ItemStack.EMPTY;
	}
	
	public ItemStack removeStack(int slot, int amount){
		if(slot == 0 && amount > 0){
			ItemStack ret = stack;
			stack = ItemStack.EMPTY;
			return ret.split(amount);
		}
		return ItemStack.EMPTY;
	}
	
	public ItemStack removeStack(int slot){
		if(slot == 0){
			ItemStack ret = stack;
			stack = ItemStack.EMPTY;
			return ret;
		}
		return ItemStack.EMPTY;
	}
	
	public void setStack(int slot, ItemStack stack){
		if(slot == 0){
			this.stack = stack;
		}
	}
	
	public boolean canPlayerUse(PlayerEntity player){
		return true;
	}
	
	public void clear(){
		stack = ItemStack.EMPTY;
	}
}