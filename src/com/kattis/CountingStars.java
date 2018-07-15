package com.kattis;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CountingStars {

	public static void main(String[] args) {

		try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

			// run while data is comming in
			while (!in.readLine().equals("")) { // pontentiaalinen error

				List<String> line = Arrays.asList((in.readLine().split(" ")));
				List<Integer> lineInts = line.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
				int rows = lineInts.get(0);
				int columns = lineInts.get(1);
				
				int stars = 0;
				List<String> newStarLine = new ArrayList();
				List<String> previousStarLine = new ArrayList();
				
				// One pattern handeling
				for (int y = 1; rows >= y; y++) {

					newStarLine = Arrays.asList(in.readLine());
					
					/*
					 // first line
					if (y == 1) {
						if (newStarLine.contains("-"));
							stars ++;
						previousStarLine = newStarLine;
						continue;
					};
					*/
					
					
					for (int i = 0 ; newStarLine.size() > 0 ; i++) {
						if(
								(newStarLine.get(i).equals("-") &&
								!newStarLine.get(i-1).equals("-") &&
								!newStarLine.get(i+1).equals("-")) ||
								previousStarLine.get(i).equals("-")
								) {
							stars++;
						}
					}

					// tässä vertaile listojen indeksit - jos samaa lukua matchaa (+/- 1), niin = samaa tähteä. Muussa tapauksessa uusi tähti.
					
					previousStarLine = newStarLine;
				}

				String output = null;
				System.out.println(output);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
