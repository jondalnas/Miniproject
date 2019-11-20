package com.JoL.Miniproject.entities;

import com.JoL.Miniproject.Main;

public class Camera extends Entity {

	public Camera() {
		super(null);
	}
	
	double time;
	public void tick() {
		time += Main.deltaTime();

		x = Math.cos(time) * 100;
		y = Math.sin(time) * 100;
	}
	
	public void render() {
	}
}
