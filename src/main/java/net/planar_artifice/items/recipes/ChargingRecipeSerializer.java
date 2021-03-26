package net.planar_artifice.items.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class ChargingRecipeSerializer implements RecipeSerializer<ChargingRecipe>{
	
	public ChargingRecipe read(Identifier id, JsonObject json){
		JsonElement jsonElement = JsonHelper.hasArray(json, "ingredient") ? JsonHelper.getArray(json, "ingredient") : JsonHelper.getObject(json, "ingredient");
		Ingredient ingredient = Ingredient.fromJson(jsonElement);
		String string2 = JsonHelper.getString(json, "result");
		Identifier outId = new Identifier(string2);
		ItemStack out = new ItemStack(Registry.ITEM.getOrEmpty(outId).orElseThrow(() -> new IllegalStateException("Item: " + string2 + " does not exist")));
		int charge = JsonHelper.getInt(json, "charge", 1000);
		return new ChargingRecipe(id, ingredient, out, charge);
	}
	
	public ChargingRecipe read(Identifier id, PacketByteBuf buf){
		Ingredient ingredient = Ingredient.fromPacket(buf);
		ItemStack out = buf.readItemStack();
		int charge = buf.readInt();
		return new ChargingRecipe(id, ingredient, out, charge);
	}
	
	public void write(PacketByteBuf buf, ChargingRecipe recipe){
		recipe.in.write(buf);
		buf.writeItemStack(recipe.out);
		buf.writeInt(recipe.charge);
	}
}