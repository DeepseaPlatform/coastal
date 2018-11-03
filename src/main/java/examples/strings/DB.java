package examples.strings;

import za.ac.sun.cs.coastal.Symbolic;

public class DB {

	public static int analyse4(String word) {
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

	public static int analyse2(String word) {
		if (word.startsWith("help")) {
			if (word.charAt(4) == ' ') {
				if (word.charAt(5) == 'd') {
					if (word.charAt(6) == 'e') {
						if (word.charAt(7) == 'a') {
							if (word.charAt(8) == 'd') {
								if (word.charAt(9) == 'b') {
									if (word.charAt(10) == 'e') {
										if (word.charAt(11) == 'e') {
											if (word.charAt(12) == 'f') {
												return 1;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			return 0;
		}
		return 0;
	}

	public static boolean startsWith(String word, String match, int idx) {
		for (int i = idx; i < word.length(); i++) {
			if (word.charAt(i) != match.charAt(i - idx)) {
				return false;
			}
		}
		return true;
	}

	public static int analyse(String word) {
		if (word.charAt(0) == 'd') {
			if (word.charAt(1) == 'e') {
				if (word.charAt(2) == 'a') {
					if (word.charAt(3) == 'd') {
						if (word.startsWith("help", 4)) {
							if (word.charAt(9) == 'b') {
								if (word.charAt(10) == 'e') {
									if (word.charAt(11) == 'e') {
										if (word.charAt(12) == 'f') {
											return 1;
										}
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

	public static int analyse6(String word) {
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
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = analyse("pumpkinpiesoup");
		if (r != 0) {
			Symbolic.stop("BUG");
		}
	}
}
