package com.JoL.Miniproject.graphics;

import java.awt.Graphics;

import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.entities.Entity;
import com.JoL.Miniproject.level.Level;

public class Screen {
	public Level level;
	
	public void render(Graphics g) {
		for (Entity e : level.entities) {
			e.render(g);
		}

		g.fillPolygon(new int[]{0-(int)level.camera.x, Main.WIDTH-(int)level.camera.x, Main.WIDTH-(int)level.camera.x}, new int[]{0+Main.HEIGHT-(int)level.camera.y, 0+Main.HEIGHT-(int)level.camera.y, -256+Main.HEIGHT-(int)level.camera.y}, 3);
		//g.fillPolygon(new int[]{0-(int)level.camera.x, Main.WIDTH-(int)level.camera.x, Main.WIDTH-(int)level.camera.x, Main.WIDTH-64-(int)level.camera.x, 64-(int)level.camera.x}, new int[]{0+Main.HEIGHT-(int)level.camera.y, 0+Main.HEIGHT-(int)level.camera.y, -64+Main.HEIGHT-(int)level.camera.y, -32+Main.HEIGHT-(int)level.camera.y, -32+Main.HEIGHT-(int)level.camera.y}, 5);
	}
}
