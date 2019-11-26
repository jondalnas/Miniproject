package com.JoL.Miniproject.level;

import java.io.*;
import java.util.Scanner;

public class LevelLoader {
	public static Level loadFile(String level) {
		Scanner scan;
		try {
			scan = new Scanner(LevelLoader.class.getResource("/lvl/" + level + ".lvl").openStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		Level result = new Level();
		
		int polygonCount;
		String line;
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			polygonCount = Integer.parseInt(line);
			Double[][] poly = new Double[polygonCount][2];
			while (polygonCount-- != 0) {
				String[] cords = scan.nextLine().split(",");
				poly[polygonCount][0] = Double.parseDouble(cords[0]);
				poly[polygonCount][1] = Double.parseDouble(cords[1]);
			}
			
			result.levelPolys.add(poly);
		}
		
		
		
		scan.close();
		return result;
	}
}
