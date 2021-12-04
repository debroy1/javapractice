package com.deb.roy.test.array;

public class RotateArray {

	public static void main(String[] args) {
		int n = 10, d = 3;
		int arr[] = {2,4,6,8,10,12,14,16,18,20};
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
		RotateArraySolution.rotateArr(arr, d, n);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

}

class RotateArraySolution {
    //Function to rotate an array by d elements in counter-clockwise direction. 
    static void rotateArr(int arr[], int d, int n) {
        int array[] = new int[n];
        for (int i = d; i < arr.length; i++) {
			array[i-d] = arr[i];
		}
        for (int i = 0; i < d; i++) {
			array[n-d+i] = arr[i];
		}
		// Most important step to point the source array to point to modified array
		System.arraycopy(array, 0, arr, 0, n);
    }
}