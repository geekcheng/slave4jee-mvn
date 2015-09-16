package com.slave.autobean.bean;

import java.util.HashMap;
import java.util.Map;

public class ClassOrInterface {
	private ClassOrInterface(){}
	private static Map<String, String> myType = new HashMap<String, String>();
	
	static {
		myType.put("bean", "class");
		myType.put("service", "class");
		myType.put("controller", "class");
		myType.put("dao", "interface");
	}
	
	public static String getClassTypeByBeanType(String strMyType) {
		return myType.get(strMyType);
	}

}
