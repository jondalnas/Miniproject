package com.JoL.Miniproject.entities;

import java.awt.Color;

public class Enemy extends GravityEntity {
	public Entity target;
	
	public double speed = 128;
	
	protected boolean dead;
	
	public Enemy(Player player) {
		super(Color.RED, 64, 64);
		target = player;
	}
	
	public void tick() {
		if (dead) return;
		
		if (target.x + 64 < x) dx = -speed;
		else if (target.x > x + 64) dx = speed;
		else dx = 0;
		
		super.tick();
	}

	public void kill() {
		dead = true;
	}
}
