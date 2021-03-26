package net.planar_artifice.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.planar_artifice.PlanarRegistry;

import static net.planar_artifice.PlanarArtifice.planarId;

@SuppressWarnings("unused")
public class ClientInit implements ClientModInitializer{
	
	public void onInitializeClient(){
		// Entity renderers
		EntityRendererRegistry.INSTANCE.register(PlanarRegistry.BLACK_HOLE_BOMB_ENTITY, (entityRenderDispatcher, context) -> new BlackHoleBombEntityRenderer(entityRenderDispatcher));
		EntityRendererRegistry.INSTANCE.register(PlanarRegistry.GRAVITY_GUIDE_PART, (entityRenderDispatcher, context) -> new GravityGuidePartEntityRenderer(entityRenderDispatcher));
		
		// TERs
		BlockEntityRendererRegistry.INSTANCE.register(PlanarRegistry.AURA_BASIN_BLOCK_ENTITY_TYPE, dispatcher -> new AuraBasinBlockEntityRenderer(dispatcher, MinecraftClient.getInstance().getItemRenderer()));
		
		// Model predicates
		FabricModelPredicateProviderRegistry.register(PlanarRegistry.VOID_DRILL, planarId("active"), (stack, world, entity) -> ((entity != null) && (entity.getActiveItem() == stack)) ? 1f : 0.0F);
		FabricModelPredicateProviderRegistry.register(PlanarRegistry.VOID_CHAINSAW, planarId("active"), (stack, world, entity) -> ((entity != null) && (entity.getActiveItem() == stack)) ? 1f : 0.0F);
		
		// Render Layers
		BlockRenderLayerMap.INSTANCE.putBlock(PlanarRegistry.AURA_BASIN, RenderLayer.getCutout());
	}
}