package bintree;

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

import za.ac.sun.cs.coastal.Symbolic;

class Node {
    public int value;
    Node left, right;

    public Node(int x) {
        value = x;
        left = null;
        right = null;
    }

}

public class BinTree {

    private Node root;

    public int intendedSize = 0;

    public BinTree() {
        root = null;
    }


    public static void covered(int br) {
        //if (br == 999)
        //	Analyze.coverage(""+br);
    }


    public static void gen(int br, Node n0, int x, Node n1, Node n2) {
        //System.out.println(br);
        covered(br);
        //int c = gen_native(br, n0, x, n1, n2);
        //if (c != 0)
        //	outputTestSequence(c);
    }


    public boolean checkTree() {
        //return checkTree(root,Integer.MIN_VALUE, Integer.MAX_VALUE);
        return checkTree(root,0,9);
    }

    private boolean checkTree(Node n, int min, int max) {
        if (n == null)
            return true;
        if (n.value < min || n.value > max)
            return false;
        boolean resLeft = checkTree(n.left,min,n.value-1);
        if(!resLeft)
            return false;
        else
            return checkTree(n.right,n.value+1,max);
    }

    public String linearize() {
        if (!checkTree()) {
            System.out.println("NOT A TREE!!!");
            return "NotABST";
        }
        return linearize(root);
    }

    public String linearize(Node n) {
        StringBuilder b = new StringBuilder();
        b.append("(");
        if (n != null) {
            b.append(n.value).append(' ');
            b.append(linearize(n.left)).append(' ').append(linearize(n.right));
        }
        b.append(")");
        return b.toString();
    }

    public int getSize() {
        return getSize(root);
    }

    public boolean checkSize() {
        System.out.println(getSize() + " = " + intendedSize);
        return getSize() == intendedSize;
    }

