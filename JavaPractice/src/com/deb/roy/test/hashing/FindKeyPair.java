package com.deb.roy.test.hashing;

import java.util.HashMap;
import java.util.Map;

public class FindKeyPair {

	public static void main(String[] args) {
		//int n = 5, x = 4;
		//int arr[] = {1, 2, 2, 6, 7};
		int n = 4, x = 12;
		int arr[] = {1, 2, 6, 7};
		System.out.println(FindKeyPairSolution.hasArrayTwoCandidates(arr, n, x));
	}

}

class FindKeyPairSolution {
    static boolean hasArrayTwoCandidates(int arr[], int n, int x) {
    	Map<Integer, Integer> map = new HashMap<>();
    	int counter = 0;
    	for(int i: arr) {
    		map.put(counter, i);
    		counter++;
    	}
    	
    	return false;
    }

}
