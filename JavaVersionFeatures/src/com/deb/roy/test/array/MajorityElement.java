package com.deb.roy.test.array;

import java.util.Arrays;

public class MajorityElement {

	public static void main(String[] args) {
		int size = 5; 
		int a[] = {3,1,3,3,2};
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
		int result = MajorityElementSolution.majorityElement(a, size);
		System.out.print(result);
	}

}

class MajorityElementSolution
{
    static int majorityElement(int a[], int size)
    {
    	// If each element appears once then -1, if any element present more than N/2 times, then return that
    	// Sort array and count which element is present how many times
    	if(size == 1) {
    		return a[0];
    	}
    	Arrays.sort(a);
    	int count = 0;
    	for (int i = 1; i < a.length; i++) {
			if(a[i] == a[i-1]) {
				count++;
				if(count >= (size/2)) {
					return a[i];
				}
			} else {
				count = 0;
			}
		}
        return -1;
    }
}