    private int getSize(Node n) {
        if (n == null)
            return 0;
        if (n.left == null && n.right == null)
            return 1;
        if (n.left == null)
            return 1 + getSize(n.right);
        if (n.right == null)
            return 1 + getSize(n.left);
        return 1 + getSize(n.left) + getSize(n.right);
    }
    /*
    private String printTree(Node n) {
        if (n == null)
            return "null";
        String result = "[" + n.value + ",";

        if (n.left != null)
            result = result + printTree(n.left);
        if (n.right != null)
            result = result + printTree(n.right);
        return result + "]";
    }


    public String toString() {
        return printTree(root);
    }
    */
    //----
/*
	public void add(int x, int depth) {
		Node current = root;		

		if (root == null) {
			gen(0, current, x, null, null);
			root = new Node(x);
			intendedSize++;
			return;
		}

		while (current.value != x && depth > 0) {
			if (current.value > x) {
				if (current.left == null) {
					gen(1, current, x, null, null);
					current.left = new Node(x);
					intendedSize++;
				} else {
					gen(2, current, x, null, null);
					current = current.left;
				}
			} else {
				if (current.right == null) {
					gen(3, current, x, null, null);
					current.right = new Node(x);
					intendedSize++;
				} else {
					gen(4, current, x, null, null);
					current = current.right;
				}
			}
			System.out.println(depth);
			depth--;
		}
	}
*/
    public void add(int x) {
        Node current = root;

        if (root == null) {
            gen(0, current, x, null, null);
            root = new Node(x);
            intendedSize++;
            return;
        }

        while (current.value != x) {
            if (current.value > x) {
                if (current.left == null) {
                    gen(1, current, x, null, null);
                    current.left = new Node(x);
                    intendedSize++;
                } else {
                    gen(2, current, x, null, null);
                    current = current.left;
                }
            } else {
                if (current.right == null) {
                    gen(3, current, x, null, null);
                    current.right = new Node(x);
                    intendedSize++;
                } else {
                    gen(4, current, x, null, null);
                    current = current.right;
                }
            }
        }
    }
    /*
    public boolean find(int x) {
        Node current = root;

        while (current != null) {

            if (current.value == x) {
                gen(5, current, x, null, null);
                return true;
            }

            if (x < current.value) {
                gen(6, current, x, null, null);
                current = current.left;
            } else {
                gen(7, current, x, null, null);
                current = current.right;
            }
        }
        gen(16, current, x, null, null);

        return false;
    }
*/
    public boolean remove(int x) {
        Node current = root;
        Node parent = null;
        boolean branch = true; //true =left, false =right

        while (current != null) {

            if (current.value == x) {
                Node bigson = current;
                while (bigson.left != null || bigson.right != null) {
                    parent = bigson;
                    if (bigson.right != null) {
                        gen(8, current, x, bigson, parent);
                        bigson = bigson.right;
                        branch = false;
                    } else {
                        gen(9, current, x, bigson, parent);
                        bigson = bigson.left;
                        branch = true;
                    }
                }
                if (bigson == root) { // && bigson.left == null && bigson.right == null) {
                    root = null;
                    intendedSize--;
                    return true;
                }

                //		System.out.println("Remove: current "+current.value+" parent "+parent.value+" bigson "+bigson.value);
                if (parent != null) {
                    if (branch) {
                        gen(10, current, x, bigson, parent);
                        parent.left = null;
                    } else {
                        gen(11, current, x, bigson, parent);
                        parent.right = null;
                    }
                }

                if (bigson != current) {
                    gen(12, current, x, bigson, parent);
                    current.value = bigson.value;
                } else {
                    gen(13, current, x, bigson, parent);
                }


                intendedSize--;
                return true;
            }

            parent = current;
            //	    if (current.value <x ) { // THERE WAS ERROR
            if (current.value > x) {
                gen(14, current, x, null, parent);
                current = current.left;
                branch = true;
            } else {
                gen(15, current, x, null, parent);
                current = current.right;
                branch = false;
            }
        }

        gen(17, current, x, null, parent);
        return false;
    }
	/*
	public boolean remove(int x, int depth) {
		Node current = root;
		Node parent = null;
		boolean branch = true; //true =left, false =right

		int depth1 = depth;

		while (current != null && depth1 > 0) {
			depth1--;
			System.out.println("depth1 = " +depth1);
			if (current.value == x) {
				Node bigson = current;
				depth = 0;
				while ((bigson.left != null || bigson.right != null)) {
					System.out.println("depth = " + depth);
					parent = bigson;
					if (bigson.right != null) {
						gen(8, current, x, bigson, parent);
						bigson = bigson.right;
						branch = false;
					} else {
						gen(9, current, x, bigson, parent);
						bigson = bigson.left;
						branch = true;
					}
					depth++;
		    		if (depth == 2)
		    		   break;
				}

				if (bigson == root) { // && bigson.left == null && bigson.right == null) {
					root = null;
					intendedSize--;
					return true;
				}

				//		System.out.println("Remove: current "+current.value+" parent "+parent.value+" bigson "+bigson.value);
				if (parent != null) {
					if (branch) {
						gen(10, current, x, bigson, parent);
						parent.left = null;
					} else {
						gen(11, current, x, bigson, parent);
						parent.right = null;
					}
				}

				if (bigson != current) {
					gen(12, current, x, bigson, parent);
					current.value = bigson.value;
				} else {
					gen(13, current, x, bigson, parent);
				}
				intendedSize--;
				return true;
			}

			parent = current;
			//	    if (current.value <x ) { // THERE WAS ERROR
			if (current.value > x) {
				gen(14, current, x, null, parent);
				current = current.left;
				branch = true;
			} else {
				gen(15, current, x, null, parent);
				current = current.right;
				branch = false;
			}
		}

		gen(17, current, x, null, parent);
		return false;
	}
	*/
    /* How delte should really work!
     *
     */


