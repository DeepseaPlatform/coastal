package apachetrie;

import java.util.TreeMap;

//import org.apache.commons.collections4.trie.PatriciaTrie;

import za.ac.sun.cs.coastal.Symbolic;

public class TrieTest {

	
	private static void analyse(String one, String two, String key) {
		TreeMap<String, Integer> map = new TreeMap<String,Integer>();
		map.put(one, 1);
		map.put(two, 2);
		PatriciaTrie trie = new PatriciaTrie(map);
		if (!trie.containsKey(one)) {
			System.out.println("BUG");
			Symbolic.stop("BUG");
		} else {
			System.out.println("FINE");
		}
				
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		analyse("one","one\u0000","one");
	}

}
