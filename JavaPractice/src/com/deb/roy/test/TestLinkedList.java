package com.deb.roy.test;

public class TestLinkedList {
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.add("101");
		list.add("102");
		list.add("103");
		list.add("104");
		list.add("105");
		list.printAll();
		list.remove("101");
		list.printAll();
	}
}

class LinkedList {
	Node node;

	class Node {
		String key;
		Node next;
		
		Node(String key) {
			this.key = key;
			next = null;
		}
	}

	public void add(String key) {
		Node newNode = new Node(key);
		if(node == null) {
			node = newNode;
		} else {
			Node last = this.node;
			while(last.next != null) {
				last = last.next;
			}
			last.next = newNode;
		}
	}
	
	public void remove(String key) {
		Node head = this.node;
		Node prev = null;
		while(node != null) {
			if(key.equals(node.key)) {
				if(head == node) {
					System.out.println("found it at head");
					this.node = head.next;
					return;
				} else {
					System.out.println("found it");
					prev.next = node.next;
					this.node = head;
					return;
				}
			}
			prev = node;
			node = node.next;
		}
		System.out.println("not found");
		this.node = head;
	}
	
	public void printAll() {
		Node node = this.node;
		while(node != null) {
			System.out.println(node.key);
			node = node.next;
		}
	}
}
