package com.codewars;

import static org.junit.Assert.*;

import org.junit.Test;

public class BouncingBallsTest {

	@Test
	public void test() {
		assertEquals(0, BouncingBalls.bouncingBall(2, 0.3));
		assertEquals(3, BouncingBalls.bouncingBall(30, 0.3));
		assertEquals(2, BouncingBalls.bouncingBall(4, 0.5));
	}
	

}
