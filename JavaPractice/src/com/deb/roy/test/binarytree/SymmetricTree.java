package com.deb.roy.test.binarytree;

public class SymmetricTree {

	public static void main(String[] args) {
		BinaryTree.root = new Node(10);
		BinaryTree.root.left = new Node(20);
		BinaryTree.root.right = new Node(20);
		BinaryTree.root.left.left = new Node(25);
		BinaryTree.root.left.right = new Node(25);
		BinaryTree.root.right.left = new Node(25);
		BinaryTree.root.right.right = new Node(25);

		System.out.println(SymmetricTreeSolution.isSymmetric(BinaryTree.root));
	}

}

class SymmetricTreeSolution
{
    // return true/false denoting whether the tree is Symmetric or not
    public static boolean isSymmetric(Node root) {
    	if (root == null) {
    		return true;
    	} else {
    		return areEqual(root.left, root.right);
    	}
    }
    
    public static boolean areEqual(Node left, Node right) {
    	if(left == null && right == null) {
    		return true;
    	} else if (left == null || right == null) {
    		return false;
    	} else if (left.data == right.data) {
    		return areEqual(left.left, right.right) && areEqual(left.right, right.left);
    	} else {
    		return false;
    	}
    }
}
