package com.JoL.Miniproject.entities;

import java.awt.Color;

import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.level.Level;

public class GravityEntity extends Entity {
	public GravityEntity(Color color) {
		super(color);
	}

	/**
	 * Remember to add super.tick(); to any class that extends this class, or else it will not calculate gravity on it.
	 */
	public void tick() {
		dy += Level.GRAVITY * Main.deltaTime();
		move();
	}
}
