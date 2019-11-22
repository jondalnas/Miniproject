package com.JoL.Miniproject.colliders;

public class Line extends Collider {
	public double x0, y0, x1, y1;
	private double a, b;
	
	public Line(double x0, double y0, double x1, double y1) {
		if (x0 <= x1) {
			this.x0 = x0;
			this.x1 = x1;
		} else {
			this.x0 = x1;
			this.x1 = x0;
		}
		
		if (y0 <= y1) {
			this.y0 = y0;
			this.y1 = y1;
		} else {
			this.y0 = y1;
			this.y1 = y0;
		}
		
		recalculateLine();
	}
	
	public boolean collide(Collider c) {
		if (c instanceof Line) {
			Line line = (Line) c;
			//Check if lines are parallel, if they are then they don't collide
			if (a == line.getA()) {
				if (b != line.getB())
					return false;
				else
					return true;
			}
			
			//Find point of collision

			//If a is Infinity, then the line is straight up and down
			if (a == Double.POSITIVE_INFINITY) {
				if (x0 < line.x0 || x0 > line.x1) return false;
				
				double y = line.getA() * x0 + line.getB();
				
				if (y >= y0 && y <= y1) return true;
			} else if (line.getA() == Double.POSITIVE_INFINITY) {
				if (line.x0 < x0 || line.x0 > x1) return false;
				
				double y = a * line.x0 + b;
				
				if (y >= line.y0 && y <= line.y1) return true;
			} else {
				//y=a*x+b
				//y=a_line*x+b_line
				//a*x+b=a_line*x+b_line
				//b-b_line=a_line*x-a*x
				//b-b_line=(a_line-a)*x
				//(b-b_line)/(a_line-a)=x
				double x = (b-line.getB())/(line.getA()-a);
				
				if (x >= x0 && x <= x1) return true;
			}
		} else if (c instanceof Polygon) {
			return c.collide(this);
		}
		
		return false;
	}
	
	public double getA() {
		return a;
	}
	
	public double getB() {
		return b;
	}
	
	public void recalculateLine() {
		a = (y0 - y1) / (x0 - x1);
		b = y0 - a * x0;
		
		if (a == Double.NEGATIVE_INFINITY) a = -a;
	}
}
