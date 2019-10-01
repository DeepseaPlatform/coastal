package binheap;//import gov.nasa.jpf.jvm.Verify;

//import gov.nasa.jpf.symbc.Debug;
//import gov.nasa.jpf.symbc.probsym.Analyze;

import za.ac.sun.cs.coastal.Symbolic;

class BinomialHeap {

	// internal class BinomialHeapNode
	public static class BinomialHeapNode {

		private int key; // element in current node

		private int degree; // depth of the binomial tree having the current node as its root

		private BinomialHeapNode parent; // pointer to the parent of the current node

		private BinomialHeapNode sibling; // pointer to the next binomial tree in the list

		private BinomialHeapNode child; // pointer to the first child of the current node

		public BinomialHeapNode(int k) {
			//	public BinomialHeapNode(Integer k) {
			key = k;
			degree = 0;
			parent = null;
			sibling = null;
			child = null;
		}

		public int getKey() { // returns the element in the current node
			return key;
		}

		public void setKey(int value) { // sets the element in the current node
			key = value;
		}

		public int getDegree() { // returns the degree of the current node
			return degree;
		}

		public void setDegree(int deg) { // sets the degree of the current node
			degree = deg;
		}

		public BinomialHeapNode getParent() { // returns the father of the current node
			return parent;
		}

		public void setParent(BinomialHeapNode par) { // sets the father of the current node
			parent = par;
		}

		public BinomialHeapNode getSibling() { // returns the next binomial tree in the list
			return sibling;
		}

		public void setSibling(BinomialHeapNode nextBr) { // sets the next binomial tree in the list
			sibling = nextBr;
		}

		public BinomialHeapNode getChild() { // returns the first child of the current node
			return child;
		}

		public void setChild(BinomialHeapNode firstCh) { // sets the first child of the current node
			child = firstCh;
		}

		public int getSize() {
			return (1 + ((child == null) ? 0 : child.getSize()) + ((sibling == null) ? 0
					: sibling.getSize()));
		}

		private BinomialHeapNode reverse(BinomialHeapNode sibl) {
			BinomialHeapNode ret;
			if (sibling != null)
				ret = sibling.reverse(this);
			else
				ret = this;
			sibling = sibl;
			return ret;
		}

		private BinomialHeapNode findMinNode() {
			BinomialHeapNode x = this, y = this;
			int min = x.key;

			while (x != null) {
				if (x.key < min) {
					y = x;
					min = x.key;
				}
				x = x.sibling;
			}

			return y;
		}

		// Find a node with the given key
		private BinomialHeapNode findANodeWithKey(int value) {
			BinomialHeapNode temp = this, node = null;
			while (temp != null) {
				if (temp.key == value) {
					node = temp;
					break;
				}
				if (temp.child == null)
					temp = temp.sibling;
				else {
					node = temp.child.findANodeWithKey(value);
					if (node == null)
						temp = temp.sibling;
					else
						break;
				}
			}

			return node;
		}

	}

	// end of helper class BinomialHeapNode

	//--------------------------------------------------------------------
	public static void covered(int br) {
		//Analyze.coverage("----> Covered " + br);
	}

	//-------------------------------------------------------------------

	private BinomialHeapNode Nodes;

	public int size;

	public BinomialHeap() {
		Nodes = null;
		size = 0;
	}

	// 2. Find the minimum key
	public int findMinimum() {
		return Nodes.findMinNode().key;
	}

	// 3. Unite two binomial heaps
	// helper procedure
	private void merge(BinomialHeapNode binHeap) {
		BinomialHeapNode temp1 = Nodes, temp2 = binHeap;
		while ((temp1 != null) && (temp2 != null)) {
			if (temp1.degree == temp2.degree) {
				covered(1);
				BinomialHeapNode tmp = temp2;
				temp2 = temp2.sibling;
				tmp.sibling = temp1.sibling;
				temp1.sibling = tmp;
				temp1 = tmp.sibling;
			} else {
				if (temp1.degree < temp2.degree) {
					if ((temp1.sibling == null)
							|| (temp1.sibling.degree > temp2.degree)) {
						covered(2);
						BinomialHeapNode tmp = temp2;
						temp2 = temp2.sibling;
						tmp.sibling = temp1.sibling;
						temp1.sibling = tmp;
						temp1 = tmp.sibling;
					} else {
						covered(3);
						temp1 = temp1.sibling;
					}
				} else {
					BinomialHeapNode tmp = temp1;
					temp1 = temp2;
					temp2 = temp2.sibling;
					temp1.sibling = tmp;
					if (tmp == Nodes) {
						covered(4);
						Nodes = temp1;
					} else {
						covered(5);
					}
				}
			}
		}

		if (temp1 == null) {
			temp1 = Nodes;
			while (temp1.sibling != null) {
				covered(6);
				temp1 = temp1.sibling;
			}
			temp1.sibling = temp2;
		} else {
			covered(7);
		}
	}

