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
		move(0, dy);
		move(dx, 0);
	}
	
	private void move(double dx, double dy) {
		if (dx == 0 && dy == 0) return;
		
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
				
				if (dy != 0) grounded = false;
				
				return;
			}
			//If there is only one entity left then set position to be next to it
			else if (collidingEntitiesLeft.size() == 1) {
				Entity e = collidingEntitiesLeft.get(0);
				
				if (dx == 0) {
					if (dy < 0) {
						y = e.y+64.1;
						this.dy = 0;
					} else {
						y = e.y-64.1;
						this.dy = 0;
						grounded = true;
					}
				} else {
					if (dx < 0) {
						x = e.x+64.1;
						this.dx = 0;
					} else {
						x = e.x-64.1;
						this.dx = 0;
					}
				}
				
				return;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(entityColor);
		g.fillRect((int) Math.round(x - level.camera.x), (int) Math.round(y - level.camera.y), (int) width, (int) height);
	}
}
