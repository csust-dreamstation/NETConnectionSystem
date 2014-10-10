package cn.com.thread;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;
import cn.com.userlist.UserInfolist;
import cn.com.util.ConfigTable;
import cn.com.util.ServerUtil;
import cn.com.vo.UserInfo;
import com.netconnection.entity.CopyScreen;
import com.netconnection.entity.Illegal;
import com.netconnection.entity.Message;
import com.netconnection.entity.Pcinfo;
import com.netconnection.service.ICopyScreenService;
import com.netconnection.service.IMessageService;
import com.netconnection.service.IPCInfoService;
import com.netconnection.service.ISoftInstallStateService;
import com.netconnection.service.IllegalService;
import com.netconnection.service.ReceiveMessageService;
import com.netconnection.service.impl.PCInfoServiceImpl;
public class Rectask extends TimerTask {
	private int begin;
	private int end;
	private ICopyScreenService copyScreenService; 
	private ISoftInstallStateService softInstallStateService;
	private IPCInfoService pcinfoService;
	private IMessageService messageService;
	private ReceiveMessageService receiveMessageService;
	private IllegalService illegalService;
	public Rectask(){}
	public Rectask(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}
	public void run() {
	try{
		int flag=0;
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(end>UserInfolist.getuserlist().size()){
			flag=UserInfolist.getuserlist().size();
		}
		else{
			flag=end;
		}
		for(int i=begin;i<flag;i++){
			try{
			   UserInfo user=UserInfolist.getuserlist().get(i);
			   if(user.getState()){
			   System.out.println("正在处理用户:"+user.getUsername()+"的消息");
				ArrayList<String> meslist=ServerUtil.getmessage(user.getIn());
				for(int j=0;j<meslist.size();j++){
					String message=meslist.get(j);
					System.out.println(message);
					String str[]=message.split("\\|");
					if(str.length>1){
					if(str[0].equals("HB")){
						String memory ="";
						String allMemory="";
						String liyonglv="";
						
						UserInfolist.resetlifetime(str[2]);
						if(!str[7].equals("")){
							memory= str[7].split("/")[0];
							allMemory=str[7].split("/")[1].split("=")[0];
							liyonglv=str[7].split("/")[1].split("=")[1];
						}
						String cpu="";
					    if(!str[6].equals("")){
					    	if(cpu.length()>200){
					    		cpu="";
					    	}
					    	else{
					    		cpu=str[6];
					    	}
					    }
						Pcinfo info = pcinfoService.findByMac(user.getMac());
							if(user.getLoadcount()>=ConfigTable.warncount&&user.getUpcount()>=ConfigTable.warncount){
								   info.setWarnstate(IPCInfoService.BOTH_WARNSTATE);
								   
								   cn.com.vo.Message mes=new cn.com.vo.Message();
								   mes.setMac(user.getMac());
								   mes.setType(cn.com.vo.Message.MSG2);
								   mes.setMessage("上传和下载"+ConfigTable.warningContent);
								   ServerUtil.send(mes);
								  
							}
							else if(user.getLoadcount()>=ConfigTable.warncount){
								   info.setWarnstate(IPCInfoService.LOAD_WARNSTATE);
								   cn.com.vo.Message mes=new cn.com.vo.Message();
								   mes.setMac(user.getMac());
								   mes.setType(cn.com.vo.Message.MSG2);
								   mes.setMessage("下载"+ConfigTable.warningContent);
								   ServerUtil.send(mes);
								   
								   
							}
							else if(user.getUpcount()>=ConfigTable.warncount){
								   info.setWarnstate(IPCInfoService.UP_WARNSTATE);
								   cn.com.vo.Message mes=new cn.com.vo.Message();
								   mes.setMac(user.getMac());
								   mes.setType(cn.com.vo.Message.MSG2);
								   mes.setMessage("上传"+ConfigTable.warningContent);
								   ServerUtil.send(mes);
								 
								   
							}
							else{
								info.setWarnstate(IPCInfoService.NO_WARNSTATE);
							}
						
							int flowin=Integer.parseInt(str[5])/1024/8;
							int flowout=Integer.parseInt(str[4])/1024/8;
							
							
							if(flowin>=ConfigTable.loadWarning){
								user.setLoadcount(user.getLoadcount()+1);
							}
							else{
								user.setLoadcount(0);
							}
							if(flowout>ConfigTable.upWarning){
								user.setUpcount(user.getUpcount()+1);
							}
							else{
								user.setUpcount(0);
							}									
							user.setFlowin(Integer.parseInt(str[5])/1024/8);
							user.setFlowout(Integer.parseInt(str[4])/1024/8);
							info.setClientname(str[3]);
							info.setIp(str[1]);
							info.setOs(str[8]);
							
							//计算传输的流量数据
							info.setUpflow((long)(user.getFlowout()));
							info.setLoadflow((long)(user.getFlowin()));
							info.setOnlinestate(PCInfoServiceImpl.ONLINESTATE);
							info.setRecordtime(new Timestamp(System.currentTimeMillis()));
							info.setStatu("unchecked");
							info.setCpu(cpu);
							info.setMemory(memory);
							info.setAllmemory(allMemory);
							info.setAvailablity(liyonglv);
							info.setPctype(Integer.parseInt(str[9]));
							info.setWifichk(Integer.parseInt(str[10]));
							pcinfoService.updatePCInfo(info);
							
							
							if(info.getWarnstate()==IPCInfoService.LOAD_WARNSTATE||info.getWarnstate()==IPCInfoService.UP_WARNSTATE||info.getWarnstate()==IPCInfoService.BOTH_WARNSTATE){
							        Illegal illegal=illegalService.findByMac(info.getMac());
							        System.out.println();
							        if(illegal!=null){
							        	illegal.setFlow(1);
							        	illegal.setFtime(sd.format(new Date()));
							        	illegal.setRecordtime(new Timestamp(System.currentTimeMillis()));
							        	illegalService.updateIllegal(illegal);
							        }else{
							        illegal=new Illegal();
							        illegal.setMac(info.getMac());
							        illegal.setIp(info.getIp());
							        illegal.setOs(info.getOs());
							        illegal.setFlow(1);
							        illegal.setRecordtime(new Timestamp(System.currentTimeMillis()));
							        illegal.setClientname(info.getClientname());
							        illegal.setFtime(sd.format(new Date()));
							        illegalService.saveIllegal(illegal);
							        }
							}
						if(!user.getIP().equals(str[1])){
						}	
					}//接收到心跳包的逻辑处理
					/*
					 * 心跳包信息：
					 * str[0]:消息类型
					 * str[1]:客户端IP地址
					 * str[2]:客户端MAC地址
					 * str[3]:用户名
					 * str[4]:网卡出流量
					 * str[5]:网卡入流量
					 * str[6]:OS版本
					 */
					else if(str[0].equals("WLCR")){
						for(int n=1;n<str.length;n++){
							String strr[]=str[n].split("\\*");
							if(strr.length>1){
							if(strr[1].equals("1")){
								receiveMessageService.saveSoftInstallState(str[0],user.getMac(), strr[0] , strr[1] );
	
							}//检测到该白名单进程未运行时执行的逻辑处理
							else if(strr[1].equals("0")){
								receiveMessageService.saveSoftInstallState(str[0],user.getMac(), strr[0] , strr[1] );
							}//检测到该白名单以运行时执行的逻辑处理
						}
						}
					}
					/*
					 * 白名单处理结果逻辑部分
					 * 示例：#START#WLCR|RavMonD.exe*RESULT|vrvsafec.exe*RESULT#END#
					 * for循环对所有白名单要求检测的进程即以"|"分隔的字段进行遍历
					 * 对于每个进程的检测结果如下：
					 * strr[0] 表示进程名如：RavMonD.exe strr[1] 表示进程监测结果 0表示正在运行，1表示未运行
					 */
					else if(str[0].equals("OPCR")){
						for(int n=1;n<str.length;n++){
							
							String strr[]=str[n].split("\\*");
							if(strr.length>1){
							if(strr[1].equals("0")){
								System.out.println(user.getMac()+strr[0]+strr[1]);
							receiveMessageService.savePatchInstallState(user.getMac(), strr[0], strr[1]);
								
							}
							else if(strr[1].equals("1")){
								receiveMessageService.savePatchInstallState(user.getMac(), strr[0], strr[1]);
							}
							}
						}
					}
					
					
					else if(str[0].equals("BLCR")){
						for(int n=1;n<str.length;n++){
							String strr[]=str[n].split("\\*");
							if(strr.length>1){
							if(strr[1].equals("0")){	
								receiveMessageService.saveSoftInstallState(str[0],user.getMac(), strr[0] , strr[1] );							
							}
							else if(strr[1].equals("1")){
								receiveMessageService.saveSoftInstallState(str[0],user.getMac(), strr[0] , strr[1] );
							}
							}
						}
					}
					
					else if(str[0].equals("MSG1")){
						Message msg = new Message();
						msg.setMac(user.getMac());
						msg.setType(IMessageService.MESSAGE_TO_SERVER);
						msg.setContent(str[1]);
						msg.setRecordtime(new Timestamp(System.currentTimeMillis()));
						msg.setReadstate(IMessageService.NO_READ);
						msg.setClientname(user.getUsername());
						messageService.saveMessage(msg);		
					}
					
					else if(str[0].equals("IMG")){
						CopyScreen cs = new CopyScreen();
						cs.setMac(user.getMac());
						cs.setPath(str[1]);
						cs.setState(0);
						cs.setRecordtime(new Timestamp(System.currentTimeMillis()));
						System.out.println(copyScreenService);
						boolean bool = copyScreenService.saveCopyScreen(cs);
						System.out.println("bool:"+bool);	
					}
					else if(str[0].equalsIgnoreCase("ILLACR")){
						
						Pcinfo info = pcinfoService.findByMac(user.getMac());
						if(info!=null){
					        Illegal illegal=illegalService.findByMac(info.getMac());
					        if(illegal!=null){
					        	illegal.setIllegal(1);
					        	illegal.setItime(str[1]);
					        	illegal.setRecordtime(new Timestamp(System.currentTimeMillis()));
					        	illegalService.updateIllegal(illegal);
					        }else{
					        illegal=new Illegal();
					        illegal.setMac(info.getMac());
					        illegal.setIp(info.getIp());
					        illegal.setOs(info.getOs());
				        	illegal.setIllegal(1);
				        	illegal.setItime(str[1]);
					        illegal.setRecordtime(new Timestamp(System.currentTimeMillis()));
					        illegal.setClientname(info.getClientname());
					        illegalService.saveIllegal(illegal);
					        }
						}				
					}
				}
			}
            }
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setSoftInstallStateService(
			ISoftInstallStateService softInstallStateService) {
		this.softInstallStateService = softInstallStateService;
	}
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	public void setCopyScreenService(ICopyScreenService copyScreenService) {
		this.copyScreenService = copyScreenService;
	}
	public void setPcinfoService(IPCInfoService pcinfoService) {
		this.pcinfoService = pcinfoService;
	}
	public void setReceiveMessageService(ReceiveMessageService receiveMessageService) {
		this.receiveMessageService = receiveMessageService;
	}
	
	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public IllegalService getIllegalService() {
		return illegalService;
	}
	public void setIllegalService(IllegalService illegalService) {
		this.illegalService = illegalService;
	}
	public void setEnd(int end) {
		this.end = end;
	}
}
