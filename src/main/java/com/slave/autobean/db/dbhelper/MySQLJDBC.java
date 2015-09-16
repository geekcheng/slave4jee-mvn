package com.slave.autobean.db.dbhelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.slave.autobean.bean.ClassBean;
import com.slave.autobean.bean.FieldBean;


public class MySQLJDBC {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/dtzq";
	private static final String user = "root";
	private static final String password = "root";
	
	private static Connection con = null;
	private static PreparedStatement pst = null;
	private static ResultSet rst = null;

	static {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  find the name, and comment of a table
	 * 
	 * @return
	 */
	public static List<ClassBean> findAllTables(){
		try {
		List<ClassBean> list = null;
		
		String sql = "SHOW TABLE STATUS";
		String name = null;
		String comment = null;
		ClassBean t = null;
		
			pst = con.prepareStatement(sql);
			rst = pst.executeQuery();
			while(rst.next()){
				if(list == null){
					list = new ArrayList<ClassBean>();
				}
				t = new ClassBean();
				name = rst.getString("name");
				comment = rst.getString("comment");
				 
				t.setTablename(name);
				t.setComment(comment);
			
				list.add(t);
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void close(){
		try {
			if(rst != null){
				rst.close();
			}
			if(pst != null){
				pst.close();
			}
			if(con != null && !con.isClosed()){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *  find the column name, type, and comment of a table
	 * 
	 * @param table
	 */
	public static void findTableDetail(ClassBean table) {
		try {
		String sql = "SHOW FULL COLUMNS FROM " + table.getTablename();
		String name = null;
		String type = null;
		String comment = null;
		List<FieldBean> columnBeanList = null;
		
			pst = con.prepareStatement(sql);
			rst = pst.executeQuery();
			while(rst.next()){
				if(columnBeanList == null){
					columnBeanList = new ArrayList<FieldBean>();
				}
				
				name = rst.getString("field");
				type = rst.getString("type");
				comment = rst.getString("comment");
				
				FieldBean cb = new FieldBean();
				cb.setColumntext(name);
				cb.setColumntype(type);
				cb.setComment(comment);
				columnBeanList.add(cb);
			}
			
			table.setClassfields(columnBeanList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
