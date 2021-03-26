package net.planar_artifice.statuses;

import net.minecraft.entity.effect.StatusEffectType;

public class SetBonusStatusEffect extends PlanarStatusEffect{
	
	public SetBonusStatusEffect(int color){
		super(StatusEffectType.BENEFICIAL, color);
	}
	
	public int getColor(){
		return 0;
	}
}