package com.deb.roy.test.array;

public class SearchInsertPosition {

	public static void main(String[] args) {
		int n = 8, k = 1;
		int[] arr = {-12, -11, -3, 5, 6, 15, 16, 18};
		int pos = SearchInsertPositionSolution.searchInsertK(arr, n, k);
		System.out.println(pos);
	}

}

class SearchInsertPositionSolution {
    static int searchInsertK(int Arr[], int N, int k) {
    	for(int i=0; i<N; i++) {
    		if(Arr[i] >= k) {
    			return i;
    		}
    	}
        return N;
    }
}