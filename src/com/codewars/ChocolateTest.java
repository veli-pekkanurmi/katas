package com.codewars;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ChocolateTest {

	@Test
	public void breakTest() {
		assertEquals(0, Chocolate.breakChocolate(1, 1));
		assertEquals(24, Chocolate.breakChocolate(5, 5));
		assertEquals(4, Chocolate.breakChocolate(1, 5));
		assertEquals(0, Chocolate.breakChocolate(1, 1));
		assertEquals(0, Chocolate.breakChocolate(0, 0));
	}

}
