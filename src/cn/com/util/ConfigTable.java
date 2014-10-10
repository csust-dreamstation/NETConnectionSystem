package cn.com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
public class ConfigTable {
	public static int port;
	public static int timeout=50;
	public static int recthread=50;
	public static int reccount=20;
	public static int rechz=1000;
	public static int sendmessagehz=10000;
	public static int lifetimehz=15000;
	public static int upWarning;
	public static int loadWarning;
	public static int warncount;
	public static String warningContent;
	public static int hour_of_day=0;
	public static int day_of_week=1;
	public static int day_of_month=1;
	public static int month_of_year=0;
	public static int deltype=4;//设置定时删除数据库的类型
	public static String databasepwd="";
	public static int dnstime=10;
	public static String dnsadress="222.240.129.80";
	public static int dnsport=53;
	public static int dely_time_del=30;
	public static int type_wifichk=0;
    private ConfigTable(){
	   
    }
    public static Properties init(String path){
		File file=new File(path);
		File dir=new File(path.substring(0,path.lastIndexOf("/")));
		if(!dir.exists()){
			dir.mkdirs();
		}
		if(!file.exists()){
			FileOutputStream out=null;
			try {
				file.createNewFile();
				Properties config=new Properties();
				config.setProperty("hour_of_day", "0");
				config.setProperty("type_wifichk","0");
				config.setProperty("dely_time_del", "30");
				config.setProperty("day_of_week", "1");
				config.setProperty("day_of_month", "1");
				config.setProperty("month_of_year", "0");
				config.setProperty("deltype","4");
				config.setProperty("databasepwd","");
				config.setProperty("port","22222");
				config.setProperty("timeout","50");
				config.setProperty("recthread","50");
				config.setProperty("reccount", "20");
				config.setProperty("rechz", "1000");
				config.setProperty("sendmessagehz", "10000");
				config.setProperty("lifetimehz", "15000");
				config.setProperty("upWarning", "30");
				config.setProperty("loadWarning", "300");
				config.setProperty("warncount","5");
				config.setProperty("warningContent", "流量超过警告值,请对其进行处理!");
				config.setProperty("dnsadress","222.240.129.80");
				config.setProperty("dnsport","53");
				config.setProperty("dnstime", "10");
				out=new FileOutputStream(file);
				config.storeToXML(out,null);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(out!=null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		Properties config=new Properties();
		try {
			FileInputStream in=new FileInputStream(file);
			config.loadFromXML(in);
			in.close();				
			port=Integer.parseInt(config.getProperty("port"));
			timeout=Integer.parseInt(config.getProperty("timeout"));
			recthread=Integer.parseInt(config.getProperty("recthread"));
			reccount=Integer.parseInt(config.getProperty("reccount"));
			rechz=Integer.parseInt(config.getProperty("rechz"));
			sendmessagehz=Integer.parseInt(config.getProperty("sendmessagehz"));
			lifetimehz=Integer.parseInt(config.getProperty("lifetimehz"));
			upWarning=Integer.parseInt(config.getProperty("upWarning"));
			loadWarning=Integer.parseInt(config.getProperty("loadWarning"));
			warningContent = config.getProperty("warningContent");
			warningContent = config.getProperty("warningContent");
			warncount =Integer.parseInt(config.getProperty("warncount"));
			hour_of_day=Integer.parseInt(config.getProperty("hour_of_day"));
			day_of_week=Integer.parseInt(config.getProperty("day_of_week"));
			day_of_month=Integer.parseInt(config.getProperty("day_of_month"));
			month_of_year=Integer.parseInt(config.getProperty("month_of_year"));
			deltype=Integer.parseInt(config.getProperty("deltype"));
			databasepwd=config.getProperty("databasepwd");
			
			dnstime=Integer.parseInt(config.getProperty("dnstime"));
			dnsport=Integer.parseInt(config.getProperty("dnsport"));
			dnsadress =config.getProperty("dnsadress");
			
			dely_time_del=Integer.parseInt(config.getProperty("dely_time_del"));
			
			type_wifichk=Integer.parseInt(config.getProperty("type_wifichk"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
   }
   public static void changeconfig(String key,String value,String path){
		 Properties config=init(path);
		 config.setProperty(key, value);
		 FileOutputStream out=null;
		try {
			out = new FileOutputStream(path);
			config.storeToXML(out,null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ConfigTable.init(path);
   }
   public static void changeconfig(String key,int value,String path){
		 Properties config=init(path);
		 config.setProperty(key, value+"");
		 FileOutputStream out=null;
		try {
			out = new FileOutputStream(path);
			config.storeToXML(out,null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ConfigTable.init(path);
   }
   public static void changeconfig(String key,int value){
		String path = ConfigTable.class.getResource("").getPath();
		path = path.substring(0,path.indexOf("/classes"))+"/config.xml";
		 Properties config=init(path);
		 config.setProperty(key, value+"");
		 FileOutputStream out=null;
		try {
			out = new FileOutputStream(path);
			config.storeToXML(out,null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		ConfigTable.init(path);
   }
}
