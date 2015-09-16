package com.slave.autobean.mybaits3;

import java.util.HashMap;
import java.util.Map;

public class ExchangeJavaTypeWithJdbcType {
	private static Map<String, String> jdbcType = new HashMap<String, String>();

	private ExchangeJavaTypeWithJdbcType() {}
	static {
		jdbcType.put("Integer", "NUMERIC");
		jdbcType.put("String", "VARCHAR");
	}
	
	/**
	 * javaType to jdbcType
	 * 
	 * @param javaType
	 * @return
	 */
	public static String javaType2JdbcType(String javaType) {
		return jdbcType.get(javaType);
	}
	
}
