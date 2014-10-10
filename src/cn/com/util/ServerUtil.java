package cn.com.util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import cn.com.server.Server;
import cn.com.server.Services;
import cn.com.userlist.UserInfolist;
import cn.com.vo.Message;
import cn.com.vo.UserInfo;
public class ServerUtil {
private ServerUtil(){
}
public static DataInputStream getin(Socket s){
	DataInputStream in=null;
    try{
		in = new DataInputStream(s.getInputStream());
	} catch (IOException e) {
		e.printStackTrace();
	}
	return in;
}
/*
 * 获得客户端输入流
 * 
 */
public static DataOutputStream getout(Socket s){
	DataOutputStream out=null;
	try{
		out = new DataOutputStream(s.getOutputStream());
	} catch (IOException e) {
		e.printStackTrace();
	}
	return out;
}
/*
 * 获得客户端输出流
 * 
 */
public static void closein(DataInputStream in){
	if(in!=null){
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
/*
 * 关闭客户端输入流
 */
public static void closeout(DataOutputStream out){
	if(out!=null){
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
/*
 * 关闭客户端输出流
 * 
 */
public static ArrayList<String> getmessage(DataInputStream in){
	String message="";
	String str="";
	ArrayList<String> meslist=new ArrayList<String>();
	if(in!=null){
	synchronized (in) {	
		byte cbuf[]=new byte[5];
		byte startbuf[]=new byte[7];
		while(getstart(in,startbuf)){
			ArrayList<Byte> mesbuf=new ArrayList<Byte>();
			String type=getnextwords(in);
			if(type.equals("COPYSCREENANSWER")){
				String status=getnextwords(in);
				if(status.equals("0")){
					String timestamp=getnextwords(in);
					int size=Integer.parseInt(getnextwords(in));
					int totalsize=Integer.parseInt(getnextwords(in));
					int sn=Integer.parseInt(getnextwords(in));
					int islast=Integer.parseInt(getnextwords(in));
					byte b[]=new byte[256*1024];
					System.out.println("数据包序号："+sn+"本次传输数据大小："+size);
					//判断文件夹是否存在若没有则创建
					
					String path = Server.class.getResource("").getPath();
					path = path.substring(0,path.indexOf("/WEB-INF"));
					String filepath=path+"/copyscreen/"+timestamp+".bmp";
					File dir=new File(path+"/copyscreen");
					if(!dir.exists()){
						dir.mkdirs();
					}
					if(sn==0){
						File file=new File(filepath);
						FileOutputStream out=null;
						try {
							if(!file.exists()){
							file.createNewFile();
							}
							out=new FileOutputStream(file);
							while(size>0){
								if(size<1024*256){
									b=new byte[size];
									int len=in.read(b);
									out.write(b,0,len);
									size-=len;
								}
								else{
								int len=in.read(b);
								out.write(b,0,len);
								size-=len;
								}
							}
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
					}
					else{
						File file=new File(filepath);
						FileOutputStream out=null;
						try {
							if(!file.exists()){
								file.createNewFile();
							}
							out=new FileOutputStream(file,true);
							while(size>0&&islast!=1){
								if(size<1024*256){
									b=new byte[size];
									int len=in.read(b);
									out.write(b,0,len);
									size-=len;
								}
								else{
								int len=in.read(b);
								out.write(b,0,len);
								size-=len;
								}
							}
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
					}
					if(islast==1){
						meslist.add("IMG|"+timestamp);
					}
					try {
						in.skip(5);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					try {
						in.skip(4);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else{
				try {
					in.read(cbuf);
					str=new String(cbuf);
					while(true){
						if(str.equals("#END#")){
							break;
						}
						else{
							mesbuf.add(cbuf[0]);
							if(getnextchar(cbuf, in)){
								str=new String(cbuf);
							}
							else{
								str="#END#";
							}
						}
					}
					message=type+"|"+trans(mesbuf);
					meslist.add(message);
					message="";
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		}
	}
	return meslist;
}

/*
 * 读取客户端积存的消息
 * 
 */
public static boolean send(Message message){
	boolean flag=false;
	try{
			System.out.println("message2.mac:"+message.getMac());
            UserInfo user=UserInfolist.getuser(message.getMac());
            if(user!=null){
			synchronized (user) {
				DataOutputStream out=user.getOut();
				if(out!=null){
				String mes="";
				switch(message.getType()){
				case Message.BLC:
					mes="#START#BLC|"+message.getMessage()+"#END#";
					break;
				case Message.BLCC:
					mes="#START#BLCC#END#";
					break;
				case Message.LOGOUT:
					mes="#START#LOGOUT|"+message.getMessage()+"#END#";
					break;
				case Message.OPC:
					mes="#START#OPC|"+message.getMessage()+"#END#";
					break;
				case Message.WLC:
					mes="#START#WLC|"+message.getMessage()+"#END#";
					break;
				case Message.WLCC:
					//?
					message.setType(Message.LOGOUT);
					new Services().delsend(message);
					mes="#START#WLCC#END#";
					break;
				case Message.MSG2:
					mes="#START#MSG2|"+message.getMessage()+"#END#";
					break;
				case Message.SHUTDOWN:
					mes="#START#SHUTDOWN#END#";
					break;
				case Message.COPYSCREEN:
					mes="#START#COPYSCREEN#END#";
					break;
				case Message.ILLAC:
					mes="#START#ILLAC|"+message.getMessage()+"#END#";
					break;
				case Message.ILLACC:
					mes="#START#ILLACC#END#";
				    break;
				case Message.TIMESYN:
					SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date=dateformat.format(System.currentTimeMillis());
					mes="#START#TIMESYN|"+date+"#END#";
					break;
				case Message.TYPE_WIFICHK:
					mes="#START#TYPE&WIFICHK|"+message.getMessage()+"#END#";
					break;
				default:
					mes="#START#MSG2|"+message.getMessage()+"#END#";
					break;
				}
			      try {
						out.write(mes.getBytes("GBK"));
						flag=true;
						System.out.println("mes******sended");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						flag=false;
						e.printStackTrace();
					}
		}
	    }
        }
	}catch (Exception e) {
		e.printStackTrace();
		   flag=false;
	}
           return flag;
	}
/*
 * 给客户端发送消息
 * 
 */
private static boolean getstart(DataInputStream in,byte []cbuf){
	boolean flag=false;
	try {
		in.read(cbuf);
		while(true){
		if(new String(cbuf).equals("#START#")){
			 flag=true;
			 break;
		}
		else if(!getnextchar(cbuf, in)){
			flag=false;
			break;
		}
		}
	} catch (IOException e) {
		flag=false;
	}
	return flag;
}
private static boolean getnextchar(byte cbuf[],DataInputStream in){
	boolean flag=false;
	for(int i=0;i<cbuf.length-1;i++){
		cbuf[i]=cbuf[i+1];
	}
	try{
		cbuf[cbuf.length-1]=(byte)in.read();
		flag=true;
	} catch (IOException e) {
		flag=false;
	}
	return flag;
}
private static String getnextwords(DataInputStream in){
	ArrayList<Byte> typebuf=new ArrayList<Byte>();
	try {
		byte b=(byte)in.read();
		while(true){
			if(b=='|'||b=='#'){
				break;
			}
			typebuf.add(b);
			b=(byte)in.read();
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	return trans(typebuf);
}
private static String trans(ArrayList<Byte> buf){
	 String str="";
	 if(buf.size()>0){
	 byte ba[]=new byte[buf.size()];
	 for(int i=0;i<buf.size();i++){
		 ba[i]=buf.get(i);
	 }
	 try {
		str=new String(ba,"GBK");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	return str;
}
}
