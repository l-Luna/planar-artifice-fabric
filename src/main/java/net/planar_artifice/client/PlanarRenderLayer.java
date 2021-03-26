package net.planar_artifice.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.util.Identifier;
import net.planar_artifice.mixin.RenderPhaseAccessor;

public class PlanarRenderLayer{
	
	public static RenderLayer getRift(int layer){
		RenderPhase.Transparency transparency2;
		RenderPhase.Texture texture2;
		if(layer <= 1){
			transparency2 = RenderPhaseAccessor.getTRANSLUCENT_TRANSPARENCY();
			texture2 = new RenderPhase.Texture(EndPortalBlockEntityRenderer.SKY_TEXTURE, false, false);
		}else{
			transparency2 = RenderPhaseAccessor.getADDITIVE_TRANSPARENCY();
			texture2 = new RenderPhase.Texture(GravityGuidePartEntityRenderer.RIFT_TEXTURE, false, false);
		}
		
		return RenderLayer.of("planar_artifice:rift", VertexFormats.POSITION_COLOR, 7, 256, false, true, RenderLayer.MultiPhaseParameters.builder().transparency(transparency2).texture(texture2).texturing(new RenderPhase.PortalTexturing(layer)).fog(RenderPhaseAccessor.getBLACK_FOG()).build(false));
	}
}