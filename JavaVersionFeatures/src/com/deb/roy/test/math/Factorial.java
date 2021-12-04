package com.deb.roy.test.math;

public class Factorial {

	public static void main(String[] args) {
		System.out.println(factorial(10));
	}
	
	static long factorial(long num) {
		// Function to find factorial of a positive integer
		if (num == 0 || num == 1) {
			return 1;
		}
		if(num < 0 || num > 25) {
			throw new RuntimeException("Input is outside range, enter positive values from 0-25 only.");
		}
		return num * factorial(num-1);
	}
}
