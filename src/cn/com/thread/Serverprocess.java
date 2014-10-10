package cn.com.thread;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.jboss.weld.logging.MessageId;

import cn.com.userlist.UserInfolist;
import cn.com.util.ConfigTable;
import cn.com.util.ServerUtil;
import cn.com.vo.Message;
import cn.com.vo.UserInfo;

import com.netconnection.entity.Onlinetime;
import com.netconnection.entity.PatchList;
import com.netconnection.entity.Pcinfo;
import com.netconnection.entity.SoftList;
import com.netconnection.entity.TacticsList;
import com.netconnection.service.IOnlineTimeService;
import com.netconnection.service.IPCInfoService;
import com.netconnection.service.SoftService;
import com.netconnection.service.TacticsPcService;
import com.netconnection.service.TacticsService;
import com.netconnection.service.TacticsSoftService;

public class Serverprocess implements Runnable {
	private Socket s;
	private int time;
	private int statu;
	private int tacticsid;
	private TacticsPcService tacticsPcService;
	private TacticsService tacticsService;
	private String together;
	private SoftService softService;
	private TacticsSoftService tacticsSoftService;
	private int timeout;
	private IPCInfoService pcinfoService;
	private IOnlineTimeService onlineTimeService;

	public Serverprocess(Socket s, int timeout) {
		this.timeout = timeout;
		this.s = s;
	}

