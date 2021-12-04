package com.deb.roy.test.array;

import java.util.ArrayList;

public class StockBuySell {
	public static void main(String[] args) {
		int n = 7;
		int arr[] = {100,180,260,310,40,535,695};
		StockBuySellSolution.stockBuySell(arr, n);
	}
}

class StockBuySellSolution {
    //Function to find the days of buying and selling stock for max profit.
    static ArrayList<ArrayList<Integer>> stockBuySell(int A[], int n) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
        
        for(int i = 1; i < n; i++){
            ArrayList<Integer> temp = new ArrayList<>();
            if(A[i - 1] < A[i]){
                temp.add(i - 1);
                temp.add(i);
                ans.add(temp);
            }
        }
        
        return ans;
    }
}