package com.JoL.Miniproject.graphics;

import java.awt.Color;
import java.awt.Graphics;

import com.JoL.Miniproject.Main;

public class Screen {
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, Main.WIDTH/2, Main.HEIGHT/2);
	}
}
