package coinchange;

import za.ac.sun.cs.coastal.Symbolic;

public class CoinDriver {

	public static void main(String[] args) {
		mainProcess(5);
	}

	public static void mainProcess(int length) {
		int[] coins = new int[2 * length];
		for (int i = 0; i < 2 * length; i++) {
			if (i < length) {
//                coins[i] = (i);
				coins[i] = Symbolic.makeSymbolicInt("c" + i);
			} else {
//                coins[i] = (i);
				coins[i] = Symbolic.makeSymbolicInt("v" + i);
			}
		}
		runTest(coins, 11 * length);
	}

	public static void runTest(int[] coins, int amount) {
		CoinChange cc = new CoinChange();
		int change = cc.change(coins, amount);
		int coin = cc.minimumCoins(coins, amount);
		System.out.println(">> " + change + " : " + coin);
	}
}
