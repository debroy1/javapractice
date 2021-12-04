package com.deb.roy.test.array;

import java.util.ArrayList;
import java.util.HashSet;

public class FindMissingInSecondArray {

	public static void main(String[] args) {
		int N = 5, M = 5;
		long A[] = {4, 3, 5, 9, 11};
		long B[] = {4, 9, 3, 11, 10};
		ArrayList<Long> missingNumber = FindMissingInSecondArraySolution.findMissing(A, B, N, M);
		for (Long missing : missingNumber) {
			System.out.print(missing + " ");
		}
	}

}

class FindMissingInSecondArraySolution {
    static ArrayList<Long> findMissing(long A[], long B[], int N, int M) {
		ArrayList<Long> listA = new ArrayList<>(N);
		for (long i : A) {
			listA.add(i);
		}
		HashSet<Long> setB = new HashSet<>(M);
		for (long j : B) {
			setB.add(j);
		}
    	ArrayList<Long> result = new ArrayList<>();

    	for(Long numA: listA) {
    		if(!setB.contains(numA)) {
    			result.add(numA);
    		}
    	}

    	return result;
    }
}
