package com.deb.roy.test.array;

import java.util.ArrayList;
import java.util.Collections;

public class PlusOne {

	public static void main(String[] args) {
		int n = 10;
		int a[] = {3, 7, 2, 7, 2, 8, 4, 4, 3, 4};
		ArrayList<Integer> arr = new ArrayList<Integer>(n);
		for (int i : a) {
		    arr.add(i);
		}
		ArrayList<Integer> result = PlusOneSolution.increment(arr, n);
		for (Integer integer : result) {
			System.out.print(integer + " ");
		}
	}

}

// Use BigInt for even bigger numbers
class PlusOneSolution {
    static ArrayList<Integer> increment(ArrayList<Integer> arr , int N) {
    	// Take out individual elements and add them to find out total; add one to it
    	Collections.reverse(arr);
    	Long sum = 1l;
    	Long placeVal = 1l;
    	for (Integer integer : arr) {
			sum = sum + (integer * placeVal);
			placeVal = (placeVal * 10);
		}
    	// Divide by 10, 100, 1000 etc and find the reminder to put into result list
    	ArrayList<Integer> result = new ArrayList<Integer>(N);
    	Long quotient = sum;
    	Long remainder = 0l;
    	do {
    		remainder = (quotient % 10);
    		quotient = (quotient / 10);
    		result.add(remainder.intValue());
    	} while (quotient != 0);
    	Collections.reverse(result);
        return result;
    }
};