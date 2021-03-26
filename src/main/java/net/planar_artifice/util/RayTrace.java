package net.planar_artifice.util;

import it.unimi.dsi.fastutil.objects.ObjectIterators;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Optional;

public class RayTrace{
	
	/**
	 * Copy of https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/api/internal/MathHelper.java.
	 * But lazy.
	 */
	public static Iterable<BlockPos> iterateBlocksAlongRay(Vec3d origin, Vec3d end, int maxBlocks){
		if(origin.equals(end))
			return () -> ObjectIterators.singleton(new BlockPos(origin.x, origin.y, origin.z));
		double endX = end.x;
		double endY = end.y;
		double endZ = end.z;
		double originX = origin.x;
		double originY = origin.y;
		double originZ = origin.z;
		final int[] blockX = {MathHelper.floor(originX)};
		final int[] blockY = {MathHelper.floor(originY)};
		final int[] blockZ = {MathHelper.floor(originZ)};
		BlockPos.Mutable blockPos = new BlockPos.Mutable(blockX[0], blockY[0], blockZ[0]);
		double lengthX = endX - originX;
		double lengthY = endY - originY;
		double lengthZ = endZ - originZ;
		int signumX = (int)Math.signum(lengthX);
		int signumY = (int)Math.signum(lengthY);
		int signumZ = (int)Math.signum(lengthZ);
		double stepSizeX = signumX == 0 ? Double.MAX_VALUE : (double)signumX / lengthX;
		double stepSizeY = signumY == 0 ? Double.MAX_VALUE : (double)signumY / lengthY;
		double stepSizeZ = signumZ == 0 ? Double.MAX_VALUE : (double)signumZ / lengthZ;
		final double[] totalStepsX = {stepSizeX * (signumX > 0 ? 1.0D - MathHelper.fractionalPart(originX) : MathHelper.fractionalPart(originX))};
		final double[] totalStepsY = {stepSizeY * (signumY > 0 ? 1.0D - MathHelper.fractionalPart(originY) : MathHelper.fractionalPart(originY))};
		final double[] totalStepsZ = {stepSizeZ * (signumZ > 0 ? 1.0D - MathHelper.fractionalPart(originZ) : MathHelper.fractionalPart(originZ))};
		
		return () -> new Iterator<BlockPos>(){
			int count = 0;
			
			public boolean hasNext(){
				return (totalStepsX[0] <= 1.0D || totalStepsY[0] <= 1.0D || totalStepsZ[0] <= 1.0D) && count != maxBlocks;
			}
			
			public BlockPos next(){
				if(count == 0){
					count++;
					return blockPos.toImmutable();
				}
				if(totalStepsX[0] < totalStepsY[0]){
					if(totalStepsX[0] < totalStepsZ[0]){
						blockX[0] += signumX;
						totalStepsX[0] += stepSizeX;
					}else{
						blockZ[0] += signumZ;
						totalStepsZ[0] += stepSizeZ;
					}
				}else if(totalStepsY[0] < totalStepsZ[0]){
					blockY[0] += signumY;
					totalStepsY[0] += stepSizeY;
				}else{
					blockZ[0] += signumZ;
					totalStepsZ[0] += stepSizeZ;
				}
				blockPos.set(blockX[0], blockY[0], blockZ[0]);
				count++;
				return blockPos.toImmutable();
			}
		};
	}
	
	public static HitResult rayTrace(Entity from, boolean includeFluids, float maxDistance){
		Vec3d direction = from.getRotationVec(1);
		World world = from.world;
		Vec3d loc = from.getCameraPosVec(1);
		Vec3d toVec = loc.add(direction.x * maxDistance, direction.y * maxDistance, direction.z * maxDistance);
		HitResult hitResult = world.rayTrace(new RayTraceContext(loc, toVec, RayTraceContext.ShapeType.OUTLINE, includeFluids ? RayTraceContext.FluidHandling.ANY : RayTraceContext.FluidHandling.NONE, from));
		if(hitResult.getType() != HitResult.Type.MISS)
			toVec = hitResult.getPos();
		
		HitResult entityHitResult = getEntityCollision(world, from, loc, toVec);
		if(entityHitResult != null)
			hitResult = entityHitResult;
		
		return hitResult;
	}
	
	public static EntityHitResult getEntityCollision(World world, Entity entity, Vec3d pos, Vec3d to){
		double d = Double.MAX_VALUE;
		Entity entity2 = null;
		
		for(Entity entity3 : world.getEntities(entity, new Box(pos, to))){
			Box box2 = entity3.getBoundingBox().expand(0.3);
			Optional<Vec3d> optional = box2.rayTrace(pos, to);
			if(optional.isPresent()){
				double e = pos.squaredDistanceTo(optional.get());
				if(e < d){
					entity2 = entity3;
					d = e;
				}
			}
		}
		
		if(entity2 == null)
			return null;
		return new EntityHitResult(entity2);
	}
}