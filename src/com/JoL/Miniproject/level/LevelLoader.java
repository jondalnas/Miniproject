package com.JoL.Miniproject.level;

import java.io.*;
import java.util.Scanner;

public class LevelLoader {
	public static void loadFile(String level) {
		Scanner scan;
		try {
			scan = new Scanner(LevelLoader.class.getResource("/lvl/" + level + ".lvl").openStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		int polygonCount;
		String line;
		while ((line = scan.nextLine()) != null) {
			polygonCount = Integer.parseInt(line);
			double[][] poly = new double[polygonCount][2];
			while (polygonCount-- != 0) {
				String[] cords = scan.nextLine().split(",");
				poly[polygonCount][0] = Integer.parseInt(cords[0]);
				poly[polygonCount][1] = Integer.parseInt(cords[1]);
			}
		}
		
	}
}
