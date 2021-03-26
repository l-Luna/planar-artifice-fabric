package net.planar_artifice.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;
import net.planar_artifice.PlanarRegistry;

public class GravityGuidePartEntity extends Entity{
	
	public GravityGuidePartEntity(World world){
		super(PlanarRegistry.GRAVITY_GUIDE_PART, world);
	}
	
	public GravityGuidePartEntity(EntityType<?> type, World world){
		super(type, world);
	}
	
	protected void initDataTracker(){
	}
	protected void readCustomDataFromTag(CompoundTag tag){
	}
	protected void writeCustomDataToTag(CompoundTag tag){
	}
	
	public Packet<?> createSpawnPacket(){
		return new EntitySpawnS2CPacket(this);
	}
}