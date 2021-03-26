package net.planar_artifice.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.planar_artifice.statuses.SetBonusStatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectUtil.class)
public class StatusEffectUtilMixin{
	
	@Inject(method = "durationToString(Lnet/minecraft/entity/effect/StatusEffectInstance;F)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
	@Environment(EnvType.CLIENT)
	private static void durationToString(StatusEffectInstance effect, float multiplier, CallbackInfoReturnable<String> cir){
		if(effect.getEffectType() instanceof SetBonusStatusEffect)
			cir.setReturnValue(I18n.translate("effect.planar_artifice.set_bonus"));
	}
}