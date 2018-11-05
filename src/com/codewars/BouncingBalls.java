package com.codewars;

public class BouncingBalls {

	public static int bouncingBall(double initHeight, double bounceProportion) {

		int bounces = 1;
		double newHeight = initHeight * bounceProportion;

		if (newHeight < 1) {
			return 0;
		}

		while (newHeight > 1) {
			bounces += 1;
			newHeight *= bounceProportion;
		}

		return bounces;

	}
}