package codewars;

import java.util.stream.Collectors;


public class TwoToOne {
    
    public static String longest (String s1, String s2) {
       
      String combined = s1 + s2;       
   		String result = combined
				.chars()
				.mapToObj(yy -> "" + (char)yy)
				.sorted()
				.distinct()
				.collect(Collectors.joining());

    return result;
    }
}