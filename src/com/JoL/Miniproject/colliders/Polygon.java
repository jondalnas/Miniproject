package com.JoL.Miniproject.colliders;

import java.util.ArrayList;
import java.util.List;

public class Polygon extends Collider {
	public List<Line> polygon = new ArrayList<Line>();
	
	public Polygon(double[][] points) {
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
}
