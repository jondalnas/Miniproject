package com.JoL.Miniproject.level;

import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.colliders.Collider;
import com.JoL.Miniproject.colliders.Line;
import com.JoL.Miniproject.colliders.Polygon;
import com.JoL.Miniproject.entities.Camera;
import com.JoL.Miniproject.entities.Entity;
import com.JoL.Miniproject.entities.GunEnemy;
import com.JoL.Miniproject.entities.Player;

public class Level {
	public static double GRAVITY = 9.82 * 192; //192 pixels is one meter
	public List<Polygon> levelPolys = new ArrayList<Polygon>();
	
	public static Camera camera;
	public List<Entity> entities = new ArrayList<Entity>();
	private List<Entity> addingEntities = new ArrayList<Entity>();
	private List<Integer> removingEntities = new ArrayList<Integer>();
	public Player player;
	
	public Level() {
		camera = new Camera();

		player = new Player();
		addEntity(player, 0, 0);
	}
	
	public void tick() {
		updateEntities();
		
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
		
		for (Polygon p : levelPolys) {
			if (p.collide(c)) result.add(p);
		}
		
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
		addingEntities.add(e);
		e.x = x;
		e.y = y;
		e.level = this;
		
		return addingEntities.size() + entities.size() - 2;
	}
	
	public void removeEntity(Entity e) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) == e) removingEntities.add(i);
		}
	}
	
	private void updateEntities() {
		for (int i : removingEntities) {
			entities.remove(i);
		}
		
		for (Entity e : addingEntities) entities.add(e);
		
		removingEntities.clear();
		addingEntities.clear();
	}

	public boolean collideLevel(Collider c) {
		for (Polygon p : levelPolys) {
			if (p.collide(c)) return true;
		}
		
		return false;
	}
}
