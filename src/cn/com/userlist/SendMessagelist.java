package cn.com.userlist;
import java.util.ArrayList;

import cn.com.util.ServerUtil;
import cn.com.vo.SendMessage;
public class SendMessagelist {
 private static ArrayList<SendMessage> meslist=new ArrayList<SendMessage>();
 private SendMessagelist(){
	 
 }
 public static void addsendmessage(SendMessage sendmessage){
	 synchronized (meslist) {
		 int flag=isexist(sendmessage);
		 if(flag==-1){
			 meslist.add(sendmessage);
		 }
		 else{
			 if(meslist.get(flag).getCount()>=0){
				 meslist.set(flag, sendmessage);
			 }
		 }
		 
	}
 }
 /*
  * 添加时序消息任务
  * 
  */
 public static int isexist(SendMessage sendmessage){
	 int flag=-1;
	 String mac=sendmessage.getMessage().getMac();
	 int type=sendmessage.getMessage().getType();
	 for(int i=0;i<meslist.size();i++){
		 if(mac.endsWith(meslist.get(i).getMessage().getMac())&&type==meslist.get(i).getMessage().getType()){
			 flag=i;
		 }
	 }
	 return flag;
 }
 /*
  * 检测是否已存在相同时序任务，若存在，返回其在队列中的位置，否则返回-1
  * 
  */
 public static boolean delsendmessage(SendMessage sendmessage){
	 boolean flag=false;
	 synchronized (meslist) {
		int i=isexist(sendmessage);
		if(i!=-1){
			meslist.get(i).setCount(-20);
			flag=true;
		}
	 }
	 return flag;
 }
 /*
  * 删除时序消息任务，若成功返回true；
  * 
  */
 public static void changesend(){
	 synchronized (meslist) {
		for(int i=0;i<meslist.size();i++){
			SendMessage sendmessage=meslist.get(i);
			int count=sendmessage.getCount();
			int time=sendmessage.getTime();
			int timeout=sendmessage.getTimeout();
			int outtime=sendmessage.getOuttime();
			if(count==-1){
				meslist.remove(i);
			}
			else if(outtime>0&&timeout>0){
				if(count>timeout){
					 if(count%outtime==0){
						 ServerUtil.send(sendmessage.getMessage());
						 count+=1;
						 sendmessage.setCount(count);
					 }
				}
				else{
					if(count%time==0&&count>=0){
						ServerUtil.send(sendmessage.getMessage());
						count+=1;
						sendmessage.setCount(count);
					}
					else{
						count+=1;
						sendmessage.setCount(count);
					}
				}
			}
			else if(outtime==-1&&timeout==-1){
				if(count%time==0&&count>=0){
					ServerUtil.send(sendmessage.getMessage());
					count+=1;
					sendmessage.setCount(count);
				}
				else{
					count+=1;
					sendmessage.setCount(count);
				}
			}
		}
	}
 }
 /*
  * 时序任务逻辑处理
  * 
  */
}
