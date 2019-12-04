package com.JoL.Miniproject.level.converter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.JoL.Miniproject.level.LevelLoader;
import com.sun.xml.internal.bind.api.impl.NameConverter.Standard;

public class Converter {
	private static final double MM_TO_PIXEL = 64/16.933333;
	

	public static void main(String[] args) {
		convert("/level.svg", "res/lvl/HI.lvl");
	}
	
	public static void convert(String from, String to) {
		Scanner scan;
		try {
			scan = new Scanner(LevelLoader.class.getResource(from).openStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		List<double[][]> polygons = new ArrayList<double[][]>();
		
		boolean inPath = false;
		while (scan.hasNext()) {
			String line = scan.next().toLowerCase();
			if (inPath) {
				line = line.toLowerCase().toLowerCase();
				if (!line.contains("id=\"") && line.contains("d=\"")) {
					line = line.substring(line.indexOf("\"")+1);
					String result = line;
					
					while (!(line = scan.nextLine().toLowerCase()).contains("z")) result += line;
					
					result += line;
					result = result.substring(0, result.indexOf('z'));
					
					String[] instructions = result.split(" ");
					
					List<double[]> coords = new ArrayList<double[]>();
					double[] currCord = new double[2];
					char instruction = ' ';
					for (int i = 0; i < instructions.length; i++) {
						if ("mlvhMLVH".contains(instructions[i].charAt(0)+"")) {
							instruction = instructions[i].charAt(0);
							i++;
						}
						
						String numbers = instructions[i];
						
						switch (instruction) {
						case 'm': {
							String[] strNums = numbers.split(",");
							double[] nums = new double[] {Double.parseDouble(strNums[0]), Double.parseDouble(strNums[1])};

							currCord[0] = nums[0] * MM_TO_PIXEL;
							currCord[1] = nums[1] * MM_TO_PIXEL;
							break;
						}
						case 'l': {
							String[] strNums = numbers.split(",");
							double[] nums = new double[] {Double.parseDouble(strNums[0]), Double.parseDouble(strNums[1])};

							currCord[0] += nums[0] * MM_TO_PIXEL;
							currCord[1] += nums[1] * MM_TO_PIXEL;
							break;
						}
						case 'v': {
							currCord[1] = Double.parseDouble(numbers) * MM_TO_PIXEL;
							break;
						}
						case 'h': {
							currCord[0] = Double.parseDouble(numbers) * MM_TO_PIXEL;
							break;
						}
						}
						
						coords.add(currCord.clone());
					}
					
					polygons.add((double[][]) coords.toArray(new double[coords.size()][2]));
					
					inPath = false;
				}
			}
			
			if (line.contains("<path")) inPath = true;
		}

		try {
			File toFile = new File(to);
			if (!toFile.exists()) {
				toFile.createNewFile();
			}
			
			for (double[][] poly : polygons) {
				Files.write(Paths.get(to), (poly.length+"\n").getBytes(), StandardOpenOption.APPEND);
				for (double[] coord : poly) {
					Files.write(Paths.get(to), (coord[0]+","+(coord[1]-1122.5197071362147)+"\n").getBytes(), StandardOpenOption.APPEND);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
