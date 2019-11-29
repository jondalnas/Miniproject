package com.JoL.Miniproject.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.JoL.Miniproject.level.Level;

public class GunEnemy extends Enemy {
	private static BufferedImage gun;
	private static BufferedImage gunShot;
	private static final AffineTransform at = new AffineTransform();
	private double gunRotation;
	private boolean flip;
	
	public GunEnemy(Player player) {
		super(player);
		
		if (gun == null) {
			gun = new BufferedImage(24, 20, BufferedImage.TYPE_INT_ARGB);
			Graphics gunGraphics = gun.getGraphics();
			gunGraphics.setColor(Color.BLACK);
			gunGraphics.fillPolygon(new int[] {0,3,5,8,18,20,22,24,24,14,16,14,8, 8, 4,4},
					  new int[] {4,4,0,4, 4, 0, 4, 4, 8, 8,12,12,8,20,20,8}, 16);
			
			gunShot = new BufferedImage(24, 20, BufferedImage.TYPE_INT_ARGB);
			Graphics gunShotGraphics = gunShot.getGraphics();
			gunShotGraphics.setColor(Color.BLACK);
			gunShotGraphics.fillPolygon(new int[] {0,3,5,8,18,20,22,24,24,13,13,11,8, 8, 4,4},
				  	  new int[] {4,4,0,4, 4, 0, 4, 4, 8, 8,12,12,8,20,20,8}, 16);
		}
	}
	
	public void tick() {
		if (dead) return;
		
		super.tick();

		double dx = target.x - x;
		double dy = target.y - y;
		
		flip = dx < 0;
		
		gunRotation = Math.atan(dy/dx);
	}
	
	public void render(Graphics g) {
		super.render(g);
		at.setToRotation(gunRotation, Math.round(x - Level.camera.x + width/2), Math.round(y - Level.camera.y + height/2));
		at.scale(flip ? -1 : 1, 1);
		((Graphics2D) g).setTransform(at);
		if (flip) 
			g.drawImage(gun, (int) -Math.round(x - Level.camera.x - gun.getWidth() + width), (int) Math.round(y - Level.camera.y + height/2 - gun.getHeight()/2), null);
		else
			g.drawImage(gun, (int) Math.round(x - Level.camera.x - gun.getWidth()/2 + width/2), (int) Math.round(y - Level.camera.y + height/2 - gun.getHeight()/2), null);
		
		at.setToRotation(0);
		((Graphics2D) g).setTransform(at);
	}
}
