package com.slave.autobean.bean;

import java.util.List;

public class ClassBean {
	private String packagename;
	private String classtype; // bean/service/controller -- > class , dao --> interface
	private String classname; 
	private String tablename; // name of table in database 
	private String comment;
	private List<FieldBean> classfields;

	
	
	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getPackagename() {
		return packagename;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public List<FieldBean> getClassfields() {
		return classfields;
	}

	public void setClassfields(List<FieldBean> classfields) {
		this.classfields = classfields;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
}
