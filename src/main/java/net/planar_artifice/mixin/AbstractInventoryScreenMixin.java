package net.planar_artifice.mixin;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.planar_artifice.statuses.PlanarStatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T>{
	
	@Shadow
	protected boolean drawStatusEffects;
	
	public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title){
		super(handler, inventory, title);
	}
	
	@Inject(method = "applyStatusEffectOffset()V", at = @At("TAIL"))
	protected void applyStatusEffectOffset(CallbackInfo ci){
		// If they're all planar artifice, don't shift
		if(client.player.getStatusEffects().stream().allMatch(instance -> instance.getEffectType() instanceof PlanarStatusEffect)){
			x = (this.width - this.backgroundWidth) / 2;
			drawStatusEffects = true;
		}
	}
}