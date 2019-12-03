package com.JoL.Miniproject.level.converter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import com.JoL.Miniproject.level.LevelLoader;

public class Converter {

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
		
		boolean inPath = false;
		while (scan.hasNext()) {
			String line = scan.next();
			if (inPath) {
				System.out.println(line);
				if (!line.contains("id=\"") && line.contains("d=\"")) {
					//System.out.println(line);
					//line = line.substring(line.indexOf('"'), line.toLowerCase().lastIndexOf('z')-1);
					//System.out.println(line);
				}
				
				if (line.contains("z")) inPath = false;
			}
			
			if (line.contains("<path")) inPath = true;
		}
	}
}
