package com.deb.roy.test.stack;

import java.util.Stack;

public class ParenthesisChecker {
	public static void main(String[] args) {
		String input = "{[()()]}";//"{}{(}))}"
		//System.out.println("isMirrorImage? - " + ParenthesisCheckerSolution.isMirrorImage(input));
		System.out.println("isPar? - " + ParenthesisCheckerSolution.ispar(input));
	}
}

class ParenthesisCheckerSolution {
    // Function to check if brackets are balanced or not.
    static boolean ispar(String x) {
    	if(x == null || x.length() == 0) {
    		return true;
    	}
    	if(x.length() % 2 != 0) {
    		return false;
    	}
    	char[] chars = x.toCharArray();
    	Stack<Character> stack = new Stack<>();
    	for(char c : chars) {
    		// if it is open bracket add to stack
    		// if it is close bracket remove the open bracket from stack
    		if(c=='(' || c=='{' || c=='[') {
    			stack.push(c);
    		} else {
    			if(c==')' && !stack.empty() && stack.peek() == '(') stack.pop();
    			else if(c=='}' && !stack.empty() && stack.peek() == '{') stack.pop();
    			else if(c==']' && !stack.empty() && stack.peek() == '[') stack.pop();
    			else return false;
    		}
    	}
    	if (stack.empty()) return true;
    	return false;
    }

    // Function to check if brackets are mirror image or not.
    static boolean isMirrorImage(String x) {
    	if(x == null || x.length() == 0) {
    		return true;
    	}
    	int len = x.length();
    	if(len % 2 != 0) {
    		return false;
    	}
    	int mid = x.length() / 2;
    	char[] charFirst = x.substring(0, mid).toCharArray();
    	char[] charLast = x.substring(mid).toCharArray();
    	Stack<Character> stack = new Stack<>();
    	for(char c : charFirst) {
    		stack.push(c);
    	}
    	for(char c : charLast) {
    		char s = stack.pop();
    		if((c=='(' && s!=')') || (c=='{' && s!='}') || (c=='[' && s!=']')) {
    			return false;
    		}
    	}
    	return true;
    }
}
