package cn.com.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Timer;
import cn.com.thread.ChangeSendMessage;
import cn.com.thread.Changelifetime;
import cn.com.thread.Changeonline;
import cn.com.thread.DelData;
import cn.com.thread.Rectask;
import cn.com.thread.ServerLisener;
import cn.com.thread.Serverprocess;
import cn.com.userlist.UserInfolist;
import cn.com.util.ConfigTable;
import com.netconnection.service.ICopyScreenService;
import com.netconnection.service.ILogService;
import com.netconnection.service.IMessageService;
import com.netconnection.service.IOnlineTimeService;
import com.netconnection.service.IPCInfoService;
import com.netconnection.service.IUserService;
import com.netconnection.service.IllegalService;
import com.netconnection.service.ReceiveMessageService;
import com.netconnection.service.SoftService;
import com.netconnection.service.TacticsPcService;
import com.netconnection.service.TacticsService;
import com.netconnection.service.TacticsSoftService;
public class Server implements Runnable{
	private static Rectask[] rectask;
	private static Changelifetime changelife;
	private static ChangeSendMessage changesend;
	private static Timer timer;
	private static IPCInfoService pcinfoService;
	private static ICopyScreenService copyScreenService;
	private static ReceiveMessageService receiveMessageService;
	private static IOnlineTimeService onlineTimeService;
//	private ISoftInstallStateService softInstallStateService;
	private static IMessageService messageService;
	private static TacticsPcService tacticsPcService;
	private static TacticsService tacticsService;
	private static SoftService softService;
	private static TacticsSoftService tacticsSoftService;
	private static ILogService logService;
	private static IUserService userService;
	private static DelData deldata;
	private static IllegalService illegalService;
	
	/*
	 * 装载服务器配置属性（配置文件存放位置 WEB-INF目录下）
	 */

	public Server(ICopyScreenService copyScreenService,IPCInfoService pcinfoService
			   ,IMessageService messageService,ReceiveMessageService receiveMessageService,
			   IOnlineTimeService onlineTimeService,TacticsPcService tacticsPcService,
			   TacticsService tacticsService,SoftService softService,
			   TacticsSoftService tacticsSoftService,ILogService logService,IUserService userService,IllegalService illegalService) {
		Server.onlineTimeService = onlineTimeService;
		Server.tacticsPcService=tacticsPcService;
		Server.tacticsSoftService=tacticsSoftService;
		Server.softService=softService;
		Server.tacticsService=tacticsService;
		Server.copyScreenService = copyScreenService; 
		Server.pcinfoService = pcinfoService;
		Server.receiveMessageService=receiveMessageService;
//	    	this.softInstallStateService = softInstallStateService;
		Server.messageService = messageService;
		Server.logService = logService;
		Server.userService = userService;
		Server.illegalService=illegalService;
	    	System.out.println("*******server,start*****");
	    	loadserver();
	    	Thread t=new Thread(this);
	    	t.start();
	    	new ServerLisener(t).start();
	    	System.out.println("*******server,end*****");
	    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	    try {
	    	Changeonline line = new Changeonline();
	    	line.setPcInfoService(pcinfoService);
			line.setTacticsService(tacticsService);
			line.setUserService(userService);
			line.setOnlineTimeService(onlineTimeService);
			line.init();
	    	ServerSocket ss=new ServerSocket(ConfigTable.port);
			while(true){
				Socket s=ss.accept();
				Serverprocess sp = new Serverprocess(s,ConfigTable.timeout);
		        sp.setPcinfoService(pcinfoService);
		        sp.setOnlineTimeService(onlineTimeService);
		        sp.setSoftService(softService);
		        sp.setTacticsPcService(tacticsPcService);
		        sp.setTacticsService(tacticsService);
		        sp.setTacticsSoftService(tacticsSoftService);
		        new Thread(sp).start();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void loadserver(){
		String path = Server.class.getResource("").getPath();
		path = path.substring(0,path.indexOf("/classes"));
		
	 	ConfigTable.init(path+"/config.xml");
	 	rectask=new Rectask[ConfigTable.recthread];
		timer=new Timer();
		changelife=new Changelifetime();
		changesend=new ChangeSendMessage();
		deldata=new DelData();
		
		deldata.setCopyScreenService(copyScreenService);
		deldata.setLogService(logService);
	    deldata.setOnlineTimeService(onlineTimeService);
	    deldata.setMessageService(messageService);
	    deldata.setIllegalService(illegalService);
		for(int i=0;i<rectask.length;i++){
			rectask[i] = new Rectask();	
		 	rectask[i].setBegin(i*ConfigTable.reccount);
		    rectask[i].setEnd((i+1)*ConfigTable.reccount);
		    rectask[i].setCopyScreenService(copyScreenService);
		    rectask[i].setPcinfoService(pcinfoService);
		    rectask[i].setMessageService(messageService);
		    rectask[i].setReceiveMessageService(receiveMessageService);  
			rectask[i].setIllegalService(illegalService);
			timer.schedule(rectask[i], 0, ConfigTable.rechz);
		}
		
		UserInfolist.setPCInfoService(pcinfoService);
		UserInfolist.setOnlineTimeService(onlineTimeService);
		timer.schedule(changelife,0, ConfigTable.lifetimehz);
		timer.schedule(changesend, 0, ConfigTable.sendmessagehz);
		Calendar c=Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.HOUR_OF_DAY,1);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		timer.schedule(deldata,c.getTimeInMillis()-System.currentTimeMillis(),1000*3600);
		
	}
	
	public static void reloadserver(){
		for(int i=0;i<rectask.length;i++){
			rectask[i].cancel();
		}
		changelife.cancel();
		changesend.cancel();
		timer.cancel();
		deldata.cancel();
		loadserver();
	}

}
