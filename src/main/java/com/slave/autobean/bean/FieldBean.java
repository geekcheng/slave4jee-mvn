package com.slave.autobean.bean;

/**
 * the defination of field in a java bean
 * 
 * @author Administrator
 *
 */
public class FieldBean {
	private String fieldtype; // field type
	private String columntype; // column type
	private String fieldtext; // field name
	private String columntext; // column text
	private String comment; //  comment
	
	

	public String getColumntype() {
		return columntype;
	}

	public void setColumntype(String columntype) {
		this.columntype = columntype;
	}

	public String getColumntext() {
		return columntext;
	}

	public void setColumntext(String columntext) {
		this.columntext = columntext;
	}

	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	public String getFieldtext() {
		return fieldtext;
	}

	public void setFieldtext(String fieldtext) {
		this.fieldtext = fieldtext;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
}
