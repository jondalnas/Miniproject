package com.JoL.Miniproject.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.Main;
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
	
	public Entity(Color color, double width, double height) {
		entityColor = color;
		this.width = width;
		this.height = height;
		
		collider = new Polygon(new double[][] {new double[] {0, 0}, new double[] {width, 0}, new double[] {width, height}, new double[] {0, height}});
		//collider = new Polygon(new double[][] {new double[] {0, 0}, new double[] {width, 0}, new double[] {width, height}});
	}
	
	public void tick() {
	}
	
	protected void move() {
		if (!move(0, dy)) dy = 0;
		if (!move(dx, 0)) dx = 0;
	}
	
	private boolean move(double dx, double dy) {
		if (dx == 0 && dy == 0) return true;
		
		List<Entity> collidingEntitiesLeft = (List<Entity>) ((ArrayList) level.entities).clone();
		
		//Loop through all distance percents backwards, and check if it collides with 
		for (double i = 1; i > 0; i -= 0.01) {
			double newX = x + dx * Main.deltaTime() * i;
			double newY = y + dy * Main.deltaTime() * i;
			
			for (int j = 0; j < collidingEntitiesLeft.size(); j++) {
				Entity e = collidingEntitiesLeft.get(j);
				
				//If entity isn't inside checking entity and they aren't the same then continue, else remove it from the list
				collider.x = newX;
				collider.y = newY;
				e.collider.x = e.x;
				e.collider.y = e.y;
				if (e != this && e.collider.collide(collider)) {
					continue;
				}
				
				collidingEntitiesLeft.remove(j);
				j--;
			}

			//If there isn't anything that the player collides with then move player full length
			if (collidingEntitiesLeft.size() == 0) {
				x += dx * Main.deltaTime() * i;
				y += dy * Main.deltaTime() * i;
				
				if (i == 1) {
					if (dy != 0) grounded = false;
					return true;
				}
				
				if (dy != 0) grounded = true;
				return false;
			}
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		g.setColor(entityColor);
		g.fillRect((int) Math.round(x - level.camera.x), (int) Math.round(y - level.camera.y), (int) width, (int) height);
	}
}
