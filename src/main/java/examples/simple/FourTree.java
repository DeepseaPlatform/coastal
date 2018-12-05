package examples.simple;

public class FourTree {

	public static void main(String[] args) {
		compute(0, 0, 0, 0, 0);
	}

	private static void compute(int x, int y, int z, int p, int q) {
		if (x == 5) {
			if (y > x) {
				if (z == x) {
					if (p < 10) {
						if (q > 2 * p) {
							System.out.println("XYZPQ");
						} else {
							System.out.println("XYZP-Q");
						}
					} else {
						if (q > 2 * p) {
							System.out.println("XYZQ-P");
						} else {
							System.out.println("XYZ-PQ");
						}
					}
				} else {
					if (p < 10) {
						if (q > 2 * p) {
							System.out.println("XYPQ-Z");
						} else {
							System.out.println("XYP-ZQ");
						}
					} else {
						if (q > 2 * p) {
							System.out.println("XYQ-ZP");
						} else {
							System.out.println("XY-ZPQ");
						}
					}
				}
			} else {
				if (z != x) {
					if (p < 10) {
						if (q > 2 * p) {
							System.out.println("XZPQ-Y");
						} else {
							System.out.println("XZP-YQ");
						}
					} else {
						if (q > 2 * p) {
							System.out.println("XZQ-YP");
						} else {
							System.out.println("XZ-YPQ");
						}
					}
				} else {
					if (p < 10) {
						if (q > 2 * p) {
							System.out.println("XPQ-YZ");
						} else {
							System.out.println("XP-YZQ");
						}
					} else {
						if (q > 2 * p) {
							System.out.println("XQ-YZP");
						} else {
							System.out.println("X-YZPQ");
						}
					}
				}
			}
		} else {
			if (y > x) {
				if (z == x) {
					if (p < 10) {
						if (q > 2 * p) {
							System.out.println("YZPQ-X");
						} else {
							System.out.println("YZP-XQ");
						}
					} else {
						if (q > 2 * p) {
							System.out.println("YZQ-XP");
						} else {
							System.out.println("YZ-XPQ");
						}
					}
				} else {
					if (p < 10) {
						if (q > 2 * p) {
							System.out.println("YPQ-XZ");
						} else {
							System.out.println("YP-XZQ");
						}
					} else {
						if (q > 2 * p) {
							System.out.println("YQ-XZP");
						} else {
							System.out.println("Y-XZPQ");
						}
					}
				}
			} else {
				if (z != x) {
					if (p < 10) {
						if (q > 2 * p) {
							System.out.println("ZPQ-XY");
						} else {
							System.out.println("ZP-XYQ");
						}
					} else {
						if (q > 2 * p) {
							System.out.println("ZQ-XYP");
						} else {
							System.out.println("Z-XYPQ");
						}
					}
				} else {
					if (p < 10) {
						if (q > 2 * p) {
							System.out.println("PQ-XYZ");
						} else {
							System.out.println("P-XYZQ");
						}
					} else {
						if (q > 2 * p) {
							System.out.println("Q-XYZP");
						} else {
							System.out.println("-XYZPQ");
						}
					}
				}
			}
		}
	}

}
