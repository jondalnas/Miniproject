package com.JoL.Miniproject.entities;

import java.awt.Color;

import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.colliders.Line;

public class Bullet extends Entity {
	private double xv, yv;
	private final Line col = new Line(0,0,0,0);
	private double alive, aliveTime = 2.5;
	private final Entity owner;

	public Bullet(double xv, double yv, Entity owner) {
		super(Color.BLACK, 4, 4);
		
		this.owner = owner;
		
		this.xv = xv;
		this.yv = yv;
	}

	public void tick() {
		alive += Main.deltaTime();
		
		if (alive > aliveTime) {
			level.removeEntity(this);
			return;
		}
		
		double dx = xv * Main.deltaTime();
		double dy = yv * Main.deltaTime();
		
		col.update(x, y, x+dx, y+dy);
		
		boolean hit = false;
		for (Entity e : level.collideEntity(col)) {
			if (e != owner && e != this) {
				hit = true;
				
				if (e instanceof Player) {
					((Player) e).kill();
				} else if (e instanceof Enemy) {
					((Enemy) e).kill();
				}
			}
		}
		
		if (level.collideLevel(col)) hit = true;
		
		if (!hit) {
			x += dx;
			y += dy;
		} else {
			level.removeEntity(this);
		}
	}
}
