package com.slave.autobean.bean;

import java.util.List;

/**
 * all the files in project
 * 
 * @author Administrator
 *
 */
public class Objects {
	private String srcRoot; // java source path 
	private List<ClassBean> classBeanList; // all the java files & xml files 

	public String getSrcRoot() {
		return srcRoot;
	}

	public void setSrcRoot(String srcRoot) {
		this.srcRoot = srcRoot;
	}

	public List<ClassBean> getClassBeanList() {
		return classBeanList;
	}

	public void setClassBeanList(List<ClassBean> classBeanList) {
		this.classBeanList = classBeanList;
	}

}
