package com.deb.roy.test.binarytree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {
	static Node root;
	
	BinaryTree() {
		root = null;
	}
	
	static int height(Node node) {
		if(node == null) {
			return 0;
		} else {
			int leftHeight = height(node.left);
			int rightHeight = height(node.right);
			if(leftHeight > rightHeight) {
				return leftHeight + 1;
			} else {
				return rightHeight + 1;
			}
		}
	}
	
	static void printLevelOrder() {
		int h = height(root);
		for(int i = 1; i <= h; i++) {
			printCurrentLevelNodes(root, i);
		}
	}
	
	static void printCurrentLevelNodes(Node node, int level) {
		if(node == null) {
			return;
		}
		if(level == 1) {
			System.out.print(node.data + " ");
		}
		else if (level > 1) {
			printCurrentLevelNodes(node.left, level - 1);
			printCurrentLevelNodes(node.right, level - 1);
		}
	}

	// Breadth-First or Level Order Traversal
	static void testBFS() {
		BinaryTree.root = new Node(10);
		BinaryTree.root.left = new Node(11);
		BinaryTree.root.right = new Node(12);
		BinaryTree.root.left.left = new Node(13);
		BinaryTree.root.left.right = new Node(14);
		BinaryTree.root.right.left = new Node(15);
		BinaryTree.root.right.right = new Node(16);
		BinaryTree.printLevelOrder();
		System.out.println();
		BinaryTree.printNodesbyBFS();
	}
	
	// Breadth-First or Level Order Traversal using LinkedList / Queue
	static void printNodesbyBFS() {
		List<Integer> list = new ArrayList<Integer>();
		Stack<Integer> stack = new Stack<>();
		Node node = root;
		if (node == null) {
			return;
		}
		Queue<Node> q = new LinkedList<>();
		q.add(node);
		while (!q.isEmpty()) {
			Node curr = q.poll();
			//System.out.print(curr.data + " ");
			list.add(curr.data);
			stack.push(curr.data);
			if (curr.left != null) {
				q.add(curr.left);
			}
			if (curr.right != null) {
				q.add(curr.right);
			}
		}

		// Forward result
		for (Integer integer : list) {
			System.out.print(integer + " ");
		}
		// Reverse result
		System.out.println();
		Collections.reverse(list);
		for (Integer integer : list) {
			System.out.print(integer + " ");
		}
		// Using stack
		System.out.println();
		while(!stack.empty()) {
			System.out.print(stack.pop() + " ");
		}
	}

	public static void main(String[] args) {
		testBFS();// Level order traversal of a tree is breadth first (BFS) traversal for the tree
	}
}

class Node {
	int data;
	Node left;
	Node right;
	
	public Node(int data) {
		this.data = data;
		left = right = null;
	}
}