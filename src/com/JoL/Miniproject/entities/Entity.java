package com.JoL.Miniproject.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.level.Level;

public class Entity {
	public double x, y;
	public double dx, dy;
	public Color entityColor;
	
	public Level level;
	
	protected boolean grounded;
	
	public Entity(Color color) {
		entityColor = color;
	}
	
	public void tick() {
	}
	
	protected void move() {
		move(dx, 0);
		move(0, dy);
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
				if (e != this && newX < e.x+64 && newY < e.y+64 && newX+64 > e.x && newY+64 > e.y) {
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
						y = e.y+64;
						this.dy = 0;
					} else {
						y = e.y-64;
						this.dy = 0;
						grounded = true;
					}
				} else {
					if (dx < 0) {
						x = e.x+64;
						this.dx = 0;
					} else {
						x = e.x-64;
						this.dx = 0;
					}
				}
				
				return;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(entityColor);
		g.fillRect((int) (x - level.camera.x), (int) (y - level.camera.y), 64, 64);
	}
}
