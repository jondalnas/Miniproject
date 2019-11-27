package com.JoL.Miniproject.level;

import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.colliders.Collider;
import com.JoL.Miniproject.colliders.Polygon;
import com.JoL.Miniproject.entities.Camera;
import com.JoL.Miniproject.entities.Enemy;
import com.JoL.Miniproject.entities.Entity;
import com.JoL.Miniproject.entities.Player;

public class Level {
	public static double GRAVITY = 9.82 * 64; //64 pixels is one meter
	
	public static Camera camera;
	public List<Entity> entities = new ArrayList<Entity>();
	private Player player;
	
	public Polygon level;
	
	public Level() {
		camera = new Camera();

		player = new Player();
		addEntity(player, 0, 0);
		addEntity(new Enemy(player), 640, -256-128+Main.HEIGHT-64);

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
	
	public List<Collider> collide(Collider c) {
		List<Collider> result = new ArrayList<Collider>();
		
		for (Entity e : entities) {
			if (e.collider.collide(c)) result.add(e.collider);
		}
		
		if (level.collide(c)) result.add(level);
		
		return result;
	}
	
	public List<Entity> collideEntity(Collider c) {
		List<Entity> result = new ArrayList<Entity>();
		
		for (Entity e : entities) {
			e.updateCollider();
			if (e.collider.collide(c)) result.add(e);
		}
		
		return result;
	}
	
	public int addEntity(Entity e, double x, double y) {
		entities.add(e);
		e.level = this;
		e.x = x;
		e.y = y;
		
		return entities.size() - 1;
	}
}
