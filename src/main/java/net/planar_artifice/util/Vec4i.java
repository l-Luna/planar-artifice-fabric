package net.planar_artifice.util;

public class Vec4i{
	
	private final int x;
	private final int y;
	private final int z;
	private final int w;
	
	public Vec4i(int x, int y, int z, int w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	public int getW(){
		return w;
	}
}