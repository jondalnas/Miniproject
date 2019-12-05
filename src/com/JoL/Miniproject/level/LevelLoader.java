package com.JoL.Miniproject.level;

import java.io.*;
import java.util.Scanner;

import com.JoL.Miniproject.colliders.Polygon;
import com.JoL.Miniproject.entities.GunEnemy;

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
		byte stage = 0;
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			if (line.contains("E")) {
				stage = 1;
				continue;
			}
			
			switch(stage) {
			case 0:
				polygonCount = Integer.parseInt(line);
				double[][] poly = new double[polygonCount][2];
				while (polygonCount-- != 0) {
					String[] cords = scan.nextLine().split(",");
					poly[polygonCount][0] = Double.parseDouble(cords[0]);
					poly[polygonCount][1] = Double.parseDouble(cords[1]);
				}
				
				result.levelPolys.add(new Polygon(poly));
				break;
			case 1:
				char entity = line.charAt(0);
				String[] attrib = line.substring(1).split(",");
				
				switch(entity) {
				case 'P':
					result.player.x = Double.parseDouble(attrib[0]);
					result.player.y = Double.parseDouble(attrib[1]);
					break;
				case 'G':
					result.addEntity(new GunEnemy(result.player), Double.parseDouble(attrib[0]), Double.parseDouble(attrib[1]));
					break;
				}
				
				break;
			}
		}
		
		
		
		scan.close();
		return result;
	}
}
