package net.planar_artifice.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.planar_artifice.mixin_ducks.BbemAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer.BEAM_TEXTURE;

@Environment(EnvType.CLIENT)
@Mixin(BeaconBlockEntityRenderer.class)
public abstract class BeaconBlockEntityRendererMixin extends BlockEntityRenderer<BeaconBlockEntity>{
	
	public BeaconBlockEntityRendererMixin(BlockEntityRenderDispatcher dispatcher){
		super(dispatcher);
	}
	
	@Redirect(method = "render(Lnet/minecraft/block/entity/BeaconBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
	          at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/BeaconBlockEntityRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;FJII[F)V"))
	private void renderProxy(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, float f, long l, int i, int j, float[] fs,         BeaconBlockEntity beaconBlockEntity, float fOrig, MatrixStack matrixStackOrig, VertexConsumerProvider vertexConsumerProviderOrig, int iOrig, int jOrig){
		// j = height
		BbemAccess basinLevelled = (BbemAccess)beaconBlockEntity;
		if(basinLevelled.getBasinLevel() != -1)
			j = basinLevelled.getBasinLevel() - beaconBlockEntity.getPos().getY();
		renderLightBeam(matrixStack, vertexConsumerProvider, BEAM_TEXTURE, f, 1.0F, l, i, j, fs, 0.2F, 0.25F);
	}
	
	@Shadow
	public static void renderLightBeam(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, Identifier identifier, float f, float g, long l, int i, int j, float[] fs, float h, float k){}
	
	@Inject(method = "renderLightBeam(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/util/Identifier;FFJII[FFF)V",
	        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", shift = At.Shift.AFTER/*, ordinal = 0*/))
	private static void offsetLightBeam(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, Identifier identifier, float f, float g, long l, int i, int j, float[] fs, float h, float k, CallbackInfo ci){
		matrixStack.translate(0, .25, 0);
	}
}