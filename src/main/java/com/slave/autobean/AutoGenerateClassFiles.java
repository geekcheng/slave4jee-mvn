package com.slave.autobean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


import com.slave.autobean.bean.ClassBean;
import com.slave.autobean.bean.ClassOrInterface;
import com.slave.autobean.bean.FieldBean;
import com.slave.autobean.bean.Objects;
import com.slave.autobean.mybaits3.ExchangeJavaTypeWithJdbcType;

public class AutoGenerateClassFiles {
	public static int generateClassFiles(Objects o, Map<String, String> args) {
		List<ClassBean> bean = o.getClassBeanList();
		int count = 0;
		for (ClassBean cb : bean) {
			count += AutoGenerateClassFiles.generateClassFile(cb, args);
		}
		return count;
	}

	public static int generateClassFile(ClassBean cb, Map<String, String> args) {
		try {
			int cnt = 0;
			String packagename = null;
			String classtype = null;
			String classname = null;
			FileWriter fileWriter = null; // for bean
			BufferedWriter bufferedWriter = null;
			FileWriter fileWriterController = null; // for controller 
			BufferedWriter bufferedWriterController = null;
			FileWriter fileWriterService = null; // for service 
			BufferedWriter bufferedWriterService = null;
			FileWriter fileWriterDao = null; // for dao 
			BufferedWriter bufferedWriterDao = null;
			FileWriter fileWriterXml = null; // for xml
			BufferedWriter bufferedWriterXml = null;
			
			File f = null;
			String prjPath = args.get("prjPath");
			prjPath.replace("\\", "/"); // replace the '\' in the path string with '/', in order to deal convinently
			if (!prjPath.endsWith("/")) {
				prjPath += "/";
			}
			if(!prjPath.endsWith("/src/")){
				prjPath += "/src/";
				f = new File(prjPath);
				if (!f.exists()) {
					f.mkdirs();
				}
			}

			packagename = cb.getPackagename();
			classtype = cb.getClasstype();
			classname = cb.getClassname();
			// System.out.println(packagename + '-' + classtype + '-' + classname);
			String packagePath = packagename.replace(".", "/");
			f = new File(prjPath + packagePath + "/");
			if (!f.exists()) {
				f.mkdirs();
			}
			
			String filePath = prjPath +  packagePath + "/" + classtype  + "/";
			f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}			
			filePath = prjPath +  packagePath + "/" + classtype  + "/" + classname
					+ upperFirstLetter(args.get("entityname")) + ".java";
			f = new File(filePath);
			if(!f.exists()){
				f.createNewFile();
			}							
			fileWriter = new FileWriter(filePath);
			bufferedWriter = new BufferedWriter(fileWriter);	
			
			filePath = prjPath +  packagePath + "/" + args.get("controllername")  + "/";
			f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}		
			filePath = prjPath +  packagePath + "/" + args.get("controllername")  + "/" + classname
					+ upperFirstLetter(args.get("controllername")) + ".java";
			f = new File(filePath);
			if(!f.exists()){
				f.createNewFile();
			}			
			fileWriterController = new FileWriter(filePath);
			bufferedWriterController = new BufferedWriter(fileWriterController);
			
