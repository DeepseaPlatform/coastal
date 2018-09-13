package examples.strings;

public class DeadBeef {
	
	public static int analyse(String word) {
		if (word.charAt(0) == 'd') {
            if (word.charAt(1) == 'e') {
                if (word.charAt(2) == 'a') {
                    if (word.charAt(3) == 'd') {
                        if (word.charAt(4) == 'b') {
                            if (word.charAt(5) == 'e') {
                                if (word.charAt(6) == 'e') {
                                    if (word.charAt(7) == 'f') {
                                        return 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (word.charAt(0) == 'f') {
            if (word.charAt(1) == 'u') {
                if (word.charAt(2) == 'n') {
                    if (word.charAt(3) == 'd') {
                        if (word.charAt(4) == 'a') {
                            if (word.charAt(5) == 'z') {
                                if (word.charAt(6) == 'e') {
                                    if (word.charAt(7) == 'd') {
                                        return 2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
		return 0;
	}
	
    public static void main(String[] args) throws Exception {
    	int r = analyse("hello111");
    	System.out.println("Found Path " + r);
    }
}