	public void run() {
	String mac="";
	try{
		s.setSoTimeout(timeout);
		DataInputStream in = ServerUtil.getin(s);
		DataOutputStream out = ServerUtil.getout(s);
		ArrayList<String> meslist = ServerUtil.getmessage(in);
		if(meslist.size()>0){
		System.out.println(meslist.get(0));
		if (!meslist.get(0).equals("")) {
			String[] str = meslist.get(0).split("\\|");
			if (str[0].equals("HB")) {
				    mac=str[2];
					UserInfo user = new UserInfo();	
					user.setIn(in);
					user.setOut(out);
					user.setLoadcount(0);
					user.setUpcount(0);
					user.setState(true);
					user.setLifetime((byte)0);
					user.setFlowin(0);
					user.setFlowout(0);
					user.setUsername(str[3]);
					user.setMac(str[2]);
					user.setIP(str[1]);
					UserInfolist.adduser(user);
					
					user.setOs(str[8]);
					String memory ="";
					String allMemory="";
					String liyonglv="";
					if(str[7]!=null&&!str[7].equals("")){
						memory= str[7].split("/")[0];
						allMemory=str[7].split("/")[1].split("=")[0];
						liyonglv=str[7].split("/")[1].split("=")[1];
					}
					String cpu="";
				    if(!str[6].equals("")){
				    	cpu="";
				    }
					Pcinfo info = pcinfoService.findByMac(user.getMac());
					if (info == null) {
						info = new Pcinfo(str[1], str[2], str[3], str[8], 0l,
								0l, pcinfoService.ONLINESTATE, new Timestamp(System.currentTimeMillis()),
								"unchecked",cpu,memory,allMemory,liyonglv);
						
						info.setWarnstate(pcinfoService.NO_WARNSTATE);
						info.setPctype(Integer.parseInt(str[9]));
						info.setWifichk(Integer.parseInt(str[10]));
						pcinfoService.savePCInfo(info);
					} else {
						info.setClientname(str[3]);
						info.setIp(str[1]);
						info.setOs(str[8]);
						info.setOnlinestate(pcinfoService.ONLINESTATE);
						info.setRecordtime(new Timestamp(System
								.currentTimeMillis()));
						info.setUpflow((long) user.getFlowout());
						info.setLoadflow((long) user.getFlowin());
						info.setWarnstate(pcinfoService.NO_WARNSTATE);
						info.setStatu("unchecked");
						info.setCpu(cpu);
						info.setMemory(memory);
						info.setAllmemory(allMemory);
						info.setAvailablity(liyonglv);
						info.setPctype(Integer.parseInt(str[9]));
						info.setWifichk(Integer.parseInt(str[10]));
						pcinfoService.updatePCInfo(info);      
					}
					
					// 记录上线的时间
					Date now = new Date();
					Onlinetime onlinetime = new Onlinetime(str[1],
							user.getMac(),
							new Timestamp(now.getTime()).toString());
					onlinetime.setClientname(user.getUsername());
					onlineTimeService.saveOnlineTime(onlinetime);
					
                    Message mes=new Message();
					mes.setMac(str[2]);
					
					mes.setType(Message.ILLACC);
					ServerUtil.send(mes);
					
					mes.setType(Message.ILLAC);
					mes.setMessage(ConfigTable.dnstime+"|"+ConfigTable.dnsadress+"|"+ConfigTable.dnsport);
					ServerUtil.send(mes);
				
					mes.setType(Message.TIMESYN);
					ServerUtil.send(mes);
				
					mes.setType(Message.TYPE_WIFICHK);
					mes.setMessage(ConfigTable.type_wifichk+"");
					ServerUtil.send(mes);
					
					
					// 获得策略id和策略对象；
					this.tacticsid=(Integer) tacticsService.findIdByName("默认策略").get(0);
					System.out.println("这是默认策略发送");
					if(tacticsPcService.findTacticsid(
							str[2]).size()!=0){
					this.tacticsid = (Integer) tacticsPcService.findTacticsid(
							str[2]).get(0);
					}
					TacticsList tacticsList = new TacticsList();
					tacticsList = tacticsService.findByTacticsid(tacticsid);
					// 发送黑名单；
					this.statu = 0;this.together = "";this.time = tacticsList.getBlacktime();
					if (tacticsSoftService.findListId(tacticsid, statu).size() != 0) {
						SoftList softlist = new SoftList();
						java.util.List<Integer> blacklist = tacticsSoftService
								.findListId(tacticsid, statu);
						for (int i = 0; i < blacklist.size(); i++) {
							System.out.println("这里是打印对应的黑名单id");
							System.out.println(blacklist.get(i));
							softlist = softService.findSoft(blacklist.get(i));
							
							together = together + "|"
									+ softlist.getThreadname() + "*"
									+ softlist.getSoftname();
						}
						Message message = new Message();
						message.setMac(str[2]);
						message.setMessage(time + together);
						message.setType(3);
						ServerUtil.send(message);
					}
					// 发送白名单；
					this.statu = 1;this.together = "";this.time = tacticsList.getWhitetime();
					if (tacticsSoftService.findListId(tacticsid, statu).size() != 0) {
						SoftList softlist = new SoftList();
						java.util.List<Integer> whitelist = tacticsSoftService
								.findListId(tacticsid, statu);
						for (int i = 0; i < whitelist.size(); i++) {
							System.out.println(whitelist.get(i));
							System.out.println("这里是打印对应的白名单id");
							softlist = softService.findSoft(whitelist.get(i));
							together = together + "|"
									+ softlist.getThreadname() + "*"
									+ softlist.getSoftname();
						}
						Message message = new Message();
						message.setMac(str[2]);
						message.setMessage(time + together);
						message.setType(1);
						ServerUtil.send(message);
					}
					// 发送补丁名单
					this.together = "";this.time = tacticsList.getPatchtime();this.statu = 2;
					if (tacticsSoftService.findListId(tacticsid, statu).size() != 0) {
						java.util.List<Integer> patchlist01 = tacticsSoftService
								.findListId(tacticsid, statu);
						for (int i = 0; i < patchlist01.size(); i++) {
							PatchList patchlist = softService
									.findPatchList(patchlist01.get(i));
							together = together + "|"
									+ patchlist.getPatchname();
						}
						Message message = new Message();
						message.setMac(str[2]);
						message.setMessage(time + together);
						message.setType(5);
						ServerUtil.send(message);
					}
			}
		}
		}
		}catch(Exception e){
			UserInfolist.deluser(mac);
			if(s!=null){
				try {
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}

	/*
	 * 客户端注册线程 str[0]:消息类型 str[1]:客户端IP地址 str[2]:客户端MAC地址 str[3]:用户名
	 * str[4]:网卡出流量 str[5]:网卡入流量 str[6]:OS版本
	 * 
	 * #START#HB|客户端IP地址|客户端MAC地址|用户名|网卡出流量|网卡入流量|CPU使用率|内存使用率|OS版本#END#
	 */
	public void setPcinfoService(IPCInfoService pcinfoService) {
		this.pcinfoService = pcinfoService;
	}

	public void setOnlineTimeService(IOnlineTimeService onlineTimeService) {
		this.onlineTimeService = onlineTimeService;
	}

	public void setTacticsPcService(TacticsPcService tacticsPcService) {
		this.tacticsPcService = tacticsPcService;
	}

	public void setTacticsService(TacticsService tacticsService) {
		this.tacticsService = tacticsService;
	}

	public void setSoftService(SoftService softService) {
		this.softService = softService;
	}

	public void setTacticsSoftService(TacticsSoftService tacticsSoftService) {
		this.tacticsSoftService = tacticsSoftService;
	}
	
}
