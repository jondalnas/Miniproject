package com.JoL.Miniproject.level;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.entities.Camera;
import com.JoL.Miniproject.entities.Entity;
import com.JoL.Miniproject.entities.Player;

public class Level {
	public static double GRAVITY = 9.82 * 64; //64 pixels is one meter
	
	public Camera camera;
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Level() {
		camera = new Camera();

		addEntity(new Player());
		int e = addEntity(new Entity(Color.red));
		entities.get(e).y = 480-64;
	}
	
	public void tick() {
		camera.tick();
		
		for (Entity e : entities) {
			e.tick();
		}
	}
	
	public int addEntity(Entity e) {
		entities.add(e);
		e.level = this;
		return entities.size() - 1;
	}
}
