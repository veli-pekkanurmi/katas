package com.kattis;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class NastyHacks {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int rounds = Integer.parseInt(sc.nextLine());
		String output = null;

		for (int y = 1; rounds >= y; y++) {
			
			List <String> line = Arrays.asList((sc.nextLine().split(" ")));
			List <Integer> lineInts = line.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());	
			
			int marketingUplift = (lineInts.get(1) - lineInts.get(2)) - lineInts.get(0);

			if (marketingUplift < 0)
				output = "do not advertise";			
			if (marketingUplift == 0)
				output = "does not matter";
			if (marketingUplift > 0)
				output = "advertise";
			
			System.out.println(output);
		}
	}

}
