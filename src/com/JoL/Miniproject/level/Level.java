package com.JoL.Miniproject.level;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.colliders.Line;
import com.JoL.Miniproject.colliders.Polygon;
import com.JoL.Miniproject.entities.Camera;
import com.JoL.Miniproject.entities.Entity;
import com.JoL.Miniproject.entities.GravityEntity;

public class Level {
	public static double GRAVITY = 9.82 * 64; //64 pixels is one meter
	
	public Camera camera;
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Level() {
		camera = new Camera();

		addEntity(new GravityEntity(Color.blue));
		int e = addEntity(new Entity(Color.red));
		entities.get(e).y = 480-64;

		Line line = new Line(.89, .9, 1, 1.01);
		Polygon poly = new Polygon(new double[][] {new double[] {0, 0}, new double[] {1, 0}, new double[] {1, 1}});
		
		System.out.println(line.collide(poly));
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
