package com.JoL.Miniproject.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;

import com.JoL.Miniproject.Input;
import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.colliders.Line;
import com.JoL.Miniproject.level.Level;

public class Player extends GravityEntity {
	private double speed = 256;
	private double jumpSpeed = 512;
	
	private double swordRotation;
	private final BufferedImage sword;
	private final AffineTransform at;
	private boolean flipSword;
	private Line swordCollider;
	private Line swordColliderClose;
	private double sliceLength = Math.PI / 4;
	public final double maxR, minR;
	private final BufferedImage arc;
	private double swingAnimation;
	private double swingAnimationTime = 0.05;
	private double swingStartAngle;
	
	public Player() {
		super(new Color(255, 0, 255), 64, 64);
		
		sword = new BufferedImage(128, 28, BufferedImage.TYPE_INT_ARGB);
		Graphics swordG = sword.getGraphics();
		swordG.setColor(Color.GRAY);
		
		swordG.fillPolygon(new int[] {0, 12, 12, 16, 16, 128, 124, 16, 16, 12, 12, 0},
						   new int[] {12, 12, 0, 4, 12, 12, 16, 16, 24, 28, 16, 16}, 12);
		
		at = new AffineTransform();

		swordCollider = new Line(0, 0, 0, 0);
		swordColliderClose = new Line(0, 0, 0, 0);
		
		maxR = width/2+sword.getWidth();
		minR = width/2+sword.getWidth()/2;
		
		arc = new BufferedImage((int) maxR, (int) maxR, BufferedImage.TYPE_INT_ARGB);
		Graphics arcG = arc.getGraphics();
		arcG.setColor(new Color(0xdfdfdf));
		Arc2D arc = new Arc2D.Double(-maxR*1.6/2, -maxR*1.6/2, maxR*1.6, maxR*1.6, 0, -Math.toDegrees(sliceLength*1.5), Arc2D.PIE);
		Ellipse2D el = new Ellipse2D.Double(-maxR*1.4/2, -maxR*1.4/2, maxR*1.4, maxR*1.4);
		Area a = new Area(arc);
		a.subtract(new Area(el));
		((Graphics2D) arcG).fill(a);
	}
	
	public void tick() {
		//Calculate sword position
		double swordX = (Input.mousePos[0] - Main.WIDTH / 2) * width;
		double swordY = (Input.mousePos[1] - Main.HEIGHT / 2) * height;

		flipSword = swordX < 0;
		if (swordY < 0 || !grounded)
			swordRotation = Math.atan(swordY/swordX) + (flipSword ? Math.PI : 0);
		else if (flipSword)
			swordRotation = Math.PI;
		else 
			swordRotation = 0;
		
		//Calculate attack
		if (swingAnimation > 0) swingAnimation -= Main.deltaTime();
		else if (Input.mouseButtons[1]) {
			double cosMin = Math.cos(swordRotation-sliceLength/2);
			double sinMin = Math.sin(swordRotation-sliceLength/2);
			double cosMax = Math.cos(swordRotation+sliceLength/2);
			double sinMax = Math.sin(swordRotation+sliceLength/2);

			swordCollider.x = x + width/2;
			swordCollider.y = y + height/2;
			swordCollider.update(cosMin * maxR, sinMin * maxR,
								 cosMax * maxR, sinMax * maxR);
			

			swordColliderClose.x = x + width/2;
			swordColliderClose.y = y + height/2;
			swordColliderClose.update(cosMin * minR, sinMin * minR,
									  cosMax * minR, sinMax * minR);
			
			for (Entity c : level.collideEntity(swordCollider)) {
				if (c == this) continue;
				
				if (c instanceof Enemy) {
					((Enemy) c).kill();
				}
			}
			
			for (Entity c : level.collideEntity(swordColliderClose)) {
				if (c == this) continue;
				
				if (c instanceof Enemy) ((Enemy) c).kill();
			}

			swingStartAngle = swordRotation;
			swingAnimation = swingAnimationTime;
			Input.mouseButtons[1] = false;
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

		if (swingAnimation > 0) {
			//TODO: Maybe use this one: at.setToRotation(swingStartAngle - (sliceLength * (1 - swingAnimation / swingAnimationTime)) * (flipSword ? -1 : 1), Main.WIDTH/2, Main.HEIGHT/2);
			at.rotate(swingStartAngle - sliceLength * (flipSword ? -1 : 1), Main.WIDTH/2, Main.HEIGHT/2);
			((Graphics2D) g).setTransform(at);
			g.drawImage(sword, Main.WIDTH/2, Main.HEIGHT/2 + (flipSword ? -sword.getHeight() : 0), null);

			at.setToRotation(swingStartAngle-sliceLength/2*(flipSword ? 1 : 2), Main.WIDTH/2, Main.HEIGHT/2);
			((Graphics2D) g).setTransform(at);
			//TODO: Add more animation to the arc
			g.drawImage(arc, Main.WIDTH/2, Main.HEIGHT/2, null);
		} else {
			at.setToRotation(swordRotation, Main.WIDTH/2, Main.HEIGHT/2);
			((Graphics2D) g).setTransform(at);
			g.drawImage(sword, Main.WIDTH/2, Main.HEIGHT/2 + (flipSword ? -sword.getHeight() : 0), null);
		}
		
		at.setToRotation(0);
		((Graphics2D) g).setTransform(at);
	}
}
