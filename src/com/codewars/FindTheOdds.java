package com.codewars;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class FindTheOdds {

	public static void main(String[] args) {
		// int[] a = { 2, 2, 3, 4, 4 };
		int[] a  = {20,1,-1,2,-2,3,3,5,5,1,2,4,20,4,-1,-2,5};

		HashMap<Integer, Integer> ints = new HashMap<Integer, Integer>();

		for (int i = 0; i < a.length; i++) {
			int number = a[i];
			if (ints.get(number) == null) {
				ints.put(number, 1);
			} else {
				ints.put(number, ints.get(number) + 1);
			}
		}

		Set<Integer> keys = ints.keySet();
		Iterator<Integer> it = keys.iterator();

		while (it.hasNext()) {
			int key = it.next();
			int value = ints.get(key);

			System.out.println("key: " + key + " value: " + value);
			if ((value % 2) != 0) {
				System.out.println("odd: " + key);
			}
		}

	}
	/*
	 * import static java.util.Arrays.stream;

public class FindOdd {
  public static int findIt(int[] arr) {
    return stream(arr).reduce(0, (x, y) -> x ^ y);
  }
}

-----

public class FindOdd {
  public static int findIt(int[] A) {
    int xor = 0;
    for (int i = 0; i < A.length; i++) {
      xor ^= A[i];
    }
    return xor;
  }
}



	 * */
	
	
}
