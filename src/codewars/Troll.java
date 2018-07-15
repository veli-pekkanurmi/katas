package codewars;

import java.util.stream.Collectors;

public class Troll {

	public static void main(String[] args) {

		String teksti = "fiosugioag";
		System.out.println(disemvowel(teksti));
	}

	public static String disemvowel(String str) {

		String vocals = "aeiouåäöAEIOUÅÄÖ"; // yY removed
		// return str.replaceAll("[aeiouAOEIU]","");
		return str.chars().mapToObj(aa -> "" + (char) aa).filter(a -> !vocals.contains(a))
				.collect(Collectors.joining(""));

	}

}
