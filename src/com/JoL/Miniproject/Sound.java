package com.JoL.Miniproject;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	public static Sound jump = loader("/snd/Jump.wav");
	public static Sound slice = loader("/snd/Slice.wav");
	public static Sound shoot = loader("/snd/Gun_Shot.wav");
	public static Sound hitGround = loader("/snd/Hit_ground.wav");
	public static Sound hit = loader("/snd/Hit.wav");
	public static Sound dash = loader("/snd/Dash.wav");
	
	public static Sound titleEffectExplosion = loader("/snd/Title_Explosion.wav");
	public static Sound titleEffectBurn = loader("/snd/Title_Burning.wav");
	public static Sound titleEffectBurnLong = loader("/snd/Title_Burning_Long.wav");
	public static Sound titleEffectLetter = loader("/snd/Title_Letters.wav");
	public static Sound titleEffectAndKnuckles = loader("/snd/Title_Knuckles.wav");
	
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
		
		result.setVolume(100);
		
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

	public void loop() {
		new Thread() {
			public void run() {
				synchronized (clip) {
					clip.stop();
					clip.setFramePosition(0);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}
		}.start();
	}
	
	public void stop() {
		clip.stop();
		clip.setFramePosition(0);
	}
	
	public void setVolume(float volume) {
		volume *= 0.8;
		FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue((volumeControl.getMaximum() - volumeControl.getMinimum()) / 100.0f * volume + volumeControl.getMinimum());
	}
}
