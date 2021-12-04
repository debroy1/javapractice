package com.deb.roy.test.string;

import java.util.ArrayList;
import java.util.Collections;

public class ReverseString {

	public static void main(String[] args) {
		String input = "i.like";
		System.out.println(input);
		String result = ReverseStringSolution.reverseWords(input);
		System.out.println(result);
	}

}

class ReverseStringSolution {
    //Function to reverse words in a given string.
    static String reverseWords(String S) {
    	String sarr[] = S.split("\\.");
		ArrayList<String> list = new ArrayList<>();
		for (String s : sarr) {
		    list.add(s);
		}
		Collections.reverse(list);
		StringBuffer buff = new StringBuffer();
		for (String string : list) {
			buff = buff.append(string).append(".");
		}
    	return buff.substring(0, buff.length()-1).toString();
    }
}