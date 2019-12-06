package com.JoL.Miniproject;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.JoL.Miniproject.Input;
import com.JoL.Miniproject.graphics.Screen;
import com.JoL.Miniproject.level.Level;
import com.JoL.Miniproject.level.LevelLoader;


public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int WIDTH = 640, HEIGHT = 480;
	
	private int targetFPS = 60;
	
	private static Screen screen;
	private static Level level;
	private Input input;
	
	public Main() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		 
		new Sound();
		
		screen = new Screen();
		setLevel("Shapes");
		
		input = new Input();
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		
		setSize(size);
	}
	
	public void start() {
		new Thread(this).run();
	}
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
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
		level.tick();
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
		int frames = 0;
		long lastSec = System.nanoTime();
		
		long timeBetweenFrames = ((long) 1e9)/targetFPS;
		
		while (true) {
			try {
				long sleepTime = (long) ((timeBetweenFrames - (System.nanoTime() - lastTick))*1e-6);
				if (sleepTime > 0) Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			deltaTime = (System.nanoTime() - lastTick) * 1.0e-9;
			lastTick = System.nanoTime();
			
			frames++;
			
			if (System.nanoTime() - lastSec >= 1e9) {
				System.out.println("FPS: " + frames);
				
				frames = 0;
				lastSec = System.nanoTime();
			}
			
			tick();
			
			render();
		}
	}
	
	public static double deltaTime() {
		return deltaTime;
	}
	
	public static void setLevel(String level) {
		Main.level = LevelLoader.loadFile(level);
		screen.level = Main.level;
	}
}
