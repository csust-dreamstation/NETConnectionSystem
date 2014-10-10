package cn.com.thread;
import java.sql.Timestamp;
import java.util.List;
import cn.com.userlist.UserInfolist;
import cn.com.vo.UserInfo;
import com.netconnection.entity.Onlinetime;
import com.netconnection.entity.Pcinfo;
import com.netconnection.entity.TacticsList;
import com.netconnection.entity.User;
import com.netconnection.service.IOnlineTimeService;
import com.netconnection.service.IPCInfoService;
import com.netconnection.service.IUserService;
import com.netconnection.service.TacticsService;

public class Changeonline{
	private IPCInfoService pcInfoService;
	private IUserService userService;
	private IOnlineTimeService onlineTimeService;
	private TacticsService tacticsService;
	public void init() {
			
			User user = userService.findUserByName("admin");
			if(user == null){
				user = new User();
				user.setUsername("admin");
				user.setPassword("admin");
				userService.saveUser(user);
			}		
			List<Pcinfo> pcList =  pcInfoService.findOnline();
			if(tacticsService.findIdByName("默认策略").size()==0){
			TacticsList tacticsList=new TacticsList();
			tacticsList.setTacticsname("默认策略");	
			tacticsList.setBlacktime(20);
			tacticsList.setPatchtime(120);
			tacticsList.setWhitetime(20);
			tacticsService.addtacticsList(tacticsList);}
			
			
			if(pcList!=null){
				for(Pcinfo pcinfo:pcList){
				try{
					UserInfo userinfo=new UserInfo();
					userinfo.setFlowin(0);
					userinfo.setFlowout(0);
					userinfo.setIn(null);
					userinfo.setLifetime((byte)0);
					userinfo.setIP(pcinfo.getIp());
					userinfo.setMac(pcinfo.getMac());
					userinfo.setState(true);
					userinfo.setUsername(pcinfo.getClientname());
					userinfo.setOs(pcinfo.getOs());
					userinfo.setOut(null);
					userinfo.setUpcount(0);
					userinfo.setLoadcount(0);
					
					
					onlineTimeService.saveEndTime(userinfo.getMac(), new Timestamp(System.currentTimeMillis()).toString());
					
					Thread.sleep(500);
					Onlinetime onlinetime = new Onlinetime(userinfo.getIP(),
							userinfo.getMac(),
							new Timestamp(System.currentTimeMillis()).toString());
							
					onlinetime.setClientname(userinfo.getUsername());					
					onlineTimeService.saveOnlineTime(onlinetime);
					UserInfolist.adduser(userinfo);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
	}
	public void setOnlineTimeService(IOnlineTimeService onlineTimeService) {
		this.onlineTimeService = onlineTimeService;
	}
	public void setPcInfoService(IPCInfoService pcInfoService) {
		this.pcInfoService = pcInfoService;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public void setTacticsService(TacticsService tacticsService) {
		this.tacticsService = tacticsService;
	}
}