    public boolean delete(int x)
    {
//  	Algorithm note: There are four cases to consider:
//  	1. The node is a leaf.
//  	2. The node has no left child.
//  	3. The node has no right child.
//  	4. The node has two children.

        //initialize parent and current to root
        Node current = root;
        Node parent = root;

        boolean isLeftChild = true;

        if (current == null)
            return false;

        //while loop to search for node to delete
        while(current.value != x) {
            //assign parent to current
            parent = current;
            if(current.value > x) {
                gen(68, null, x, null, null);
                isLeftChild = true; //current is a left child
                current = current.left; //make current's left child the current node
            }
            else {
                gen(69, null, x, null, null);
                isLeftChild = false; //current is a right child
                current = current.right; //make current's right child the current node
            }
            if(current == null) { //data can't be found, break from loop
                gen(70, null, x, null, null);
                return false;
            }
        }
//  	test for a leaf
        if(current.left == null && current.right == null)
        {
            if(current == root)  {//tree has a single node, make root null
                gen(71, null, x, null, null);
                root = null;
            }
            else if(isLeftChild)  { //current is a left child so make its parent's left null
                gen(72, null, x, null, null);
                parent.left = null;
            }
            else {
                gen(73, null, x, null, null);
                parent.right = null; //current is a right child so make its parent's right null
            }
        }
//  	test for no right child		
        else if(current.right == null)
            if(current == root) { //current is root so make root point to current's left
                gen(74, null, x, null, null);
                root = current.left; //old root gets deleted by garbage collector
            }
            else if(isLeftChild) { //current is a left child so make its parent's left point to it's left child
                gen(75, null, x, null, null);
                parent.left = current.left;
            }
            else { //current is a right child so make its parent's right point to it's left child
                gen(76, null, x, null, null);
                parent.right = current.left;
            }
//  	test for no left child			
        else if(current.left == null)
            if(current == root) { //current is root so make root point to current's right
                gen(77, null, x, null, null);
                root = current.right; //old root gets deleted by garbage collector
            }
            else if(isLeftChild) { //current is a left child so make its parent's left point to it's right child
                gen(78, null, x, null, null);
                parent.left = current.right;
            }
            else { //current is a right child so make its parent's right point to it's right child
                gen(79, null, x, null, null);
                parent.right = current.right;
            }
//  	there are two children:
//  	retrieve and delete the inorder successor
        else {

            Node successor = getSuccessor(current); //get successor

            if(current == root) {
                gen(80, null, x, null, null);
                root = successor;
            }
            else if(isLeftChild) {
                gen(81, null, x, null, null);
                parent.left= successor; //set node to delete to successor
            }
            else {
                gen(82, null, x, null, null);
                parent.right = successor;
            }
//		 	attach current's left to successor's left since successor has no left child		
            successor.left = current.left;
        }
        intendedSize--;
        return true;
    }

