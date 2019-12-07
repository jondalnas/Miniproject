package com.JoL.Miniproject.graphics;

import java.awt.Graphics;

import com.JoL.Miniproject.colliders.Polygon;
import com.JoL.Miniproject.entities.Entity;
import com.JoL.Miniproject.level.Level;

public class Screen {
	public Level level;
	
	public void render(Graphics g) {
		if (level.showingTitle) {
			level.title.render(g);
		} else {
			for (Entity e : level.entities) {
				e.render(g);
			}
	
			for (Polygon p : level.levelPolys) {
				p.render(g);
			}
		}
	}
}
