package com.deb.roy.test;

public class TestDoublyLinkedList {
	public static void main(String[] args) {
		DoublyLinkedList list = new DoublyLinkedList();
		list.addLast("101");
		list.addLast("102");
		list.addLast("103");
		list.addLast("104");
		list.addLast("105");
		list.addFirst("100");
		list.printAll();
		list.addLast("110");
		list.remove("1030");
		list.remove("105");
		list.addLast("112");
		list.addFirst("99");
		list.printAll();
	}
}

class DoublyLinkedList {
	Node node;

	class Node {
		String key;
		Node next;
		Node prev;
		
		Node(String key) {
			this.key = key;
			next = null;
			prev = null;
		}
	}

	public void addLast(String key) {
		Node newNode = new Node(key);
		if(node == null) {
			node = newNode;
		} else {
			Node last = this.node;
			while(last.next != null) {
				last = last.next;
			}
			newNode.prev = last;
			last.next = newNode;
		}
	}

	public void addFirst(String key) {
		Node newNode = new Node(key);
		if(node == null) {
			node = newNode;
		} else {
			Node first = this.node;
			while(first.prev != null) {
				first = first.prev;
			}
			first.prev = newNode;
			newNode.next = first;
			node = newNode;
		}
	}

	public void remove(String key) {
		Node head = this.node;
		Node prev = null;
		while(node != null) {
			if(key.equals(node.key)) {
				if(head == node) {
					System.out.println("removed from head");
					this.node = head.next;
					this.node.prev = null;
					return;
				} else {
					System.out.println("removed it");
					prev.next = node.next;
					if(node.next != null) {
						node.next.prev = node.prev;
					}
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
		System.out.println("Key" + " |                     " + "Current Node" + "                | " + "                      " + "Next Node" + "                 | " + "                     " + "Previous Node" + "                ");
		while(node != null) {
			System.out.println(node.key + " | " + node + " | " + node.next + " | " + node.prev);
			node = node.next;
		}
	}
}
