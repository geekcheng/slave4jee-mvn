package com.slave.autobean.db.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * replace database type with java type
 * 
 * @author Administrator
 *
 */
public class ExchangeWithColumnTypeAndJavaType {
	private ExchangeWithColumnTypeAndJavaType() {	}

	private static Map<String, String> mapDB2Java = new HashMap<String, String>();
	private static Map<String, String> myType = new HashMap<String, String>();

	static {
		// now only affords Integer/Boolean/String(Date)/Double
		myType.put("Integer", "Integer");
		myType.put("int", "Integer");
		myType.put("integer", "Integer");

		myType.put("Boolean", "Boolean");
		myType.put("boolean", "Boolean");
		myType.put("bool", "Boolean");

		myType.put("String", "String");
		myType.put("string", "String");
		myType.put("str", "String");

		myType.put("Date", "String");
		myType.put("date", "String");
		myType.put("datetime", "String");
		myType.put("timestamp", "String");

		myType.put("Double", "Double");
		myType.put("double", "Double");
		myType.put("Float", "Double");
		myType.put("float", "Double");
	}

	static {
		mapDB2Java.put("CLOB", "String"); // oracle type 
		mapDB2Java.put("BLOB", "String"); 
		mapDB2Java.put("TIMESTAMP", "String");
		mapDB2Java.put("DATE", "String");
		mapDB2Java.put("VARCHAR2", "String");
		mapDB2Java.put("VARCHAR", "String");
		mapDB2Java.put("NUMBER", "Integer");
		mapDB2Java.put("NUMERIC", "Integer");
		
		mapDB2Java.put("DOUBLE", "Double"); // mysql type
		mapDB2Java.put("DECIMAL", "Double"); 
		mapDB2Java.put("FLOAT", "Double"); 
		mapDB2Java.put("VARCHAR", "String");
		mapDB2Java.put("TINYINT", "Integer");
		mapDB2Java.put("BIT", "Boolean");
		mapDB2Java.put("BIGINT", "Integer");
		mapDB2Java.put("INT", "Integer");
		mapDB2Java.put("TINYTEXT", "String");
		mapDB2Java.put("LONGTEXT", "String");
		mapDB2Java.put("MEDIUMTEXT", "String");
		mapDB2Java.put("TEXT", "String");
		mapDB2Java.put("CHAR", "String");
		mapDB2Java.put("TIME", "String");
		mapDB2Java.put("DATETIME", "String");

	}

	/**
	 * transform  column in table   into java type
	 * 
	 * @param columnType
	 * @return
	 */
	public static String columnType2Java(String columnType) {
		String upper = columnType.toUpperCase();
		String str = new String(columnType);
		if(str.contains("(")){
			upper = str.substring(0, str.indexOf("(") ).toUpperCase();
		}
		
		return mapDB2Java.get(upper);
	}
	
	/**
	 * transform  myType   into java type
	 *  	myType: int/integer/string/str ect.
	 *  
	 * @param columnType
	 * @return
	 */
	public static String myType2Java(String strMyType) {
		return myType.get(strMyType.toUpperCase());
	}
}