			filePath = prjPath +  packagePath + "/" + args.get("servicename")  + "/";
			f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}	
			filePath = prjPath +  packagePath + "/" + args.get("servicename")  + "/" + classname 
					+ upperFirstLetter(args.get("servicename"))  + ".java";
			f = new File(filePath);
			if(!f.exists()){
				f.createNewFile();
			}			
			fileWriterService = new FileWriter(filePath);
			bufferedWriterService  = new BufferedWriter(fileWriterService);
			
			filePath = prjPath +  packagePath + "/" + args.get("daoname")  + "/";
			f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}	
			filePath = prjPath +  packagePath + "/" + args.get("daoname")  + "/" + classname 
					+ upperFirstLetter(args.get("daoname"))+ "Mapper.java";
			f = new File(filePath);
			if(!f.exists()){
				f.createNewFile();
			}			
			fileWriterDao = new FileWriter(filePath);
			bufferedWriterDao = new BufferedWriter(fileWriterDao);
			
			filePath = prjPath +  packagePath + "/" + args.get("daoname") + "/mapper/";
			f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}	
			filePath = prjPath +  packagePath + "/" + args.get("daoname") + "/mapper/" + classname 
					+ upperFirstLetter(args.get("daoname"))+ "DaoMapper.xml";
			f = new File(filePath);
			if(!f.exists()){
				f.createNewFile();
			}			
			fileWriterXml = new FileWriter(filePath);
			bufferedWriterXml = new BufferedWriter(fileWriterXml);

			List<FieldBean> fields = cb.getClassfields();
			StringBuffer sb = new StringBuffer();
			StringBuffer sbController = new StringBuffer();
			StringBuffer sbService = new StringBuffer();
			StringBuffer sbDao = new StringBuffer();
			StringBuffer sbXml = new StringBuffer();
			sb.append("package ").append(packagename + '.' + args.get("entityname")).append(";\n\n");
			sbController.append("package ").append(packagename + '.' + args.get("controllername")).append(";\n\n");
			sbService.append("package ").append(packagename + '.' + args.get("servicename")).append(";\n\n");
			sbDao.append("package ").append(packagename + '.' + args.get("daoname")).append(";\n\n");
			sb.append("import java.io.Serializable;\n\n");
			sb.append("/**\n *\n *  ").append(cb.getComment()).append("\n *\n *\n");
			sb.append(" * total " + fields.size() + " fields \n */\n");
			sb.append("public class " + classname + "Bean implements Serializable{\n");
			//@Path("/BIExport")
			//@Controller
			//public class BIExportBO {
			sbController.append("import javax.ws.rs.Path;\nimport org.springframework.stereotype.Controller;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.stereotype.Service;\n\n");
			sbController.append("@Path(\"/"+ lowerFirstLetter(classname) +"\")\n@Controller\npublic class " + classname + "Bo {\n");
			sbController.append("\t@Autowired\n\t"+classname + "Service " + lowerFirstLetter(classname) + "Service;\n\t");
			sbController.append("\n\t/**\n\t * 增加\n\t * @return\n\t */").append("\n\t@POST\n\t@Path(\"/save\")")
				.append("\n\t@Produces(\"application/json;charset=gbk\")")
				.append("\n\tpublic String save(MultivaluedMap<String, String> params, @Context HttpServletRequest request){");
				
			sbService.append("import org.springframework.beans.factory.annotation.Autowired;\nimport org.springframework.stereotype.Service;\n\n");
			sbService.append("@Service\npublic class " + classname + "Service {\n");
			sbDao.append("import org.springframework.stereotype.Component;\n\n");
			sbDao.append("@Component\npublic interface " + classname + "DaoMapper {\n");
			sbXml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
				.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ")
				.append("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n")
				.append("<mapper namespace=\"" + packagename + "." + args.get("daoname") + "." + classname + upperFirstLetter(args.get("daoname")) + "Mapper\">\n\n");
			

			StringBuffer sbSetGet = new StringBuffer();
			StringBuffer sbFields = new StringBuffer();
			sbXml.append("\t<resultMap id=\"ret"+ classname  +"\" type=\""+packagename + "." + args.get("entityname") + "." + classname+"Bean\">\n");
			sbXml.append("\t\t<id column=\"ID\" property=\"id\" jdbcType=\"NUMERIC\"/>\n");
			for (FieldBean fieldBean : fields) {				
				sb.append(toField(fieldBean));				
				sbSetGet.append(toGetSetterMethod(fieldBean));
				sbFields.append(toGerFirstParams(fieldBean));
				if(!"ID".equals(fieldBean.getColumntext())){
					sbXml.append("\t\t<!-- " + fieldBean.getComment() + "  -->\n");
					sbXml.append("\t\t<result column=\""+fieldBean.getColumntext()+"\" property=\""
						+fieldBean.getFieldtext()+"\" jdbcType=\""+ExchangeJavaTypeWithJdbcType.javaType2JdbcType(fieldBean.getFieldtype())+"\" />\n");
				}
			}
			sbXml.append("\t</resultMap\n");
			sb.append(sbSetGet.toString());
			sb.append("\n} // end of bean class\n");
			
			sbController.append(sbFields.toString())
						.append("\n\n\t\t" + classname + " " + lowerFirstLetter(classname) + "= new " + classname + "();");
			sbController.append("\n} // end of bo class\n");
			sbService.append("\n} // end of service class\n");
			sbDao.append("\n} // end of dao interface\n");
			
			sbXml.append("\n</mapper>\n");
			
			bufferedWriter.write(sb.toString());
			bufferedWriter.close();
			fileWriter.close();
			cnt++;
		//	bufferedWriterController.write(sbController.toString());
			bufferedWriterController.close();
			fileWriterController.close();
		//	cnt++;
			bufferedWriterService.write(sbService.toString());
			bufferedWriterService.close(); 
			fileWriterService.close(); 
			cnt++;
			bufferedWriterDao.write(sbDao.toString());
			bufferedWriterDao.close();
			fileWriterDao.close();
			cnt++;
			bufferedWriterXml.write(sbXml.toString());
			bufferedWriterXml.close();
			fileWriterXml.close();
			cnt++;
			return cnt;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	private static String toGerFirstParams(FieldBean fieldBean) {
		String comment = fieldBean.getComment();
		String text = fieldBean.getFieldtext();
		String type = fieldBean.getFieldtype();
		
		StringBuffer sb = new StringBuffer();
		
				
		sb.append("\n\t\tString " + text + " = params.getFirst(\""+ text +"\");");
		
		return sb.toString();
	}

	/**
	 *  to generate basic field
	 * 
	 * @param fieldBean
	 * @return
	 */
	private static StringBuffer toField(FieldBean fieldBean){
		String comment = fieldBean.getComment();
		String text = fieldBean.getFieldtext();
		String type = fieldBean.getFieldtype();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("\t// " + comment + "\n");
		sb.append("\tprivate " + type + " " + text + ";\n");
		
		return sb;
	}
	
	/**
	 * to generate set/get method 
	 * 
	 * @param fieldBean
	 * @return
	 */
	private static StringBuffer toGetSetterMethod(FieldBean fieldBean){
		String comment = fieldBean.getComment();
		String text = fieldBean.getFieldtext();
		String type = fieldBean.getFieldtype();
		
		StringBuffer sbSetGet = new StringBuffer();
		
		sbSetGet .append("\n\t/**\n\t * 获取" + comment 	+ "\n\t */\n");
		sbSetGet.append("\tpublic " + type + toGetSetterMethodName(text, 1));
		sbSetGet.append("(){\n\t\treturn this." + text 	+ ";\n\t}\n");
		
		sbSetGet.append("\n\t/**\n\t * 设置" + comment 	+ "\n\t */\n");
		sbSetGet.append("\tpublic void" + toGetSetterMethodName(text, 2));
		sbSetGet.append("(" + type + " "	+ text + "){\n\t\t this." + text + " = " + text	+ ";\n\t}\n");
		
		return sbSetGet;
	}
	
	/**
	 * 
	 * 
	 * @param fieldName field name
	 * @param flag 1 to generate getter method, other to generate setter method
	 * @return
	 */
	private static String toGetSetterMethodName(String fieldName, int flag){
		return (" " + (flag == 1 ? "g" : "s") + "et" + upperFirstLetter(fieldName));
	}
	
	private static String upperFirstLetter(String text){
		StringBuffer sb =  new StringBuffer(text.substring(0, 1).toUpperCase()
				+ text.substring(1));
		return sb.toString();
	}
	
	private static String lowerFirstLetter(String text){
		StringBuffer sb =  new StringBuffer(text.substring(0, 1).toLowerCase()
				+ text.substring(1));
		return sb.toString();
	}

}
