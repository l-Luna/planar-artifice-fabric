package net.planar_artifice.client;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static net.planar_artifice.PlanarArtifice.planarId;

public class PlanarWorldRenderer{
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public static RenderPhase.Target PRISM_OUTLINE_BUFFER = new RenderPhase.Target(
			"planar_artifice:prism_outline_target",
			() -> getPrismOutlinesFramebuffer().beginWrite(false),
			() -> MinecraftClient.getInstance().getFramebuffer().beginWrite(false));
	
	private static Framebuffer prismOutlinesFramebuffer;
	private static ShaderEffect prismOutlineShader;
	
	public static Framebuffer getPrismOutlinesFramebuffer(){
		return prismOutlinesFramebuffer;
	}
	
	public static ShaderEffect getPrismOutlineShader(){
		return prismOutlineShader;
	}
	
	public static void close(){
		if(prismOutlineShader != null)
			prismOutlineShader.close();
	}
	
	public static void loadPrismOutlineShader(){
		if(prismOutlineShader != null)
			prismOutlineShader.close();
		
		Identifier shader = planarId("shaders/post/rainbow_outline.json");
		MinecraftClient client = MinecraftClient.getInstance();
		
		try{
			prismOutlineShader = new ShaderEffect(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), shader);
			prismOutlineShader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
			prismOutlinesFramebuffer = prismOutlineShader.getSecondaryTarget("final");
		}catch(IOException var3){
			LOGGER.warn("Failed to load shader: {}", shader, var3);
			prismOutlineShader = null;
			prismOutlinesFramebuffer = null;
		}catch(JsonSyntaxException var4){
			LOGGER.warn("Failed to parse shader: {}", shader, var4);
			prismOutlineShader = null;
			prismOutlinesFramebuffer = null;
		}
	}
	
	public static void onResized(int width, int height){
		if(prismOutlineShader != null)
			prismOutlineShader.setupDimensions(width, height);
	}
	
	public static void drawPrismOutlinesFramebuffer(){
		if(prismOutlineShader != null){
			/*MinecraftClient client = MinecraftClient.getInstance();
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
			prismOutlinesFramebuffer.draw(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight(), false);
			RenderSystem.disableBlend();*/
		}
	}
}