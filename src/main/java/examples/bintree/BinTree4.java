package examples.bintree;
//import gov.nasa.jpf.symbc.Debug;

//
// Copyright (C) 2006 United States Government as represented by the
// Administrator of the National Aeronautics and Space Administration
// (NASA).  All Rights Reserved.
//
// This software is distributed under the NASA Open Source Agreement
// (NOSA), version 1.3.  The NOSA has been approved by the Open Source
// Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
// directory tree for the complete NOSA document.
//
// THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
// KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
// LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
// SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
// A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
// THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
// DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//

public class BinTree4 {

	private static class Node {

		public int value;

		public Node left, right;

		Node(int x) {
			value = x;
			left = null;
			right = null;
		}

	}

	private Node root;

	public BinTree4() {
		root = null;
	}

	private void add(int x) {
		Node current = root;
		if (root == null) {
			root = new Node(x);
			return;
		}
		while (current.value != x) {
			if (current.value > x) {
				if (current.left == null) {
					current.left = new Node(x);
				} else {
					current = current.left;
				}
			} else {
				if (current.right == null) {
					current.right = new Node(x);
				} else {
					current = current.right;
				}
			}
		}
	}

	private boolean remove(int x) {
		Node current = root;
		Node parent = null;
		boolean branch = true; //true =left, false =right
		while (current != null) {
			if (current.value == x) {
				Node bigson = current;
				while (bigson.left != null || bigson.right != null) {
					parent = bigson;
					if (bigson.right != null) {
						bigson = bigson.right;
						branch = false;
					} else {
						bigson = bigson.left;
						branch = true;
					}
				}
				if (bigson == root) {
					root = null;
					return true;
				}
				if (parent != null) {
					if (branch) {
						parent.left = null;
					} else {
						parent.right = null;
					}
				}
				if (bigson != current) {
					current.value = bigson.value;
				}
				return true;
			}
			parent = current;
			if (current.value > x) {
				current = current.left;
				branch = true;
			} else {
				current = current.right;
				branch = false;
			}
		}
		return false;
	}

	public static void runExplicit4(int option1, int option2, int option3, int option4, int value1, int value2,
			int value3, int value4) {
		BinTree4 b = new BinTree4();
		if (option1 == 1) {
			b.add(value1);
		} else {
			b.remove(value1);
		}
		if (option2 == 1) {
			b.add(value2);
		} else {
			b.remove(value2);
		}
		if (option3 == 1) {
			b.add(value3);
		} else {
			b.remove(value3);
		}
		if (option4 == 1) {
			b.add(value4);
		} else {
			b.remove(value4);
		}
	}

	public static void main(String[] args) {
		runExplicit4(1, 1, 1, 1, 1, 1, 1, 1);
	}

}
