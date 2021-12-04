package com.deb.roy.test.string;

public class ValidateIpAddress {

	public static void main(String[] args) {
		String ip = "00.00.00.00";
		System.out.println(ValidateIpAddressSolution.isValid(ip));
	}

}

class ValidateIpAddressSolution {
    static boolean isValid(String s) {
    	// must have a dot (.) and must not ends with a dot (.)
    	if(!s.contains(".") || s.endsWith(".")) {
    		return false;
    	}
    	String ip[] = s.split("\\.");
    	// must contain 4 parts
    	if(ip.length != 4) {
    		return false;
    	}
		for (String ipPart : ip) {
	    	// must be 3 char or less
			if(ipPart.length() > 3) {
				return false;
			}
			// must not have any additional leading zeroes
			if(ipPart.length() != 1 && ipPart.startsWith("0")) {
				return false;
			}
	    	// must be integer/numeric
			int ipNum = 0;
			try {
				ipNum = Integer.parseInt(ipPart);
			} catch (NumberFormatException e) {
				return false;
			}
	    	// must be between 0 to 255
			if(ipNum < 0 || ipNum > 255) {
				return false;
			}
		}
        return true;
    }
}