package com.JoL.Miniproject.colliders;

public class Circle extends Collider {
	public double r;
	
	public Circle(double radius) {
		r = radius;
	}
	
	public boolean collide(Collider c) {
		if (c instanceof Line) {
			Line l = (Line) c;

			double a = l.getA() * l.getA() - 1;
			double b = l.getB() - this.y;
			double e = l.getA()*this.x+l.getB()-this.y;
			double d = Math.sqrt(a*r*r+e*e);
			
			if (d < 0) return false;

			double x0 = (-Math.sqrt(d) - b * l.getA() - this.x)/a;
			double x1 = (Math.sqrt(d) - b * l.getA() - this.x)/a;
		}
		
		return false;
	}
}
