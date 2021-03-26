package net.planar_artifice.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.planar_artifice.entities.GravityGuidePartEntity;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static net.planar_artifice.PlanarArtifice.planarId;

public class GravityGuidePartEntityRenderer extends EntityRenderer<GravityGuidePartEntity>{
	
	public static final Identifier RIFT_TEXTURE = planarId("textures/entity/rift.png");
	
	private static final List<RenderLayer> END_PORTAL_LAYERS = IntStream.range(0, 16).mapToObj((i) -> PlanarRenderLayer.getRift(i + 1)).collect(ImmutableList.toImmutableList());
	
	public GravityGuidePartEntityRenderer(EntityRenderDispatcher dispatcher){
		super(dispatcher);
	}
	
	public Identifier getTexture(GravityGuidePartEntity entity){
		return planarId("null");
	}
	
	public void render(GravityGuidePartEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light){
		// So let's start with this
		// Just a cube
		// And then we'll add fancy effects until it becomes a cool magic cube
		double squaredDist = new BlockPos(entity.getPos()).getSquaredDistance(dispatcher.camera.getPos(), true);
		int layers = layers(squaredDist);
		matrices.push();
		matrices.scale(4, 4, 4);
		Matrix4f model = matrices.peek().getModel();
		
		VertexConsumer layer = vertexConsumers.getBuffer(END_PORTAL_LAYERS.get(0));
		cube(entity, .1f, model, layer);
		
		for(int l = 1; l < layers; ++l)
			cube(entity, 1f / (l + 1), model, vertexConsumers.getBuffer(END_PORTAL_LAYERS.get(l)));
		
		matrices.pop();
	}
	
	private void cube(GravityGuidePartEntity entity, float brightness, Matrix4f model, VertexConsumer vertexConsumer){
		float r = MathHelper.abs((float)(Math.sin((float)Math.toRadians(entity.world.getTime())) * .7f * brightness + .2f));
		float g = MathHelper.abs((float)(Math.sin((float)Math.toRadians(entity.world.getTime() + 360 / 3f)) * .7f * brightness + .2f));
		float b = MathHelper.abs((float)(Math.sin((float)Math.toRadians(entity.world.getTime() + (360 * 2) / 3f)) * .7f * brightness + .2f));
		quad(model, vertexConsumer, 0, 1, 0, 1, 1, 1, 1, 1, r, g, b);
		quad(model, vertexConsumer, 0, 1, 1, 0, 0, 0, 0, 0, r, g, b);
		quad(model, vertexConsumer, 1, 1, 1, 0, 0, 1, 1, 0, r, g, b);
		quad(model, vertexConsumer, 0, 0, 0, 1, 0, 1, 1, 0, r, g, b);
		quad(model, vertexConsumer, 0, 1, 0, 0, 0, 0, 1, 1, r, g, b);
		quad(model, vertexConsumer, 0, 1, 1, 1, 1, 1, 0, 0, r, g, b);
	}
	
	private void quad(Matrix4f model, VertexConsumer vertexConsumer, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, float red, float green, float blue){
		vertexConsumer.vertex(model, x0, y0, z0).color(red, green, blue, 1).normal(x0, y0, z0).light(1).overlay(0).texture(0, 0).next();
		vertexConsumer.vertex(model, x1, y0, z1).color(red, green, blue, 1).normal(x1, y0, z1).light(1).overlay(0).texture(0, 0).next();
		vertexConsumer.vertex(model, x1, y1, z2).color(red, green, blue, 1).normal(x1, y1, z2).light(1).overlay(0).texture(0, 0).next();
		vertexConsumer.vertex(model, x0, y1, z3).color(red, green, blue, 1).normal(x0, y1, z3).light(1).overlay(0).texture(0, 0).next();
	}
	
	protected int layers(double squaredDist){
		if(squaredDist > 36864)
			return 1;
		else if(squaredDist > 25600)
			return 3;
		else if(squaredDist > 16384)
			return 5;
		else if(squaredDist > 9216)
			return 7;
		else if(squaredDist > 4096)
			return 9;
		else if(squaredDist > 1024)
			return 11;
		else if(squaredDist > 576)
			return 13;
		return squaredDist > 256 ? 14 : 15;
	}
}