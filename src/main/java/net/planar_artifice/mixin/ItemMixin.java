package net.planar_artifice.mixin;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.class)
public interface ItemMixin{
	
	@Accessor
	@Mutable
	void setRecipeRemainder(Item remainder);
}