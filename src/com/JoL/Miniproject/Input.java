package com.JoL.Miniproject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	public static boolean[] keys = new boolean[0xFFFF];
	public static boolean[] mouseButtons = new boolean[4];
	public static int[] mousePos = new int[2];
	

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void mouseMoved(MouseEvent e) {
		mousePos[0] = e.getX();
		mousePos[1] = e.getY();
	}
	public void mouseDragged(MouseEvent e) {
		mousePos[0] = e.getX();
		mousePos[1] = e.getY();
	}

	public void mousePressed(MouseEvent e) {
		int button = e.getButton();
		
		if (button >= mouseButtons.length) return;
		
		mouseButtons[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e) {
		int button = e.getButton();
		
		if (button >= mouseButtons.length) return;
		
		mouseButtons[e.getButton()] = false;
	}

	public void keyTyped(KeyEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
