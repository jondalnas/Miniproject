package com.JoL.Miniproject;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	public static Sound jump = loader("/snd/Jump.wav");
	public static Sound slice = loader("/snd/Slice.wav");
	public static Sound shoot= loader("/snd/Gun_Shot.wav");
	public static Sound hitGround = loader("/snd/Hit_ground.wav");
	
	private Clip clip;
	private static Sound loader(String location) {
		Sound result = new Sound();
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(location));
			result.clip = AudioSystem.getClip();
			result.clip.open(ais);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.err.println("Couldn't find " + location);
			throw new RuntimeException(e);
		}
		
		return result;
	}
	
	public void play() {
		new Thread() {
			public void run() {
				synchronized (clip) {
					clip.stop();
					clip.setFramePosition(0);
					clip.start();	
				}
			}
		}.start();
	}
}
