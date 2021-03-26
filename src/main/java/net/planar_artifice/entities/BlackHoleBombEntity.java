package net.planar_artifice.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.planar_artifice.PlanarRegistry;
import net.planar_artifice.util.RayTrace;

import java.util.HashSet;
import java.util.Set;

public class BlackHoleBombEntity extends Entity{
	
	public BlackHoleBombEntity(World world){
		this(PlanarRegistry.BLACK_HOLE_BOMB_ENTITY, world);
	}
	
	public BlackHoleBombEntity(EntityType<?> type, World world){
		super(type, world);
	}
	
	public void tick(){
		super.tick();
		
		if(world.isClient && age > 2){
			// Add particles
			world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY(), getZ(), world.random.nextGaussian() / 16, 0.1, world.random.nextGaussian() / 16);
			world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY(), getZ(), world.random.nextGaussian() / 12, 0.1, world.random.nextGaussian() / 12);
			world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, getX(), getY(), getZ(), world.random.nextGaussian() / 9, 0.1, world.random.nextGaussian() / 9);
			world.addParticle(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), world.random.nextGaussian() / 4, 0.1, world.random.nextGaussian() / 4);
		}
		
		// Gravity
		if(!hasNoGravity())
			setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
		move(MovementType.SELF, getVelocity());
		setVelocity(getVelocity().multiply(0.98D));
		
		if(horizontalCollision || verticalCollision){
			// Explode when touching something
			remove();
			explodeAndCondense();
		}
	}
	
	protected void explodeAndCondense(){
		//   Server:
		// Gather all affected blocks
		Set<BlockPos> blocks = new HashSet<>();
		final int amnt = 66 * 3;
		for(int x = -amnt; x < amnt; x++)
			for(int y = -amnt; y < amnt; y++)
				for(int z = -amnt; z < amnt; z++){
					Vec3d origin = new Vec3d(getX(), getY(), getZ());
					int endX = (int)getX() + x, endY = (int)getY() + y, endZ = (int)getZ() + z;
					Vec3d end = new Vec3d(endX, endY, endZ);
					BlockPos e = new BlockPos(endX, endY, endZ);
					// If I'm too far, just skip
					if(origin.squaredDistanceTo(end) > (amnt / 6f) * (amnt / 6f))
						continue;
					// If this block can be reached from the centre, destroy it
					float power = amnt;
					// Air can't be destroyed
					if(world.getBlockState(e).isAir())
						continue;
					// "Go to" the block and take away resistance along the way
					for(BlockPos pos : RayTrace.iterateBlocksAlongRay(origin, end, (int)power)){
						power -= Math.max(6, world.getBlockState(pos).getBlock().getBlastResistance());
						if(power < 0)
							break;
					}
					if(power >= 0)
						blocks.add(e);
				}
		// Spawn Black Hole to hold items
		// Remove blocks
		for(BlockPos block : blocks)
			world.setBlockState(block, Blocks.AIR.getDefaultState());
		// Kill entities
		
		//   Client:
		// Make sound and appear
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