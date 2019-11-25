package com.JoL.Miniproject.level;

import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.colliders.Polygon;
import com.JoL.Miniproject.entities.Camera;
import com.JoL.Miniproject.entities.Entity;
import com.JoL.Miniproject.entities.Player;

public class Level {
	public static double GRAVITY = 9.82 * 64; //64 pixels is one meter
	
	public static Camera camera;
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Polygon level;
	
	public Level() {
		camera = new Camera();

		addEntity(new Player());
		/*int e = addEntity(new Entity(Color.red, Main.WIDTH/2, 64));
		entities.get(e).y = 480-64;
		int e1 = addEntity(new Entity(Color.red, 64, 64*3));
		entities.get(e1).y = 480-64*3;
		entities.get(e1).x = -64;*/

		level = new Polygon(new double[][] {new double[]{0, 0},
			  new double[]{640, -256},
			  new double[]{640, -256-128},
			  new double[]{640 * 2, -288},
			  new double[]{640 * 2, 32},
			  new double[]{0, 32}});
		level.y = Main.HEIGHT;
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
