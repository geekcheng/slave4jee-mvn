package com.slave.autobean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.slave.autobean.bean.ClassBean;
import com.slave.autobean.bean.FieldBean;
import com.slave.autobean.bean.Objects;
import com.slave.autobean.db.basic.ExchangeWithColumnTypeAndJavaType;
import com.slave.autobean.db.basic.TableInfoToJavaInfo;
import com.slave.autobean.db.dbhelper.MySQLJDBC;
import com.slave.autobean.db.dbhelper.OracleJDBC;

public class MainEntry {
	private static Map<String, String> args = new HashMap<String, String>(); 
	
	private static void initArgs(Map<String, String> m ){
		args.put("prjPath", m.get("prjPath"));
		args.put("jdbcType", m.get("jdbcType"));
		args.put("packagename",m.get("packagename"));
		args.put("entityname", m.get("entityname"));
		args.put("controllername", m.get("controllername"));
		args.put("servicename", m.get("servicename"));
		args.put("daoname", m.get("daoname"));
	}

	/**
	 * 1. gather information from database,
	 * 2. generate our files
	 * 
	 * @param map
	 */
	public static void main(Map<String, String> map ) {
		initArgs(map);		
		
		// 0th step - gather information from database
		List<ClassBean> tables = findTablesFromDB();
		
		// 1st transform - change database type into java type
		Objects o = paddingJavaObjects(tables);
		
		// 2nd step - to generate files 
		System.out.println("Totally generate " + AutoGenerateClassFiles.generateClassFiles(o, args) + " files.");
		
	}
	
	private static Objects paddingJavaObjects(List<ClassBean> tables) {
		Objects o = new Objects();		
		o.setSrcRoot(args.get("prjPath"));
		
		for (ClassBean cb : tables) {
				cb.setPackagename(args.get("packagename"));
				cb.setClasstype(args.get("entityname"));
				cb.setClassname(TableInfoToJavaInfo.tableToClassNameWithCamelStyle(cb.getTablename()));
				List<FieldBean> columns = cb.getClassfields();
				for(FieldBean fb: columns){
					fb.setFieldtype(ExchangeWithColumnTypeAndJavaType.columnType2Java(fb.getColumntype()));
					fb.setFieldtext(TableInfoToJavaInfo.columnToFieldWithCamelStyle(fb.getColumntext()));
				}
		}
		
		o.setClassBeanList(tables);
				
		return o;
	}


	private static List<ClassBean> findTablesFromDB() {
		List<ClassBean> tables = new ArrayList<ClassBean>();
		String jdbc = args.get("jdbcType");
		if("ORACLE".equals(jdbc.toUpperCase())){
			tables = OracleJDBC.findAllTables();
			
			for(ClassBean t : tables){
				OracleJDBC.findTableDetail(t);
			}	
			OracleJDBC.close();
		}
		else if("MYSQL".equals(jdbc.toUpperCase())){
			tables = MySQLJDBC.findAllTables();
			
			for(ClassBean t : tables){
				MySQLJDBC.findTableDetail(t);
			}
			MySQLJDBC.close();
		}
		else{
					
		}
		
		return tables;
	}

}
