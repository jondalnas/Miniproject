package com.JoL.Miniproject.colliders;

public abstract class Collider {
	public double x, y;
	
	public abstract boolean collide(Collider c);
}
