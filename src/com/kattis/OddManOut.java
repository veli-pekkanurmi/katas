package com.kattis;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class OddManOut {

    public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);   

    int rounds = Integer.parseInt(sc.nextLine());
 
    for (int y = 1 ; rounds >= y ; y++) {
    	
    		int noUse = Integer.parseInt(sc.nextLine());
			String line = sc.nextLine();	
			List <String> lineList = Arrays.asList(line.split(" "));
		
			String single = "Case #" + y + ": ";
			for(String i : lineList) {	
				int count = (int) lineList
						.stream()
						.filter(p -> p.equals(i))
						.count();
				if (count == 1) {
					single += i;
				}
			}
			System.out.println(single);
	    }
    }
}