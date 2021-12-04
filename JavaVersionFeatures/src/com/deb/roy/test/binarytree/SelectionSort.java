package com.deb.roy.test.binarytree;

public class SelectionSort {

	public static void main(String[] args) {
		int arr[] = {23, 45, 1, 56, 9, 17, 87, 44, 12};
		selectionSort(arr);
		for(int i : arr) {
			System.out.print(i + " ");
		}
	}
	
	static void selectionSort(int arr[]) {
		for(int unsortedInd = arr.length-1; unsortedInd > 0; unsortedInd--) {
			int largest = 0;
			for(int i=1; i<=unsortedInd; i++) {
				if(arr[i] > arr[largest]) {
					largest = i;
				}
				int temp = arr[largest];
				arr[largest] = arr[unsortedInd];
				arr[unsortedInd] = temp;
			}
		}
	}
}
