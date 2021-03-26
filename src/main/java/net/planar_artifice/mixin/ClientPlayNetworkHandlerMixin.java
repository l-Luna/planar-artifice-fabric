package net.planar_artifice.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.planar_artifice.PlanarRegistry;
import net.planar_artifice.entities.BlackHoleBombEntity;
import net.planar_artifice.entities.GravityGuidePartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin{
	
	@Shadow
	private ClientWorld world;
	
	@Inject(method = "onEntitySpawn(Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;)V", at = @At("TAIL"))
	public void onEntitySpawn(EntitySpawnS2CPacket packet, CallbackInfo ci){
		EntityType<?> entityType = packet.getEntityTypeId();
		Entity entity = null;
		if(entityType == PlanarRegistry.BLACK_HOLE_BOMB_ENTITY)
			entity = new BlackHoleBombEntity(entityType, world);
		else if(entityType == PlanarRegistry.GRAVITY_GUIDE_PART)
			entity = new GravityGuidePartEntity(entityType, world);
		if(entity != null){
			double x = packet.getX();
			double y = packet.getY();
			double z = packet.getZ();
			int i = packet.getId();
			entity.updateTrackedPosition(x, y, z);
			entity.refreshPositionAfterTeleport(x, y, z);
			entity.pitch = (packet.getPitch() * 360) / 256.0F;
			entity.yaw = (packet.getYaw() * 360) / 256.0F;
			entity.setEntityId(i);
			entity.setUuid(packet.getUuid());
			world.addEntity(i, entity);
		}
	}
}