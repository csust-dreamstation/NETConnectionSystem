package cn.com.thread;
import java.util.TimerTask;
import cn.com.userlist.SendMessagelist;
public class ChangeSendMessage extends TimerTask {
	public void run() {
		try{
			SendMessagelist.changesend();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
