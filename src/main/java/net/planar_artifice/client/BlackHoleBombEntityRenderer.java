package net.planar_artifice.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.planar_artifice.entities.BlackHoleBombEntity;

import static net.planar_artifice.PlanarArtifice.planarId;

public class BlackHoleBombEntityRenderer extends EntityRenderer<BlackHoleBombEntity>{
	
	public BlackHoleBombEntityRenderer(EntityRenderDispatcher dispatcher){
		super(dispatcher);
	}
	
	public Identifier getTexture(BlackHoleBombEntity entity){
		return planarId("null");
	}
	
	public void render(BlackHoleBombEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light){}
}