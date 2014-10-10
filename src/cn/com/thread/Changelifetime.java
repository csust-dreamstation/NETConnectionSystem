package cn.com.thread;
import java.util.TimerTask;
import cn.com.userlist.UserInfolist;
public class Changelifetime extends TimerTask {
	public void run() {
      try{
    	     System.out.println("我在执行检查");
			 UserInfolist.changelifetime();
			 for(int i=0;i<UserInfolist.getuserlist().size();i++){
				 System.out.println(UserInfolist.getuserlist().get(i).getUsername()+"的生存时间："+UserInfolist.getuserlist().get(i).getLifetime());
			 }
            }catch(Exception e){
				e.printStackTrace();
          }
	}

}
