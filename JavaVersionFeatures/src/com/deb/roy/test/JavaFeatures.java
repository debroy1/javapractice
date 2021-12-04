package com.deb.roy.test;

public class JavaFeatures {
	String firstName = "Deb";

	void testVar() {
		var lastName = "Roy";
		System.out.println("lastname: " + lastName);
	}
	
	public static void main(String[] args) {
		Baby baby = new Baby("Gaga", 2);
		System.out.println(baby);
		
		var value = 1;
		boolean result = switch (value) {
		    case 1 -> true;
		    case 2 -> false;
		    default -> throw new IllegalArgumentException("something is murky!");
		};
		System.out.println("result: " + result);
	}

}

record Baby(String name, int age) {
	
}
