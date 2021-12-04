package com.deb.roy.test.binarytree;

public class BubbleSort {

	public static void main(String[] args) {
		int n = 5;
		int arr[] = {4, 1, 3, 9, 7};
		BubbleSortSolution.recursiveBubbleSort(arr, n);
		for(int num : arr) {
			System.out.print(num + " ");
		}
	}

}

class BubbleSortSolution {
    //Function to sort the array using bubble sort algorithm.
	static void recursiveBubbleSort(int arr[], int n) {
        if (n == 1) {
        	return;
        }
        for(int i=0; i<n-1; i++) {
        	if(arr[i] > arr[i+1]) {
        		int temp = arr[i];
        		arr[i] = arr[i+1];
        		arr[i+1] = temp;
        	}
        }
        recursiveBubbleSort(arr, n-1);
    }
}