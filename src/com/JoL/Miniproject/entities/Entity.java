package com.JoL.Miniproject.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.JoL.Miniproject.level.Level;

public class Entity {
	public double x, y;
	public Color entityColor;
	
	public Level level;
	
	public Entity(Color color) {
		entityColor = color;
	}
	
	public void tick() {
	}
	
	public void render(Graphics g) {
		g.setColor(entityColor);
		g.fillRect((int) (x - level.camera.x), (int) (y - level.camera.y), 64, 64);
	}
}
