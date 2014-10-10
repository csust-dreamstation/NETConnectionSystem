package cn.com.server;
import java.util.ArrayList;

import cn.com.userlist.SendMessagelist;
import cn.com.util.ConfigTable;
import cn.com.util.ServerUtil;
import cn.com.vo.Message;
import cn.com.vo.SendMessage;
public class Services {
     public boolean send(Message message){
    	 return ServerUtil.send(message);
     }
     /*
      * 单发消息实现
      */
     public void sends(ArrayList<Message> meslist){
    	 for(int i=0;i<meslist.size();i++){
    		 ServerUtil.send(meslist.get(i));
    	 }
     }
     /*
      * 群发消息实现
      */
     public void timesend(Message message,int time){
    	 SendMessage sendmessage=new SendMessage(message, time,-1, -1,0);
    	 SendMessagelist.addsendmessage(sendmessage);
     }
     /*
      * 以time的时间间隔发送消息(单位：10s)
      * 若新发送的message中字段的mac 和 type 与前面已执行发送操作但未取消发送时，则执行替换操作，即新发送的message会
      * 替换前面的message
      */
     public void timesend(Message message,int time,int timeout,int outtime){
    	 SendMessage sendmessage=new SendMessage(message, time, timeout, outtime,0);
    	 SendMessagelist.addsendmessage(sendmessage);
     }
     /*
      * 在未超过timeout时间内以time时间间隔发送该message
      * 在超过timeout时间时以outtime时间间隔发送消息(单位：10s)
      * 若新发送的message中字段的mac 和 type 与前面已执行发送操作但未取消发送时，则执行替换操作，及新发送的message会
      * 替换前面的message
      */
     public boolean delsend(Message message){
    	 SendMessage sendmessage=new SendMessage();
    	 sendmessage.setMessage(message);
    	 return SendMessagelist.delsendmessage(sendmessage);
     }
     /*
      * 此方法的message形参只需填充其中的mac属性和type属性
      * 作用:取消对应mac用户的时序消息发送任务
      * 
      */
     public void reloadserver(){
    	 Server.reloadserver();
     }
     /*
      * 重新加载服务器
      */
     public void changecofig(String key,int value,String path){
    	 ConfigTable.changeconfig(key, value, path);
     }
     /*
      * 改变服务器配置，key 键 ，value 值， path 配置文件存储路径
      * 
      */
     public void changecofig(String key,String value,String path){
    	 ConfigTable.changeconfig(key, value, path);
     }
}
