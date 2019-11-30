package com.JoL.Miniproject.entities;

import java.awt.Color;

import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.colliders.Line;

public class Bullet extends Entity {
	private double xv, yv;
	private final Line col = new Line(0,0,0,0);
	private final Entity owner;

	public Bullet(double xv, double yv, Entity owner) {
		super(Color.BLACK, 4, 4);
		
		this.owner = owner;
		
		this.xv = xv;
		this.yv = yv;
	}

	public void tick() {
		double dx = xv * Main.deltaTime();
		double dy = yv * Main.deltaTime();
		
		col.update(x, y, x+dx, y+dy);
		
		boolean hit = false;
		for (Entity e : level.collideEntity(col)) {
			if (e != owner) {
				hit = true;
				
				if (e instanceof Player) {
					level.removeEntity(this);
					((Player) e).kill();
				}
			}
		}
		
		if (!hit) {
			x += dx;
			y += dy;
		}
	}
}
