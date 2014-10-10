package cn.com.userlist;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import cn.com.util.ServerUtil;
import cn.com.vo.UserInfo;

import com.netconnection.entity.Pcinfo;
import com.netconnection.service.IOnlineTimeService;
import com.netconnection.service.IPCInfoService;
import com.netconnection.service.impl.OnlineTimeServiceImpl;
public class UserInfolist {
	 private static ArrayList<UserInfo> userlist=new ArrayList<UserInfo>();
	 private static byte MAX_LIFETIME=4;
	 private static byte MAX_OLDTIME=12;
	 private static IPCInfoService pcInfoService;
	 private static IOnlineTimeService onlineTimeService;
	 
	 public static void setPCInfoService(IPCInfoService pcinfoService){
		 UserInfolist.pcInfoService = pcinfoService;
	 }
	 
	 public static void setOnlineTimeService(IOnlineTimeService onlineTimeService) {
		UserInfolist.onlineTimeService = onlineTimeService;
	}




	private UserInfolist(){
		 
	 }	 
	 public static void adduser(UserInfo user){
		 synchronized (userlist) {
			 boolean flag=true;
			 for(int i=0;i<userlist.size();i++){
				 if(user.getMac().equals(userlist.get(i).getMac())){
					    userlist.set(i, user);
					    flag=false;
				 }
			 }
			 if(flag){
				 userlist.add(user);
			 }
		}
	 }//添加用户
	 public static void deluser(String mac){
		 synchronized (userlist) {			
			 for(int i=0;i<userlist.size();i++){
				 if(mac.equals(userlist.get(i).getMac())){
					 deluser(i);
				 }
			 }
		}
	 }//删除用户
	 public static void changelifetime(){
		 for(int i=0;i<userlist.size();i++){
			 try{
				 UserInfo user=userlist.get(i);
				 byte lifetime=user.getLifetime();
				 if(lifetime>MAX_OLDTIME){
					 deluser(i);
//					 Pcinfo pcinfo = pcInfoService.findByMac(user.getMac());
//					 if(pcinfo!=null){
//					 pcinfo.setOnlinestate(pcInfoService.DEL_ONLINESTATE);
//					 pcinfo.setLoadflow(0l);
//					 pcinfo.setUpflow(0l);
//					 pcInfoService.updatePCInfo(pcinfo);
//					 }
				 }
				 else if(lifetime>=MAX_LIFETIME){
					 user.setState(false);				 
					 Pcinfo pcinfo = pcInfoService.findByMac(user.getMac());
					 if(pcinfo!=null){
					 pcinfo.setOnlinestate(pcInfoService.NO_ONLINESTATE);
					 pcinfo.setLoadflow(0l);
					 pcinfo.setUpflow(0l);
					 pcInfoService.updatePCInfo(pcinfo);
					 
					 //下线时间
					 Date now = new Date();
					 onlineTimeService.saveEndTime(user.getMac(), new Timestamp(now.getTime()).toString());
					 }
					 lifetime+=1;
					 user.setLifetime(lifetime);
				 }
				 else{
					 user.setState(true);
					 lifetime+=1;
					 user.setLifetime(lifetime);
				 }
				 }catch(Exception e){
					 e.printStackTrace();
				 }
			}
	 }//检查生存时间
	 public static void resetlifetime(String mac){
		 for(int i=0;i<userlist.size();i++){
			 if(userlist.get(i).getMac().equals(mac)){
				 userlist.get(i).setLifetime((byte)0);
			 }
		 }
	 }//重置生存时间ֵ
	public static ArrayList<UserInfo> getuserlist() {
		return userlist;
	}
	public static UserInfo getuser(String mac){
		UserInfo user=null;
	   
		for(int i=0;i<userlist.size();i++){
			if(userlist.get(i).getMac().equals(mac)){
			 user=userlist.get(i);
			}
		}
		return user;
	}//根据mac查找用户
	private static void deluser(int i){
        ServerUtil.closein(userlist.get(i).getIn());
        ServerUtil.closeout(userlist.get(i).getOut());
		userlist.remove(i);
	}
}
