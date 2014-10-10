package com.netconnection.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.netconnection.servlet.LoadServlet;
import com.opensymphony.xwork2.ActionSupport;

public class DataBaseAction extends ActionSupport {
	public String backupDataBase() throws IOException{
		String path = DataBaseAction.class.getResource("").getPath();
		   path = path.substring(0,path.indexOf("/WEB-INF"));
		   System.out.println(path);
		   File file=new File(path+"/download");
		   if(!file.exists()){
			   file.mkdirs();
		   }
		   Runtime rt=Runtime.getRuntime();
		   String mysql="mysqldump -h localhost -uroot netconnection > ";
		   String filepath=path+"/download/1.sql";

	      String cmd []= {"sh","-c",mysql+filepath};  
	      Process pro=rt.exec(cmd);
	   try {
			pro.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	      HttpServletResponse resp = ServletActionContext.getResponse();
	      String filename="database.sql";
	      resp.setContentType("application/x-download");  
	      
	      resp.addHeader("Content-Disposition","attachment;filename=" + java.net.URLEncoder.encode(filename,"UTF-8"));
	      
	      //System.out.println(req.getContextPath()+"/download/1.sql");
		  //req.getRequestDispatcher("download/1.sql").forward(req, resp);
		return INPUT;
	}
}