    //This method searches the successor of a node to be deleted
    private Node getSuccessor(Node delNode)
    {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.right;

        while(current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        if(successor != delNode.right) {
            successorParent.left = successor.right;
            successor.right = delNode.right;
        }
        return successor;
    }


    public static void runTest(int[] options, int limit) {
        BinTree b = new BinTree();
        int round = 0;
        while (round < limit) {
            if (options[round] == 1) {
                b.add(options[limit + round]);
            }
            else {
                //b.remove(options[limit + round]); //broken
                b.delete(options[limit + round]); //working
            }
            round++;
        }
        //if (!b.checkSize())
        //	b.covered(99);
        //else if (!b.checkTree())
        //	b.covered(999);
        //else
        //	b.covered(1000);
        //assert b.checkSize() : " size = " + b.getSize() + " but should have been " + b.intendedSize;
        //b.covered(999);
    }

    public static void runTestDriver(int length) {
        int[] values = new int[length*2];
        int i = 0;
        while (i < 2*length) {
            if (i < length)
                values[i] = Symbolic.makeSymbolicInt("c" + i);
            else
                values[i] = Symbolic.makeSymbolicInt("v" + i);
            i++;
        }
        runTest(values,length);
    }
	
	/*
	public static void runTestDriver2(int length) {
		BinTree b = new BinTree();
		Node root = new Node(1);
		b.root = (Node)Debug.makeSymbolicRef("root", root);
		if (b.root != null) {
			//b.add(Debug.makeSymbolicInteger("x"), length);
			b.remove(Debug.makeSymbolicInteger("x"), length);
		}
	}
	*/

    public static void countq(int i) {}

    public static void runExplicit3(int option1, int option2, int option3, int value1, int value2, int value3) {
        BinTree b = new BinTree();
        if (option1 == 1) {
            b.add(value1);
        }
        else {
            b.remove(value1);
        }
        if (option2 == 1) {
            b.add(value2);
        }
        else {
            b.remove(value2);
        }
        if (option3 == 1) {
            b.add(value3);
        }
        else {
            b.remove(value3);
        }
        assert b.checkSize() : " size = " + b.getSize() + " but should have been " + b.intendedSize;
        assert b.checkTree() : " tree broken ";
        countq(0);
    }

    public static String runExplicit4Broken(
            int option1, int option2, int option3, int option4,
            int value1, int value2, int value3, int value4) {
        BinTree b = new BinTree();
        if (option1 == 1) {
            b.add(value1);
        }
        else {
            b.remove(value1);
        }
        if (option2 == 1) {
            b.add(value2);
        }
        else {
            b.remove(value2);
        }
        if (option3 == 1) {
            b.add(value3);
        }
        else {
            b.remove(value3);
        }
        if (option4 == 1) {
            b.add(value4);
        }
        else {
            b.remove(value4);
        }
        return b.linearize();
        //if (!b.checkTree())
        //	countq(0);

        //assert b.checkSize() : " size = " + b.getSize() + " but should have been " + b.intendedSize;
		/*
		if (!b.checkTree()) {
			covered(99);
			countq(0);
		}
		*/
        //assert b.checkTree() : " tree broken ";

    }

    public static String runExplicit4Working(
            int option1, int option2, int option3, int option4,
            int value1, int value2, int value3, int value4) {
        BinTree b = new BinTree();
        if (option1 == 1) {
            b.add(value1);
        }
        else {
            b.delete(value1);
        }
        if (option2 == 1) {
            b.add(value2);
        }
        else {
            b.delete(value2);
        }
        if (option3 == 1) {
            b.add(value3);
        }
        else {
            b.delete(value3);
        }
        if (option4 == 1) {
            b.add(value4);
        }
        else {
            b.delete(value4);
        }
        return b.linearize();
        //if (!b.checkTree())
        //	countq(0);

        //assert b.checkSize() : " size = " + b.getSize() + " but should have been " + b.intendedSize;
		/*
		if (!b.checkTree()) {
			covered(99);
			countq(0);
		}
		*/
        //assert b.checkTree() : " tree broken ";

    }

    public static void runExplicit4(
            int option1, int option2, int option3, int option4,
            int value1, int value2, int value3, int value4) {
        String one = runExplicit4Broken(option1,option2,option3,option4,value1,value2,value3,value4);
        String two = runExplicit4Working(option1,option2,option3,option4,value1,value2,value3,value4);
        if (!one.equals(two))
            countq(0);
    }
    public static void main(String[] args) {
        runTestDriver(3);
        //runExplicit4(1,1,1,1,1,1,1,1);
		/*
		BinTree b = new BinTree();

		b.add(0);
		System.out.println(b.checkTree() + " " + b);
		b.add(-10);
		System.out.println(b.checkTree() + " " + b);
		b.add(-11);
		System.out.println(b.checkTree() + " " + b);
		b.add(-12);
		System.out.println(b.checkTree() + " " + b);
		b.add(-13);
		System.out.println(b.checkTree() + " " + b);

		System.out.println(b.delete(-11));
		System.out.println(b.checkTree() + " " + b);

		System.out.println(b.remove(-12));		
		assert b.checkSize() : " size = " + b.getSize() + " but should have been " + b.intendedSize;
		System.out.println("check = " + b.checkSize()  + " " + b);
		*/
    }

}
