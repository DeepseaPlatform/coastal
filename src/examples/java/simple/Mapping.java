package simple;

import java.util.HashMap;
import java.util.Map;

public class Mapping {

	public static void main(String[] args) {
		Map<Integer, Integer> map = new HashMap<>();
		run(map, 1, 0);
		System.out.println("DONE");
	}

	private static void run(Map<Integer, Integer> map, int x, int y) {
		map.put(x, y);
		int z = map.get(x);
		if (z == x) {
			System.out.println("X IS VALUE");
		} else {
			System.out.println("X NOT VALUE");
		}
	}

//	public static void main(String[] args) {
//		run(1, 0);
//		System.out.println("DONE");
//	}
//
//	private static void run(int x, int y) {
//		int z = Math.max(x, y);
//		if (z == x) {
//			System.out.println("X IS MAX");
//		} else {
//			System.out.println("X NOT MAX");
//		}
//	}

}
