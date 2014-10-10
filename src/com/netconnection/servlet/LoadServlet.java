package com.netconnection.servlet;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns="/loadservlet")
public class LoadServlet extends HttpServlet {
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        		throws ServletException, IOException {
	   String path = LoadServlet.class.getResource("").getPath();
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
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      String filename="database.sql";
      resp.setContentType("application/x-download");  
      
      resp.addHeader("Content-Disposition","attachment;filename=" + java.net.URLEncoder.encode(filename,"UTF-8"));
      
      System.out.println(req.getContextPath()+"/download/1.sql");
	  req.getRequestDispatcher("download/1.sql").forward(req, resp);
      }
}
