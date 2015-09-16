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


public class OracleJDBC {
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@10.137.46.5:1521:tkorasol";
	private static final String user = "webmanage";
	private static final String password = "ins185";
	
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
		List<ClassBean> list = new ArrayList<ClassBean>();
			String sql = "select * from user_tab_comments";
			
			pst = con.prepareStatement(sql);			
			rst = pst.executeQuery();
			while(rst.next()){
				ClassBean t = new ClassBean();
				t.setTablename(rst.getString("table_name"));
				t.setComment(rst.getString("comments"));
				list.add(t);				
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *  find the column name, type, and comment of a table
	 * 
	 * @param t
	 */
	public static void findTableDetail(ClassBean t) {
		try {
		List<FieldBean> list = new ArrayList<FieldBean>();
		String sql = "";
			 sql = " select ucc.column_name as f_name, ucc.comments as f_comment, utc.data_type as f_type from user_col_comments ucc " 
					+ " left join user_tab_columns utc on ucc.column_name = utc.column_name " 
					+ " where ucc.table_name = '"+t.getTablename()+"' and utc.table_name = '" + t.getTablename() +"'";
	//		System.out.println(sql);
			pst = con.prepareStatement(sql);
			
			rst = pst.executeQuery();
			while(rst.next()){
				FieldBean te = new FieldBean();
				te.setColumntext(rst.getString("f_name"));
				te.setColumntype(rst.getString("f_type"));
				te.setComment(rst.getString("f_comment"));
				
				list.add(te);
			}
			t.setClassfields(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void close() {
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
	
}
