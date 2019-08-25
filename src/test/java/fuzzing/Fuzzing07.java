package fuzzing;

import za.ac.sun.cs.coastal.Symbolic;

public class Fuzzing07 {

    public static void main(String[] args) throws Exception {
    	analyse("123456789012");
    	// analyse("DEADHELPBEEF");
    	// analyse("deadhelpbeef");
    }

	public static void analyse(String word) {
		if (word.charAt(0) == 'd') {
            if (word.charAt(1) == 'e') {
                if (word.charAt(2) == 'a') {
                    if (word.charAt(3) == 'd') {
                    	if (word.startsWith("help", 4)) {
                    		if (word.charAt(8) == 'b') {
                    			if (word.charAt(9) == 'e') {
                    				if (word.charAt(10) == 'e') {
                    					if (word.charAt(11) == 'f') {
                    						System.out.println("DEADHELPBEEF");
                    						Symbolic.stop("DEADHELPBEEF");
                    					}
                    				}
                    			}
                    		}
                    	}
                    }
                }
            }
//		} else {
//			Symbolic.stop("REACHED");
        }
	}
	
}
