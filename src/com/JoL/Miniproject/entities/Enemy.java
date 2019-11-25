package com.JoL.Miniproject.entities;

import java.awt.Color;

public class Enemy extends GravityEntity {
	public Entity target;
	
	public double speed = 128;
	
	public Enemy(Player player) {
		super(Color.RED, 64, 64);
		target = player;
	}
	
	public void tick() {
		if (target.x < x + 64) dx = -speed;
		if (target.x + 64 > x) dx = speed;
		
		super.tick();
	}
}
