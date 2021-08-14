package com.jamesr.commandaliases.utils;

public class SystemUtils {
	
	public static String[] combineArrays(String[]... arrs) {
	    
	    int length = 0;
	    
	    for (String[] arr : arrs)
	        length += arr.length;
	        
	    String[] ret = new String[length];
	    
	    int pos = 0;
	    for(String[] arr : arrs) {
	        System.arraycopy(arr, 0, ret, pos, arr.length);
	        pos += arr.length;
	    }
	    
	    return ret;
	}
}
