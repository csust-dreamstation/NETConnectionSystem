package com.netconnection.action;
import java.io.File;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.apache.struts2.util.ServletContextAware;
import cn.com.server.Server;
import cn.com.util.ConfigTable;
import com.opensymphony.xwork2.ActionSupport;
public class LoadAction extends ActionSupport implements ServletContextAware{
    private   ServletContext context;

    @Override
    public void setServletContext(ServletContext context)
    {
        this.context = context;
    }

    public InputStream getSimpleDownloadStream() throws Exception
    {
		String pathc = Server.class.getResource("").getPath();
		pathc = pathc.substring(0,pathc.indexOf("/classes"));
		
	 	ConfigTable.init(pathc+"/config.xml");
    	if(System.getProperties().getProperty("os.name").split(" ")[0].equalsIgnoreCase("Windows")){
		String path = LoadAction.class.getResource("").getPath();
		path = path.substring(1,path.indexOf("/WEB-INF"));
		String filepath="/download/1.sql";
		File file=new File(path+"/download");
		if(!file.exists()){
			file.mkdirs();
		}
		Runtime rt=Runtime.getRuntime();
		String command="";
		if(ConfigTable.databasepwd.equals("")){
		command="cmd.exe /c mysqldump -h localhost -u root netconnection >"+path+filepath;
		}else{
			command="cmd.exe /c mysqldump -h localhost -u root "+"-p" +ConfigTable.databasepwd.equals("")+" netconnection >"+path+filepath;
		}
		System.out.println(command);
		Process pro=rt.exec(command);
		pro.waitFor();
    	}
    	else{
    		   String path = LoadAction.class.getResource("").getPath();
    		   path = path.substring(0,path.indexOf("/WEB-INF"));
    			File file=new File(path+"/download");
    			if(!file.exists()){
    				file.mkdirs();
    			}
    		   Runtime rt=Runtime.getRuntime();
    		   String mysql="";
    		   if(ConfigTable.databasepwd.equals("")){
    		   mysql="mysqldump -h localhost -uroot netconnection > ";
    		   }else{
    			   mysql="mysqldump -h localhost -uroot "+"-p"+ConfigTable.databasepwd+" netconnection > ";
    		   }
    		   String filepath=path+"/download/1.sql";

    	      String cmd []= {"sh","-c",mysql+filepath};  
    	      Process pro=rt.exec(cmd);
    	   try {
    			pro.waitFor();
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
        return context.getResourceAsStream("/download/1.sql");
        
    }

    @Override
    public String execute()
    {
        return SUCCESS;
    }

}
