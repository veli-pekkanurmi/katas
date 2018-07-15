

import java.util.*;
import java.util.stream.Collectors;
import java.lang.*;
import java.io.*;

class Goldbach {
	public static void main(String[] args) {
		/*
		 Jaana Jantunen 10.07.2018 13.07.2018
		 Väinö Väinämöinen 21.07.2018 24.07.2018
		 Maija Mehiläinen 24.07.2018 02.08.2018
		 Ossi Onnekas 04.08.2018 04.08.2018
		 */

		String result = ":)";

		try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

			String readLine = "";
			int iterations = 0;
			String name = null;
			GregorianCalendar arrivalDate = new GregorianCalendar();
			GregorianCalendar departureDate = new GregorianCalendar();

			while (!(readLine = in.readLine()).equals("")) {

				List<String> line = Arrays.asList(readLine.split("\\ "));
				name = line.get(0) + " " + line.get(1);
				arrivalDate = parseDate(line.get(2));

				if (iterations < 1) {
					departureDate = parseDate(line.get(3));
				} else {
					if (arrivalDate.before(departureDate) || arrivalDate.equals(departureDate)) {
						result = name;
						break;
					}
					departureDate = parseDate(line.get(3));
				}
				iterations++;
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println(result);
	}

	private static GregorianCalendar parseDate(String input) {
		List<Integer> arrival = Arrays.asList(input.split("\\.")).stream().map(a -> Integer.parseInt(a)).collect(Collectors.toList());
		GregorianCalendar arrivalDate = new GregorianCalendar(arrival.get(2), arrival.get(1), arrival.get(0));
		return arrivalDate;
	}
}
