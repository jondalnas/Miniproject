package com.JoL.Miniproject.graphics;

import java.awt.Graphics;

import com.JoL.Miniproject.entities.Entity;
import com.JoL.Miniproject.level.Level;

public class Screen {
	public Level level;
	
	public void render(Graphics g) {
		for (Entity e : level.entities) {
			e.render(g);
		}
		
		level.level.render(g);
	}
}
