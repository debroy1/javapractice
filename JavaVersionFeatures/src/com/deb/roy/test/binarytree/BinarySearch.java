package com.deb.roy.test.binarytree;

public class BinarySearch {

	public static void main(String[] args) {
		int n = 5, k = 6;
		int arr[] = {1,2,3,4,6};
		System.out.println(BinarySearchSolution.searchInSorted(arr, n, k));
	}

}

class BinarySearchSolution{
    static int searchInSorted(int arr[], int N, int K) {
        return search(arr, 0, N-1, K);
    }
    
    static int search(int arr[], int start, int end, int num) {
    	if(end >= start) {
	    	int mid = (start + end) / 2;
	    	if(arr[mid] == num) {
	    		// found it
	    		return mid;
	    	} else if (arr[mid] > num) {
	    		// move to left
	    		return search(arr, start, mid-1, num);
	    	} else {
	    		// move to right
	    		return search(arr, mid+1, end, num);
	    	}
    	}
    	return -1;
    }
}