package net.planar_artifice.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.BlockItem;
import net.planar_artifice.blocks.AuraBasinBlockEntity;

public class AuraBasinBlockEntityRenderer extends BlockEntityRenderer<AuraBasinBlockEntity>{
	
	private final ItemRenderer itemRenderer;
	
	public AuraBasinBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher, ItemRenderer renderer){
		super(dispatcher);
		itemRenderer = renderer;
	}
	
	public void render(AuraBasinBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay){
		matrices.push();
		matrices.scale(.5f, .5f, .5f);
		matrices.translate(1, 1, 1);
		matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
		// render the item
		if(!entity.getStack().isEmpty()){
			if(entity.getStack().getItem() instanceof BlockItem)
				matrices.scale(1.6f, 1.6f, 1.6f);
			itemRenderer.renderItem(entity.getStack(), ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
		}
		matrices.pop();
	}
}
