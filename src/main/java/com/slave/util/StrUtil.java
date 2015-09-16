package com.slave.util;

public class StrUtil {
	public static String trimLeft(String str, char s){
		StringBuffer sb2 = new StringBuffer(str);
		
		while(sb2.charAt(0) == s){
			sb2.deleteCharAt(0);
		}
		
		return sb2.toString();
	}
	
	public static boolean isBlank(String string){
		if(string == null){
			return true;
		}
		if("".equals(string.trim())){
			return true;
		}
		if("\n".equals(string.trim())){
			return true;
		}
		if("\t".equals(string.trim())){
			return true;
		}
		
		return false;
	}

}

