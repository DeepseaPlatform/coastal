package examples.learning;

import za.ac.sun.cs.coastal.Symbolic;

public class Example {

	public static boolean foo(int[] input) {
		int state = 0;
		int idx = 0;

		while (idx <= 2) {
			if (state == 0) {
				if (input[idx] >= 51 && input[idx] <= 100) {
					if (idx == 0 && input[1] == 999 && input[2] == 999) {
						return false;
					}
					state = 1;
				} else {
					state = 0;
				}
			} else if (state == 1) {
				if (input[idx] >= 0 && input[idx] <= 20) {
					state = 2;
				} else {
					return false;
				}
			} else if (state == 2) {
				return (input[idx] >= 0 && input[idx] <= 20);
			}
			idx++;
		}
		return (state == 0);
	}

	public static boolean candidate1(int[] input) {
		return true;
	}

	public static boolean check(int[] input) {
		boolean resultCandidate = candidate1(input);
		boolean resultFoo = foo(input);
		return resultCandidate == resultFoo;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] inputValues = { 10, 20, 20 };
		boolean result = check(inputValues);
		if (!result) {
			Symbolic.printPC("Not Accepted");
		}
	}

}

/*
 * After first iteration we get
 *  
X$2<=100 && X$2>=51 && X$1<51   && X$0<51
X$2<=20  && X$2>=0  && X$1<=100 && X$1>=51 && X$0<51
X$2 >20  && X$2>=0  && X$1<=100 && X$1>=51 && X$0<51
X$2<=100 && X$2>=51 && X$1>100  && X$1>=51 && X$0<51
X$2>20   && X$2>=0  && X$1<=20  && X$1>=0  && X$0<=100 && X$0>=51
X$1>20   && X$1>=0  && X$0<=100 && X$0>=51
X$2<=100 && X$2>=51 && X$1<51   && X$0>100 && X$0>=51
X$2<=20  && X$2>=0  && X$1<=100 && X$1>=51 && X$0>100 && X$0>=51
X$2>20   && X$2>=0  && X$1<=100 && X$1>=51 && X$0>100 && X$0>=51
X$2<=100 && X$2>=51 && X$1>100  && X$1>=51 && X$0>100 && X$0>=51
*/


