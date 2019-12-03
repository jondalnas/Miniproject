package com.JoL.Miniproject.level.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.JoL.Miniproject.level.LevelLoader;

public class Converter {
	private static final double MM_TO_PIXEL = 64/16.933333;
	

	public static void main(String[] args) {
		convert("/level.svg", "HI");
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
			String line = scan.next();
			if (inPath) {
				line = line.toLowerCase();
				if (!line.contains("id=\"") && line.contains("d=\"")) {
					line = line.substring(line.indexOf("\"")+1);
					String result = line;
					
					while (!(line = scan.nextLine()).contains("Z")) result += line;
					
					result += line;
					result = result.substring(0, result.indexOf('Z'));
					
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
							currCord[0] += Double.parseDouble(numbers) * MM_TO_PIXEL;
							break;
						}
						case 'h': {
							currCord[1] += Double.parseDouble(numbers) * MM_TO_PIXEL;
							break;
						}
						case 'M': {
							String[] strNums = numbers.split(",");
							double[] nums = new double[] {Double.parseDouble(strNums[0]), Double.parseDouble(strNums[1])};

							currCord[0] = nums[0] * MM_TO_PIXEL;
							currCord[1] = nums[1] * MM_TO_PIXEL;
							break;
						}
						case 'L': {
							String[] strNums = numbers.split(",");
							double[] nums = new double[] {Double.parseDouble(strNums[0]), Double.parseDouble(strNums[1])};

							currCord[0] += nums[0] * MM_TO_PIXEL;
							currCord[1] += nums[1] * MM_TO_PIXEL;
							break;
						}
						case 'V': {
							currCord[0] += Double.parseDouble(numbers) * MM_TO_PIXEL;
							break;
						}
						case 'H': {
							currCord[1] += Double.parseDouble(numbers) * MM_TO_PIXEL;
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

		for (double[][] poly : polygons) {
			for (double[] coord : poly) {
				System.out.println("x: " + coord[0] + " y: " + coord[1]);
			}
		}
	}
}
