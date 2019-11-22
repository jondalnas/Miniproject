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
	}

	public boolean collide(Collider c) {
		for (Line l : polygon) {
			if (l.collide(c)) return true;
		}
		
		return false;
	}
}
