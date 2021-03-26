package net.planar_artifice.mixin;

import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderPhase.class)
public interface RenderPhaseAccessor{
	
	@Accessor
	static RenderPhase.Cull getENABLE_CULLING(){
		throw new UnsupportedOperationException();
	}
	
	@Accessor
	static RenderPhase.Transparency getADDITIVE_TRANSPARENCY(){
		throw new UnsupportedOperationException();
	}
	
	@Accessor
	static RenderPhase.Transparency getTRANSLUCENT_TRANSPARENCY(){
		throw new UnsupportedOperationException();
	}
	
	@Accessor
	static RenderPhase.Fog getBLACK_FOG(){
		throw new UnsupportedOperationException();
	}
	
	@Accessor
	static RenderPhase.Fog getNO_FOG(){
		throw new UnsupportedOperationException();
	}
	
	@Accessor
	static RenderPhase.Texturing getOUTLINE_TEXTURING(){
		throw new UnsupportedOperationException();
	}
	
	@Accessor
	static RenderPhase.Alpha getONE_TENTH_ALPHA(){
		throw new UnsupportedOperationException();
	}
	
	@Accessor
	static RenderPhase.DepthTest getALWAYS_DEPTH_TEST(){
		throw new UnsupportedOperationException();
	}
}
