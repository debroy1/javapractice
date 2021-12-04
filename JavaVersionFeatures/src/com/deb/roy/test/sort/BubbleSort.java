package com.deb.roy.test.sort;

public class BubbleSort {

	public static void main(String[] args) {
		int arr[] = {23, 45, 1, 56, 9, 17, 87, 44, 12};
		iterativeBubbleSort(arr);
		for(int i : arr) {
			System.out.print(i + " ");
		}
	}
	
	static void iterativeBubbleSort(int arr[]) {
		for(int j=0; j<arr.length; j++) {
			for(int i=0; i<arr.length-j-1; i++) {
				if(arr[i] > arr[i+1]) {
					int temp = arr[i+1];
					arr[i+1] = arr[i];
					arr[i] = temp;
				}
			}
		}
	}

}
