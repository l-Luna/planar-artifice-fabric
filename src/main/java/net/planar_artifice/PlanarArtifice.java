package net.planar_artifice;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.planar_artifice.items.PlanarArmorMaterials;

@SuppressWarnings("unused")
public class PlanarArtifice implements ModInitializer{
	
	public static final String MODID = "planar_artifice";
	
	public static final ItemGroup ITEMS = FabricItemGroupBuilder
			.create(planarId("items"))
			.icon(() -> new ItemStack(PlanarRegistry.DIMENSIONAL_SINGULAR))
			.build();
	
	@Override
	public void onInitialize(){
		PlanarRegistry.registerObjects();
		
		ServerTickEvents.END_WORLD_TICK.register(world -> {
			for(PlayerEntity player : world.getPlayers()){
				int matched = 0;
				ArmorMaterial setBonusMat = null;
				for(ItemStack item : player.getArmorItems())
					if(item.getItem() instanceof ArmorItem){
						ArmorItem armorItem = (ArmorItem)item.getItem();
						// if it matches the correct set
						if(setBonusMat == armorItem.getMaterial() || setBonusMat == null){
							setBonusMat = armorItem.getMaterial();
							matched++;
						}
					}
				if(matched == 4 && setBonusMat instanceof PlanarArmorMaterials)
					player.addStatusEffect(new StatusEffectInstance(((PlanarArmorMaterials)setBonusMat).getSetBonus(), 10, 0, true, false, true));
			}
		});
	}
	
	public static Identifier planarId(String path){
		return new Identifier(MODID, path);
	}
}