	// another helper procedure
	private void unionNodes(BinomialHeapNode binHeap) {
		merge(binHeap);

		BinomialHeapNode prevTemp = null, temp = Nodes, nextTemp = Nodes.sibling;

		while (nextTemp != null) {
			if ((temp.degree != nextTemp.degree)
					|| ((nextTemp.sibling != null) && (nextTemp.sibling.degree == temp.degree))) {
				covered(8);
				prevTemp = temp;
				temp = nextTemp;
			} else {
				if (temp.key <= nextTemp.key) {
					covered(9);
					temp.sibling = nextTemp.sibling;
					nextTemp.parent = temp;
					nextTemp.sibling = temp.child;
					temp.child = nextTemp;
					temp.degree++;
				} else {
					if (prevTemp == null) {
						covered(10);
						Nodes = nextTemp;
					} else {
						covered(11);
						prevTemp.sibling = nextTemp;
					}
					temp.parent = nextTemp;
					temp.sibling = nextTemp.child;
					nextTemp.child = temp;
					nextTemp.degree++;
					temp = nextTemp;
				}
			}
			covered(12);

			nextTemp = temp.sibling;
		}
	}

	// 4. Insert a node with a specific value
	public void insert(int value) {
		if (value > 0) {
			BinomialHeapNode temp = new BinomialHeapNode(value);
			if (Nodes == null) {
				Nodes = temp;
				size = 1;
			} else {
				unionNodes(temp);
				size++;
			}
		}
	}

	// 5. Extract the node with the minimum key
	public int extractMin() {
		if (Nodes == null)
			return -1;

		BinomialHeapNode temp = Nodes, prevTemp = null;
		BinomialHeapNode minNode = Nodes.findMinNode();
		while (temp.key != minNode.key) {
			covered(13);
			prevTemp = temp;
			temp = temp.sibling;
		}

		if (prevTemp == null) {
			covered(14);
			Nodes = temp.sibling;
		} else {
			covered(15);
			prevTemp.sibling = temp.sibling;
		}
		temp = temp.child;
		BinomialHeapNode fakeNode = temp;
		while (temp != null) {
			covered(16);
			temp.parent = null;
			temp = temp.sibling;
		}

		if ((Nodes == null) && (fakeNode == null)) {
			covered(17);
			size = 0;
		} else {
			if ((Nodes == null) && (fakeNode != null)) {
				covered(18);
				Nodes = fakeNode.reverse(null);
				size = Nodes.getSize();
			} else {
				if ((Nodes != null) && (fakeNode == null)) {
					covered(19);
					size = Nodes.getSize();
				} else {
					covered(20);
					unionNodes(fakeNode.reverse(null));
					size = Nodes.getSize();
				}
			}
		}

		return minNode.key;
	}

	// 6. Decrease a key value
	public void decreaseKeyValue(int old_value, int new_value) {
		BinomialHeapNode temp = Nodes.findANodeWithKey(old_value);
		if (temp == null)
			return;
		temp.key = new_value;
		BinomialHeapNode tempParent = temp.parent;

		while ((tempParent != null) && (temp.key < tempParent.key)) {
			int z = temp.key;
			covered(21);
			temp.key = tempParent.key;
			tempParent.key = z;

			temp = tempParent;
			tempParent = tempParent.parent;
		}
	}

	// 7. Delete a node with a certain key
	public void delete(int value) {
		if ((Nodes != null) && (Nodes.findANodeWithKey(value) != null)) {
			decreaseKeyValue(value, findMinimum() - 1);
			extractMin();
		}
	}

	public static void runTest(int[] options, int limit) {
		BinomialHeap b = new BinomialHeap();
		int round = 0;
		while (round < limit) {
			if (options[round] == 1) {
				//System.out.println("Insert v1");
				b.insert(options[limit + round]);
			} else if (options[round] == 2) {
				//System.out.println("Delete v1");
				b.delete(options[limit + round]);
			}
			/*
			else if (options[round] == 2) {
				System.out.println("ExtractMin round 1");
				b.extractMin();
			}
			*/
			round++;
		}
	}

	public static void runTestDriver(int length) {
		int[] values = new int[length * 2];
		int i = 0;
		while (i < 2 * length) {
			if (i < length)
				values[i] = Symbolic.makeSymbolicInt("c" + i);
			else
				values[i] = Symbolic.makeSymbolicInt("v" + i);
			i++;
		}
		runTest(values, length);
	}

	public static void main(String[] Argv) {

		runTestDriver(5);
/*
		BinomialHeap b = new BinomialHeap();
		b.delete(0);
		b.delete(0);
		b.insert(1);
		b.insert(2);
		b.insert(1);

		// b.insert(3);
		// b.insert(5);
		System.out.println("min: " + b.findMinimum());
		System.out.println("size: " + b.size);
		b.extractMin();
		System.out.println("min: " + b.findMinimum());
*/
	}

}
// end of class BinomialHeap
