package com.JoL.Miniproject.level;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.entities.Camera;
import com.JoL.Miniproject.entities.Entity;

public class Level {
	public Camera camera;
	
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Level() {
		camera = new Camera();
		
		addEntity(new Entity(Color.BLACK));
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
		return entities.size();
	}
}
