package com.deb.roy.test;

public class NumberOfCoin {

	public static void main(String[] args) {
		int v = 11, m = 4, coins[] = {9, 6, 5, 1};
		System.out.println(NumberOfCoinSolution.minNumberOfCoins(coins, m, v));
	}

}

class NumberOfCoinSolution {
	public static int minNumberOfCoins(int coins[], int M, int V) {
		int count = -1;
		for(int i=0; i<M; i++) {
			int result = countCoins(coins, i, V);
			System.out.println(result);
		}
	    return count;
	}
	static int countCoins(int coins[], int start, int V) {
		int count = 0;
		for(int coin : coins) {
			int num = V / coin;
			if(num > 0) {
				count = count + num;
				V = V - (num * coin);
			}
		}
		if (V == 0) return count;
	    return -1;
	}
}