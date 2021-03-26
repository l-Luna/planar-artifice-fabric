package net.planar_artifice;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.registry.Registry;
import net.planar_artifice.blocks.AuraBasinBlock;
import net.planar_artifice.blocks.AuraBasinBlockEntity;
import net.planar_artifice.entities.BlackHoleBombEntity;
import net.planar_artifice.entities.GravityGuidePartEntity;
import net.planar_artifice.items.*;
import net.planar_artifice.items.recipes.ChargingRecipe;
import net.planar_artifice.items.recipes.ChargingRecipeSerializer;
import net.planar_artifice.mixin.ItemMixin;
import net.planar_artifice.statuses.SetBonusStatusEffect;

import java.util.ArrayList;
import java.util.List;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.of;
import static net.minecraft.util.registry.Registry.register;
import static net.planar_artifice.PlanarArtifice.planarId;

public class PlanarRegistry{
	
	// For BlockItems
	private static final List<Pair<Identifier, Block>> WITH_ITEMS = new ArrayList<>();
	
	// Items
	// General crafting
	// Dimensional Curio stores 350 charge
	public static final Item DIMENSIONAL_CURIO = new ChargedItem(new Item.Settings().group(PlanarArtifice.ITEMS).maxDamage(150));
	public static final Item DIMENSIONAL_SINGULAR = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).recipeRemainder(DIMENSIONAL_CURIO).rarity(Rarity.UNCOMMON));
	public static final Item EFFERVESCENT_INGOT = new Item(new Item.Settings().group(PlanarArtifice.ITEMS));
	public static final Item GLASS_GLOBE = new Item(new Item.Settings().group(PlanarArtifice.ITEMS));
	
	// Boss crafting
	public static final Item ROCK_FEATHER = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).rarity(Rarity.UNCOMMON));
	
	// Misc
	//public static final Item BOOK_OF_PLANES = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1));
	public static final Item SNOW_GLOBE = new Item(new Item.Settings().group(PlanarArtifice.ITEMS));
	
	// Armour
	public static final Item GILDED_HELMET = new ArmorItem(PlanarArmorMaterials.GILDED, EquipmentSlot.HEAD, new Item.Settings().group(PlanarArtifice.ITEMS));
	public static final Item GILDED_CHESTPLATE = new ArmorItem(PlanarArmorMaterials.GILDED, EquipmentSlot.CHEST, new Item.Settings().group(PlanarArtifice.ITEMS));
	public static final Item GILDED_LEGGINGS = new ArmorItem(PlanarArmorMaterials.GILDED, EquipmentSlot.LEGS, new Item.Settings().group(PlanarArtifice.ITEMS));
	public static final Item GILDED_BOOTS = new ArmorItem(PlanarArmorMaterials.GILDED, EquipmentSlot.FEET, new Item.Settings().group(PlanarArtifice.ITEMS));
	
	// Equipment
	public static final Item BLACK_HOLE_BOMB = new BlackHoleBombItem(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(16).rarity(Rarity.UNCOMMON));
	public static final Item REMOTE_GATEWAY = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item PROJECTOR_DRILL = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item VOID_DRILL = new VoidDrillItem(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item GRAVITY_GLOBE = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item WARP_GLOBE = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item LOOKING_GLASS = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item MIRRORED_AMULET = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item NORTH_STAR = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item JET_HARNESS = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item VOID_CHAINSAW = new VoidDrillItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE)){
		protected void handleEntityHit(EntityHitResult result, PlayerEntity player){
			Entity entity = result.getEntity();
			if(entity instanceof LivingEntity && !entity.isInvulnerableTo(DamageSource.GENERIC)){
				LivingEntity living = (LivingEntity)entity;
				living.damage(DamageSource.GENERIC, 2f);
				living.timeUntilRegen -= 1;
				living.hurtTime -= 1;
			}
		}
	};
	public static final Item BARRIER_PLATFORM = new BarrierPlatformItem(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item WATERPROOF_BARRIER_PLATFORM = new BarrierPlatformItem(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item LAVAPROOF_BARRIER_PLATFORM = new BarrierPlatformItem(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	public static final Item GRAVITY_GUIDE = new Item(new Item.Settings().group(PlanarArtifice.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON));
	
	// Blocks
	public static final Block AURA_BASIN = new AuraBasinBlock(of(Material.METAL).breakByHand(true).strength(6f).nonOpaque().solidBlock((state, world, pos) -> false));
	
	// Status effects
	public static final StatusEffect ETHEREAL = new SetBonusStatusEffect(0xFFFFFF);
	public static final StatusEffect GILDED_SET = new SetBonusStatusEffect(0xFFFFFF)
			.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "83491d5c-b4be-11ea-b3de-0242ac130004", 1, EntityAttributeModifier.Operation.ADDITION)
			.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "97e7c790-b4be-11ea-b3de-0242ac130004", 1, EntityAttributeModifier.Operation.ADDITION);
	
	// Entities
	public static final EntityType<Entity> BLACK_HOLE_BOMB_ENTITY = FabricEntityTypeBuilder
			.create(SpawnGroup.MISC, BlackHoleBombEntity::new)
			.dimensions(EntityDimensions.fixed(.5f, .5f))
			.build();
	public static final EntityType<Entity> GRAVITY_GUIDE_PART = FabricEntityTypeBuilder
			.create(SpawnGroup.MISC, GravityGuidePartEntity::new)
			.dimensions(EntityDimensions.changing(1, 1))
			.build();
	
	// Block Entity types
	public static final BlockEntityType<AuraBasinBlockEntity> AURA_BASIN_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.create(AuraBasinBlockEntity::new, AURA_BASIN).build(null);
	
	// Recipe Serializers and Types
	public static final RecipeSerializer<ChargingRecipe> CHARGING_RECIPE_SERIALIZER = new ChargingRecipeSerializer();
	public static final RecipeType<ChargingRecipe> CHARGING_RECIPE_TYPE = new RecipeType<ChargingRecipe>(){
		public String toString(){
			return "planar_artifice:charging";
		}
	};
	
	public static void registerObjects(){
		// Items
		((ItemMixin)DIMENSIONAL_CURIO).setRecipeRemainder(DIMENSIONAL_CURIO);
		register(Registry.ITEM, planarId("dimensional_curiosity"), DIMENSIONAL_CURIO);
		register(Registry.ITEM, planarId("dimensional_singularity"), DIMENSIONAL_SINGULAR);
		register(Registry.ITEM, planarId("effervescent_metal_ingot"), EFFERVESCENT_INGOT);
		register(Registry.ITEM, planarId("glass_globe"), GLASS_GLOBE);
		register(Registry.ITEM, planarId("rock_feather"), ROCK_FEATHER);
		//register(Registry.ITEM, planarId("book_of_planes"), BOOK_OF_PLANES);
		register(Registry.ITEM, planarId("snow_globe"), SNOW_GLOBE);
		register(Registry.ITEM, planarId("gilded_helmet"), GILDED_HELMET);
		register(Registry.ITEM, planarId("gilded_chestplate"), GILDED_CHESTPLATE);
		register(Registry.ITEM, planarId("gilded_leggings"), GILDED_LEGGINGS);
		register(Registry.ITEM, planarId("gilded_boots"), GILDED_BOOTS);
		register(Registry.ITEM, planarId("black_hole_bomb"), BLACK_HOLE_BOMB);
		register(Registry.ITEM, planarId("remote_gateway"), REMOTE_GATEWAY);
		register(Registry.ITEM, planarId("projector_drill"), PROJECTOR_DRILL);
		register(Registry.ITEM, planarId("void_drill"), VOID_DRILL);
		register(Registry.ITEM, planarId("gravity_globe"), GRAVITY_GLOBE);
		register(Registry.ITEM, planarId("warp_globe"), WARP_GLOBE);
		register(Registry.ITEM, planarId("looking_glass"), LOOKING_GLASS);
		register(Registry.ITEM, planarId("mirrored_amulet"), MIRRORED_AMULET);
		register(Registry.ITEM, planarId("north_star"), NORTH_STAR);
		register(Registry.ITEM, planarId("jet_harness"), JET_HARNESS);
		register(Registry.ITEM, planarId("void_chainsaw"), VOID_CHAINSAW);
		register(Registry.ITEM, planarId("barrier_platform"), BARRIER_PLATFORM);
		register(Registry.ITEM, planarId("waterproof_barrier_platform"), WATERPROOF_BARRIER_PLATFORM);
		register(Registry.ITEM, planarId("lavaproof_barrier_platform"), LAVAPROOF_BARRIER_PLATFORM);
		register(Registry.ITEM, planarId("gravity_guide"), GRAVITY_GUIDE);
		
		// Blocks and BlockItems
		WITH_ITEMS.add(new Pair<>(planarId("aura_basin"), AURA_BASIN));
		
		for(Pair<Identifier, Block> item : WITH_ITEMS){
			register(Registry.BLOCK, item.getLeft(), item.getRight());
			register(Registry.ITEM, item.getLeft(), new BlockItem(item.getRight(), new Item.Settings().group(PlanarArtifice.ITEMS)));
		}
		
		// Status Effects
		register(Registry.STATUS_EFFECT, planarId("ethereal"), ETHEREAL);
		register(Registry.STATUS_EFFECT, planarId("gilded_aura"), GILDED_SET);
		
		// Entity types
		register(Registry.ENTITY_TYPE, planarId("black_hole_bomb_entity"), BLACK_HOLE_BOMB_ENTITY);
		register(Registry.ENTITY_TYPE, planarId("gravity_guide_part"), GRAVITY_GUIDE_PART);
		
		// Block entity types
		register(Registry.BLOCK_ENTITY_TYPE, planarId("aura_basin_block_entity"), AURA_BASIN_BLOCK_ENTITY_TYPE);
		
		// Recipe Serializers and Types
		Registry.register(Registry.RECIPE_SERIALIZER, planarId("charging"), CHARGING_RECIPE_SERIALIZER);
		Registry.register(Registry.RECIPE_TYPE, planarId("charging"), CHARGING_RECIPE_TYPE);
	}
}