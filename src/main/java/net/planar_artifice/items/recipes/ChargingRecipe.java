package net.planar_artifice.items.recipes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.planar_artifice.PlanarRegistry;
import net.planar_artifice.blocks.AuraBasinBlockEntity;

public class ChargingRecipe implements Recipe<AuraBasinBlockEntity>{
	
	Identifier id;
	
	// item + charge level
	Ingredient in;
	ItemStack out;
	int charge;
	
	public ChargingRecipe(Identifier id, Ingredient in, ItemStack out, int charge){
		this.id = id;
		this.in = in;
		this.out = out;
		this.charge = charge;
	}
	
	public boolean matches(AuraBasinBlockEntity inv, World world){
		return in.test(inv.getStack()) && inv.getStack().getOrCreateTag().getInt(AuraBasinBlockEntity.CHARGE_TAG) >= charge;
	}
	
	public ItemStack craft(AuraBasinBlockEntity inv){
		return out.copy();
	}
	
	@Environment(EnvType.CLIENT)
	public boolean fits(int width, int height){
		return true;
	}
	
	public ItemStack getOutput(){
		return out;
	}
	
	public Identifier getId(){
		return id;
	}
	
	public RecipeSerializer<?> getSerializer(){
		return PlanarRegistry.CHARGING_RECIPE_SERIALIZER;
	}
	
	public RecipeType<?> getType(){
		return PlanarRegistry.CHARGING_RECIPE_TYPE;
	}
}