package net.planar_artifice.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

import static net.minecraft.entity.effect.StatusEffects.JUMP_BOOST;
import static net.planar_artifice.PlanarRegistry.GILDED_SET;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{
	
	@Shadow
	@Final
	private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;
	
	public LivingEntityMixin(EntityType<?> type, World world){
		super(type, world);
	}
	
	@Inject(method = "hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z", at = @At("HEAD"), cancellable = true)
	public void hasStatusEffect(StatusEffect effect, CallbackInfoReturnable<Boolean> cir){
		if(((effect == StatusEffects.SLOW_FALLING && !isSneaky()) || effect == JUMP_BOOST) && activeStatusEffects.containsKey(GILDED_SET))
			cir.setReturnValue(true);
	}
	
	// TODO: un-hardcode
	@Inject(method = "getStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Lnet/minecraft/entity/effect/StatusEffectInstance;", at = @At("HEAD"), cancellable = true)
	public void getStatusEffect(StatusEffect effect, CallbackInfoReturnable<StatusEffectInstance> cir){
		if(effect == StatusEffects.SLOW_FALLING && !isSneaky()){
			if(activeStatusEffects.containsKey(GILDED_SET))
				cir.setReturnValue(activeStatusEffects.get(GILDED_SET));
		}else if(effect == JUMP_BOOST){
			if(activeStatusEffects.containsKey(GILDED_SET)){
				// if jump boost has higher power, it should take priority
				if(activeStatusEffects.containsKey(JUMP_BOOST) && activeStatusEffects.get(JUMP_BOOST).getAmplifier() > activeStatusEffects.get(GILDED_SET).getAmplifier())
					cir.setReturnValue(activeStatusEffects.get(JUMP_BOOST));
				else
					cir.setReturnValue(activeStatusEffects.get(GILDED_SET));
			}
		}
	}
}