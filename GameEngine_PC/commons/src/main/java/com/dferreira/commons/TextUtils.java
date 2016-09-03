package com.dferreira.commons;

public class TextUtils {

	public static final String EMPTY_STRING = "";
	
	/**
	 * 
	 * @param string the string to indicate if is null or empty
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return (string == null) || (EMPTY_STRING).equals(string);
	}

}
