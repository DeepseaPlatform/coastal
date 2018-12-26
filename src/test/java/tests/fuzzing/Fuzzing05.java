package tests.fuzzing;

import za.ac.sun.cs.coastal.Symbolic;

public class Fuzzing05 {

    public static void main(String[] args) throws Exception {
    	char[] w = {'1', '2', '3', '4', '5', '6', '7', '8'};
    	analyse(w);
    }

	public static void analyse(char[] word) {
		if (word[0] == 'd') {
			Symbolic.mark(1);
            if (word[1] == 'e') {
            	Symbolic.mark(2);
                if (word[2] == 'a') {
                	Symbolic.mark(3);
                    if (word[3] == 'd') {
                    	Symbolic.mark(4);
                        if (word[4] == 'b') {
                        	Symbolic.mark(5);
                            if (word[5] == 'e') {
                            	Symbolic.mark(6);
                                if (word[6] == 'e') {
                                	Symbolic.mark(7);
                                    if (word[7] == 'f') {
                                        Symbolic.stop("DEADBEEF");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
	}
	
}
