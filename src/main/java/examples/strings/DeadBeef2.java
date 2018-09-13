package examples.strings;

public class DeadBeef2 {
	
	public static int analyse(char[] word) {
		if (word[0] == 'd') {
            if (word[1] == 'e') {
                if (word[2] == 'a') {
                    if (word[3] == 'd') {
                        if (word[4] == 'b') {
                            if (word[5] == 'e') {
                                if (word[6] == 'e') {
                                    if (word[7] == 'f') {
                                        return 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } /*else if (word[0] == 'f') {
            if (word[1] == 'u') {
                if (word[2] == 'n') {
                    if (word[3] == 'd') {
                        if (word[4] == 'a') {
                            if (word[5] == 'z') {
                                if (word[6] == 'e') {
                                    if (word[7] == 'd') {
                                        return 2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/
		return 0;
	}
	
    public static void main(String[] args) throws Exception {
    	char[] w = {'1', '2', '3', '4', '5', '6', '7', '8'};
    	int r = analyse(w);
    	System.out.println("Found Path " + r);
    }
}
