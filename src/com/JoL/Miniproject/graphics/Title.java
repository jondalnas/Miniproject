package com.JoL.Miniproject.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.JoL.Miniproject.Input;
import com.JoL.Miniproject.Main;
import com.JoL.Miniproject.Sound;
import com.JoL.Miniproject.level.Level;

public class Title {
	private static BufferedImage[] introTitleImages = new BufferedImage[113];
	private static BufferedImage[] titleImages = new BufferedImage[8];
	
	private double titleScreenFrameCount = 0;
	private boolean inIntro = true;
	private Level level;
	
	public Title(Level level) {
		this.level = level;
		
		for (int i = 0; i < introTitleImages.length; i++) {
			try {
				introTitleImages[i] = ImageIO.read(Images.class.getResource("/ttl/IntroTitle" + (i >= 10 ? (i >= 100 ? "" : "0") : "00") + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < titleImages.length; i++) {
			try {
				titleImages[i] = ImageIO.read(Images.class.getResource("/ttl/Title" + (i >= 10 ? (i >= 100 ? "" : "0") : "00") + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void render(Graphics g) {
		if (g.getFont().getSize() != 40)
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		
		if (inIntro) {
			g.drawImage(introTitleImages[(int) titleScreenFrameCount], 0, 0, null);
			
			if (titleScreenFrameCount < 10)
				titleScreenFrameCount += 1;
			
			else if ((int) titleScreenFrameCount == 10)
				titleScreenFrameCount += 0.04;
			
			else if ((int) titleScreenFrameCount == 36)
				titleScreenFrameCount += 0.05;
			
			else if (titleScreenFrameCount < 38)
				titleScreenFrameCount += 0.4;
			else
				titleScreenFrameCount += 0.25;
				
			if (titleScreenFrameCount == 10)
				Sound.titleEffectExplosion.play();
			
			if ((int) titleScreenFrameCount == 11)
				Sound.titleEffectBurnLong.loop();
			
			if ((int) titleScreenFrameCount == 31)
				Sound.titleEffectBurnLong.stop();
				
			if ((int) titleScreenFrameCount == 37)
				Sound.titleEffectBurn.loop();
			
			if ((int) titleScreenFrameCount == 45)
				Sound.titleEffectBurn.setVolume(17);
			if ((int) titleScreenFrameCount == 46)
				Sound.titleEffectBurn.setVolume(14);
			if ((int) titleScreenFrameCount == 47)
				Sound.titleEffectBurn.setVolume(11);
			if ((int) titleScreenFrameCount == 48)
				Sound.titleEffectBurn.setVolume(8);
			if ((int) titleScreenFrameCount == 49)
				Sound.titleEffectBurn.setVolume(5);
				
			if ((int) titleScreenFrameCount == 85)
				Sound.titleEffectAndKnuckles.play();
			
			if ((int) titleScreenFrameCount == 112)
				Sound.titleEffectBurn.setVolume(2);
			
			if ((((int) titleScreenFrameCount)) > 50 && (((int) titleScreenFrameCount)) < 81 && (((int) titleScreenFrameCount)-50)%2 == 0)
				Sound.titleEffectLetter.play();
			
			if ((int) titleScreenFrameCount == 104)
				Sound.titleEffectExplosion.play();
			
			if (titleScreenFrameCount > 112) inIntro = false;
		} else {
			titleScreenFrameCount += 0.25;
			g.drawImage(titleImages[((int) titleScreenFrameCount-112) % 8], 0, 0, null);
			g.setColor(Color.BLACK);
			g.drawString("Start Game", Main.WIDTH/2 - g.getFontMetrics().stringWidth("Start Game")/2, Main.HEIGHT/4*3 - (int) (g.getFontMetrics().getHeight()*0.5));
			g.drawString("Quit Game", Main.WIDTH/2 - g.getFontMetrics().stringWidth("Quit Game")/2, Main.HEIGHT/4*3 + (int) (g.getFontMetrics().getHeight()*0.5));
		}
	}
	
	public void tick() {
		System.out.println(Input.mouseButtons[1]);
		
		if (Input.mouseButtons[1]) {
			if (inIntro) {
				titleScreenFrameCount = 112;
				inIntro = false;
			} else {
				if (Input.mousePos[0] > Main.WIDTH/4 && Input.mousePos[0] < Main.WIDTH/4*3) {
					if (Input.mousePos[1] < Main.HEIGHT/4*3) {
						if (Input.mousePos[1] > Main.HEIGHT/2)
							level.showingTitle = false;
					} else {
						System.exit(0);
					}
				}
			}
			
			Input.mouseButtons[1] = false;
		}
	}
}
