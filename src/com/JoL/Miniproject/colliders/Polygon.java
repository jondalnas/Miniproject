package com.JoL.Miniproject.colliders;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.JoL.Miniproject.level.Level;

public class Polygon extends Collider {
	public List<Line> polygon = new ArrayList<Line>();
	public int[] xp;
	public int[] yp;
	
	public Polygon(double[][] points) {
		xp = new int[points.length + 1];
		yp = new int[points.length + 1];
		
		for (int i = 0; i < points.length; i++) {
			xp[i] = (int) points[i][0];
			yp[i] = (int) points[i][1];
		}
		
		double[] lastPoint = null;
		for (double[] point : points) {
			if (lastPoint == null) {
				lastPoint = point;
				continue;
			}
			
			polygon.add(new Line(lastPoint[0], lastPoint[1], point[0], point[1]));
			
			lastPoint = point;
		}
		
		polygon.add(new Line(points[0][0], points[0][1], lastPoint[0], lastPoint[1]));
	}

	public boolean collide(Collider c) {
		for (Line l : polygon) {
			l.x = x;
			l.y = y;
			
			if (c.collide(l))
				return true;
		}
		
		return false;
	}
	
	public void render(Graphics g) {
		int[] xpp = xp.clone();
		int[] ypp = yp.clone();
		
		for (int i = 0; i < xpp.length; i++) {
			xpp[i] += x - Level.camera.x;
			ypp[i] += y - Level.camera.y;
		}
		
		g.setColor(Color.BLACK);
		g.fillPolygon(xpp, ypp, xp.length);
	}
}
