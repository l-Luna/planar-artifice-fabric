package net.planar_artifice.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.math.Matrix4f;
import net.planar_artifice.client.PlanarWorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin{
	
	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V",
	        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 12))
	private void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci){
		PlanarWorldRenderer.getPrismOutlineShader().render(tickDelta);
		MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
	}
	
	@Inject(method = "onResized",
	        at = @At("TAIL"))
	private void onResized(int i, int j, CallbackInfo ci){
		PlanarWorldRenderer.onResized(i, j);
	}
	
	@Inject(method = "close",
	        at = @At("TAIL"))
	private void close(CallbackInfo ci){
		PlanarWorldRenderer.close();
	}
	
	@Inject(method = "apply",
	        at = @At("TAIL"))
	private void apply(ResourceManager manager, CallbackInfo ci){
		PlanarWorldRenderer.loadPrismOutlineShader();
	}
	
	@Inject(method = "drawEntityOutlinesFramebuffer",
	        at = @At("TAIL"))
	private void drawEntityOutlinesFramebuffer(CallbackInfo ci){
		PlanarWorldRenderer.drawPrismOutlinesFramebuffer();
	}
}