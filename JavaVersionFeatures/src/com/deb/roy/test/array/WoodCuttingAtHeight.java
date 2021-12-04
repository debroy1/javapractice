package com.deb.roy.test.array;

public class WoodCuttingAtHeight {

	public static void main(String[] args) {
    	int n = 6, k = 47;
    	int tree[] = {81, 13, 36, 65, 38, 69};
    	int height = WoodCuttingAtHeightSolution.find_height(tree, n, k);
    	System.out.println(height);
	}

}

class WoodCuttingAtHeightSolution {
    static int find_height(int tree[], int n, int k) {
    	int eachCut = 1;
    	int height = 0;
    	int largest = largest(tree);
    	while(eachCut <= largest) {
    		height = 0;
    		for(int i=0; i<n; i++) {
    			if(tree[i] >= eachCut) {
    				height = height + (tree[i] - eachCut);
    			}
    		}
    		if(height == k) {
    			return eachCut;
    		} else {
    			eachCut++;
    		}
    	}
    	return -1;
    }
    
    static int largest(int arr[])
    {
        int i;
        int max = arr[0];
        for (i = 1; i < arr.length; i++) {
            if (arr[i] > max)
                max = arr[i];
        }
        return max;
    }
}