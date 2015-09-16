package com.slave.autobean.db.basic;

public class TableInfoToJavaInfo {
	private TableInfoToJavaInfo(){}
	
	/**
	 * transform the database table name into java class camel style.
	 * 
	 * @param column
	 * @return
	 */
	public static String tableToClassNameWithCamelStyle(String column){
		if(column.startsWith("_")){ // return self if starts with '_'
			return column;
		}
		boolean contains = column.contains("_");
		if(!contains){
			return upperFirst(column.toLowerCase()); // return lowerCase if no '_'
		}
		
		String lowerCase = column.toLowerCase(); // remove '_' & upperCase the next character
		char[] charArray = lowerCase.toCharArray();
		char[] charArray2 = new char[charArray.length];
		boolean flag = false;
		int j = 0;
		for(int i = 0; i < charArray.length; i++){
			flag = false;
			if(charArray[i] == '_'){
				i++;
				flag = true;
			}
			
			if(flag){
				charArray2[j] = (char) (charArray[i] - 'a' + 'A');
			}
			else{
				charArray2[j] = charArray[i];
			}
			j++;
		}
		String a_s = new String(charArray2).trim();
		return upperFirst(a_s);
	}
	/**
	 * transform the database column into java camel style
	 * 
	 * @param column
	 * @return
	 */
	public static String columnToFieldWithCamelStyle(String column){
		if(column.startsWith("_")){ // return self if starts with '_'
			return column;
		}
		boolean contains = column.contains("_");
		if(!contains){
			return column.toLowerCase(); // return lowerCase if no '_'
		}
		
		String lowerCase = column.toLowerCase(); // remove '-' & upperCase the next character
		char[] charArray = lowerCase.toCharArray();
		char[] charArray2 = new char[charArray.length];
		boolean flag = false;
		int j = 0;
		for(int i = 0; i < charArray.length; i++){
			flag = false;
			if(charArray[i] == '_'){
				i++;
				flag = true;
			}
			
			if(flag){
				charArray2[j] = (char) (charArray[i] - 'a' + 'A');
			}
			else{
				charArray2[j] = charArray[i];
			}
			j++;
			
		}
		return new String(charArray2).trim();
	}
	
	private static String upperFirst(String text){
		//System.out.print("bef:" + text);
		StringBuffer sb = new StringBuffer(text);
		
		String first = sb.substring(0, 1);
		String other = sb.substring(1);
		String string = first.toUpperCase() + other;
		//System.out.println(", aft:" + string);
		
		return string;
	}
	
}
