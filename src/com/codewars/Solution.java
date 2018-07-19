package com.codewars;

public class Solution {

	public static void main(String[] args) {

		System.out.println(validatePin("1234")); // true
		System.out.println(validatePin("123456")); // true

		System.out.println(validatePin("12345")); // false
		System.out.println(validatePin("12345K")); // false
		System.out.println(validatePin("12345k")); // false

	}

	public static boolean validatePin(String pin) {

		if ((pin.length() != 4 && pin.length() != 6))
			return false;

		try {
			Integer.parseInt(pin);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
		
		//return pin.matches("\\d{4}|\\d{6}");
	}

}
