package com.JoL.Miniproject;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.JoL.Miniproject.graphics.Screen;
import com.JoL.Miniproject.level.Level;
import com.JoL.Miniproject.level.LevelLoader;

public class Main extends Canvas implements Runnable {
	public static int WIDTH = 640, HEIGHT = 480;
	
	private Screen screen;
	private static Level level;
	
	public Main() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		 
		screen = new Screen();
		
		setLevel("Shapes");
		for (Double[][] p : level.levelPolys) {
			for (Double[] c : p)
				System.out.println("x" + c[0] + "y" + c[1]);
		}
		
		setSize(size);
	}
	
	public void start() {
		new Thread(this).run();
	}
	
	public static void main(String[] args) {
		Main main = new Main();

		JFrame frame = new JFrame("Hello");
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(main, 0);
		
		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		 
		frame.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
		frame.setPreferredSize(frame.getSize());
		
		main.start();
	}
	
	private void tick() {
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		screen.render(g);
		
		g.dispose();
		bs.show();
	}

	private static double deltaTime = 0;
	@Override
	public void run() {
		long lastTick = System.nanoTime();
		while (true) {
			deltaTime = ((System.nanoTime() - lastTick) * 1.0e-9);
			lastTick = System.nanoTime();
			
			tick();
			render();
		}
	}
	
	public static double deltaTime() {
		return deltaTime;
	}
	
	public static void setLevel(String level) {
		Main.level = LevelLoader.loadFile(level);
	}
}
