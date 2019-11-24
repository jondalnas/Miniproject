package com.JoL.Miniproject.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.JoL.Miniproject.Input;
import com.JoL.Miniproject.Main;
import com.sun.glass.events.KeyEvent;

public class Player extends GravityEntity {
	private double speed = 256;
	private double jumpSpeed = 512;
	
	public Player() {
		super(new Color(255, 0, 255));
	}
	
	public void tick() {
		dx = 0;
		if (Input.keys[KeyEvent.VK_D]) dx += speed;
		if (Input.keys[KeyEvent.VK_A]) dx -= speed;
		
		if (grounded && Input.keys[KeyEvent.VK_SPACE]) dy = -jumpSpeed;
		
		super.tick();
		level.camera.x = x - Main.WIDTH/2 + 64/2;
		level.camera.y = y - Main.HEIGHT/2 + 64/2;
	}
	
	public void render(Graphics g) {
		g.setColor(entityColor);
		g.fillRect(Main.WIDTH/2 - 64/2, Main.HEIGHT/2 - 64/2, 64, 64);
	}
}
