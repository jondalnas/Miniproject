package com.JoL.Miniproject.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.JoL.Miniproject.Input;
import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.level.Level;

public class Player extends GravityEntity {
	private double speed = 256;
	private double jumpSpeed = 512;
	
	private double swordRotation;
	private final BufferedImage sword;
	private final AffineTransform at;
	
	public Player() {
		super(new Color(255, 0, 255), 64, 64);
		
		sword = new BufferedImage(64, 28, BufferedImage.TYPE_INT_ARGB);
		Graphics swordG = sword.getGraphics();
		swordG.setColor(Color.GRAY);
		
		swordG.fillPolygon(new int[] {0, 12, 12, 16, 16, 64, 60, 16, 16, 12, 12, 0},
					  new int[] {12, 12, 0, 4, 12, 12, 16, 16, 24, 28, 16, 16}, 12);
		
		at = new AffineTransform();
	}
	
	public void tick() {
		//Calculate attack
		double swordX = (Input.mousePos[0] - Main.WIDTH / 2) * width;
		double swordY = (Input.mousePos[1] - Main.HEIGHT / 2) * height;

		swordRotation = Math.atan(swordY/swordX) + (swordX < 0 ? Math.PI : 0);
		
		if (Input.mouseButtons[1]) {
			
		}
		
		//Calculate position
		dx = 0;
		if (Input.keys[KeyEvent.VK_D]) dx += speed;
		if (Input.keys[KeyEvent.VK_A]) dx -= speed;
		
		if (grounded && Input.keys[KeyEvent.VK_SPACE]) dy = -jumpSpeed;
		
		super.tick();
		Level.camera.x = x - Main.WIDTH/2 + 64/2;
		Level.camera.y = y - Main.HEIGHT/2 + 64/2;
	}
	
	public void render(Graphics g) {
		g.setColor(entityColor);
		g.fillRect(Main.WIDTH/2 - (int) width/2, Main.HEIGHT/2 - (int) height/2, (int) width, (int) height);

		at.setToRotation(swordRotation , Main.WIDTH/2, Main.HEIGHT/2);
		((Graphics2D) g).setTransform(at);
		g.drawImage(sword, Main.WIDTH/2, Main.HEIGHT/2, null);
		at.setToRotation(0);
		((Graphics2D) g).setTransform(at);
	}
}
