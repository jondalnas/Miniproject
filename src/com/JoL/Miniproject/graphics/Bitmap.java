package com.JoL.Miniproject.graphics;

public class Bitmap {
	public int width, height;
	public int[] pixels;
	
	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
	}
}
