package net.planar_artifice.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;
import net.planar_artifice.PlanarRegistry;

import java.util.function.Supplier;

public enum PlanarArmorMaterials implements ArmorMaterial{
	
	GILDED("planar_artifice_gilded", 25, new int[]{2, 5, 5, 4}, 30, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1, 0, () -> Ingredient.ofItems(PlanarRegistry.EFFERVESCENT_INGOT), () -> PlanarRegistry.GILDED_SET),
	;
	
	private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantability;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Lazy<Ingredient> repairIngredientSupplier;
	private final Lazy<StatusEffect> set;
	
	PlanarArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> supplier, Supplier<StatusEffect> set){
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantability = enchantability;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredientSupplier = new Lazy<>(supplier);
		this.set = new Lazy<>(set);
	}
	
	public int getDurability(EquipmentSlot slot){
		return BASE_DURABILITY[slot.getEntitySlotId()] * durabilityMultiplier;
	}
	
	public int getProtectionAmount(EquipmentSlot slot){
		return protectionAmounts[slot.getEntitySlotId()];
	}
	
	public int getEnchantability(){
		return enchantability;
	}
	
	public SoundEvent getEquipSound(){
		return equipSound;
	}
	
	public Ingredient getRepairIngredient(){
		return repairIngredientSupplier.get();
	}
	
	@Environment(EnvType.CLIENT)
	public String getName(){
		return name;
	}
	
	public float getToughness(){
		return toughness;
	}
	
	public float getKnockbackResistance(){
		return knockbackResistance;
	}
	
	public StatusEffect getSetBonus(){
		return set.get();
	}
}