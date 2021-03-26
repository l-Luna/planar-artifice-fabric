package net.planar_artifice.mixin;

import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.planar_artifice.blocks.AuraBasinBlockEntity;
import net.planar_artifice.mixin_ducks.BbemAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeaconBlockEntity.class)
public abstract class BeaconBlockEntityMixin extends BlockEntity implements BbemAccess{
	
	@Unique
	private int basinLevel = -1;
	
	public BeaconBlockEntityMixin(BlockEntityType<?> type){
		super(type);
	}
	
	public int getBasinLevel(){
		return basinLevel;
	}
	
	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo ci){
		checkForBasins();
	}
	
	@Unique
	private void checkForBasins(){
		boolean found = false;
		BlockPos.Mutable pos = new BlockPos.Mutable();
		for(int y = getPos().getY(); y < 256; y++){
			pos.set(getPos().getX(), y, getPos().getZ());
			BlockEntity entity = world.getBlockEntity(pos);
			if(entity instanceof AuraBasinBlockEntity){
				AuraBasinBlockEntity auraBasin = (AuraBasinBlockEntity)entity;
				if(!auraBasin.getStack().isEmpty()){
					found = true;
					basinLevel = y;
					break;
				}
			}
		}
		if(!found)
			basinLevel = -1;
	}
}
