package com.JoL.Miniproject.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.colliders.Collider;
import com.JoL.Miniproject.colliders.Polygon;
import com.JoL.Miniproject.level.Level;

public class Entity {
	public double x, y;
	public double dx, dy;
	public Color entityColor;
	
	public Level level;
	
	protected boolean grounded;
	public Polygon collider;
	protected double width, height;
	public double maxStepHeight = 1;
	
	public Entity(Color color, double width, double height) {
		entityColor = color;
		this.width = width;
		this.height = height;
		
		collider = new Polygon(new double[][] {new double[] {0, 0}, new double[] {width, 0}, new double[] {width, height}, new double[] {0, height}});
	}
	
	public void tick() {
	}
	
	protected void move() {
		if (!move(dx, 0)) dx = 0;
		if (!move(0, dy)) dy = 0;
	}
	
	private boolean move(double dx, double dy) {
		if (dx == 0 && dy == 0) return true;

		List<Collider> colliders = new ArrayList<Collider>();
		
		for (Entity e : level.entities) {
			if (e == this) continue;
			
			e.updateCollider();
			colliders.add(e.collider);
		}
		
		colliders.addAll(level.levelPolys);
		
		//Loop through all distance percents backwards, and check if it collides with 
		for (double i = 1; i > 0; i -= 0.01) {
			double ddx = dx * Main.deltaTime() * i;
			double ddy = dy * Main.deltaTime() * i;
			
			double newX = x + ddx;
			double newY = y + ddy;

			collider.x = newX;
			collider.y = newY;
			
			for (int j = 0; j < colliders.size(); j++) {
				Collider c = colliders.get(j);
				
				//If collider isn't inside checking collider then continue, else remove it from the list
				if (c.collide(collider)) continue;
				
				colliders.remove(j);
				j--;
			}

			//If there isn't anything that the player collides with then move player full length
			if (colliders.size() == 0) {
				x += ddx;
				y += ddy;
				
				if (i == 1) {
					if (dy < 0) grounded = false;
					return true;
				}
				
				if (dy != 0) grounded = true;
				return false;
			} else if (dx != 0 && colliders.size() == 1) {
				Collider c = colliders.get(0);
				
				for (double j = 0.25; j <= 1; j += 0.25) {
					collider.y = newY - maxStepHeight * Math.abs(ddx) * j;
					
					if (!c.collide(collider)) {
						x += ddx;
						y += ddy - maxStepHeight * Math.abs(ddx) * j;

						if (dy != 0) grounded = true;
						return false;
					}
				}
			}
		}
		
		return false;
	}
	
	public void updateCollider() {
		collider.x = x;
		collider.y = y;
	}
	
	public void render(Graphics g) {
		g.setColor(entityColor);
		g.fillRect((int) Math.round(x - Level.camera.x), (int) Math.round(y - Level.camera.y), (int) width, (int) height);
	}
}
