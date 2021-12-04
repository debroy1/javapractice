package com.deb.roy.test;

import java.util.HashMap;
import java.util.Map;

public class TestLRUCache {
	public static void main(String[] args) {
		LRUCache cache = new LRUCache();
		cache.put("101", "1010");
		cache.put("102", "1020");
		cache.put("103", "1030");
		cache.put("104", "1040");
		cache.put("105", "1050");
		//cache.remove("105");
		cache.printNodes();
		cache.printCache();
		cache.get("101");
		cache.get("102");
		cache.put("106", "1060");
		cache.put("107", "1070");
		cache.printNodes();
		cache.printCache();
	}
}

class LRUCache {
	Node node;
	Node head;
	Node tail;
	final int capacity = 5;
	Map<String, Node> map = new HashMap<>();

	class Node {
		String key;
		String value;
		Node next;
		Node prev;
		
		Node(String key, String value) {
			this.key = key;
			this.value = value;
			next = null;
			prev = null;
		}
	}
	
	public void get(String key) {
		if(map.containsKey(key)) {
			Node node = map.get(key);
			String value = node.value;
			remove(key);
			put(key, value);
			System.out.println("Cache hit: found key value as " + value);
		} else {
			System.err.println("Cache miss: key not found");
		}
	}

	public void put(String key, String value) {
		if(map.size() >= capacity) {
			if(map.containsKey(key)) {
				remove(key);
			} else {
				remove(this.tail.key);
			}
		}
		Node newNode = new Node(key, value);
		if(node == null) {
			node = newNode;
			map.put(key, newNode);
			this.head = newNode;
			this.tail = newNode;
		} else {
			Node first = this.node;
			while(first.prev != null) {
				first = first.prev;
			}
			first.prev = newNode;
			newNode.next = first;
			node = newNode;
			map.put(key, newNode);
			this.head = newNode;
		}
	}

	public void remove(String key) {
		Node head = this.node;
		Node prev = null;
		map.remove(key);
		while(node != null) {
			if(key.equals(node.key)) {
				if(head == node) {
					System.out.println("removed from head");
					this.node = head.next;
					this.node.prev = null;
					this.head = head.next;
					return;
				} else {
					System.out.println("removed it");
					prev.next = node.next;
					if(node.next != null) {
						node.next.prev = node.prev;
					} else {
						this.tail = node.prev;
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
	
	public void printCache() {
		System.out.println("------------------- Printing Cache ---------------------");
		System.out.println("head: " + head + " || Node: [" + head.key + " | " + head.value + "]");
		System.out.println("tail: " + tail + " || Node: [" + tail.key + " | " + tail.value + "]");
		map.forEach((key, node) -> System.out.println("Key: " + key + " || Node: [" + node.key + " | " + node.value + "]"));
		System.out.println("--------------------------------------------------------");
	}
	
	public void printNodes() {
		System.out.println("------------------- Printing Nodes ---------------------");
		Node node = this.node;
		System.out.println("Key" + " |                     " + "Current Node" + "                | " + "                      " + "Next Node" + "                 | " + "                     " + "Previous Node" + "                ");
		while(node != null) {
			System.out.println(node.key + " | " + node + " | " + node.next + " | " + node.prev);
			node = node.next;
		}
		System.out.println("--------------------------------------------------------");
	}
}
