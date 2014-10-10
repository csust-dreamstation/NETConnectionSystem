package com.netconnection.action;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import cn.com.server.Services;
import cn.com.vo.Message;
import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;
import com.netconnection.entity.TacticsList;
import com.netconnection.service.SoftService;
import com.netconnection.service.TacticsPcService;
import com.netconnection.service.TacticsService;
import com.netconnection.service.TacticsSoftService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author penicillus 
 */
@SuppressWarnings("serial")
public class SoftSubmitAction extends ActionSupport{
	private int id;
	private int time;
	private int type;
	private String mac;
	private int statu;
	private int tacticsid;
	private String together;
	private SoftService softService;
	private Services services;
	private TacticsPcService tacticsPcService;
	private TacticsService tacticsService;
	private TacticsSoftService tacticsSoftService;
	public String execute() throws IOException{
							HttpServletRequest request = ServletActionContext.getRequest();
							String strIds=request.getParameter("recordIds");
							String whiteStr=request.getParameter("whiteId");
							String patchIds=request.getParameter("patchid");
							String tacticsname=request.getParameter("tacticsname");
							System.out.println("黑名单"+strIds+"白名单"+whiteStr+"补丁名单"+tacticsname);
							this.tacticsid=(Integer) tacticsService.findIdByName(tacticsname).get(0);
							SoftList softlist=new SoftList();
							String[]patchStr=patchIds.split(",");
							String[] tStrIds  = strIds.split(",");
							String[] whiteStrIds = whiteStr.split(",");
							TacticsList tacticsList=new TacticsList();
							tacticsList=tacticsService.findByTacticsid(tacticsid);
							if(patchStr[0].equals("")&&tStrIds[0].equals("")&&whiteStrIds[0].equals("")){
								for(int z=0;z<3;z++)
									tacticsSoftService.deleteByTacticsStatu(tacticsid,z);
								HttpServletResponse response = ServletActionContext.getResponse();
								response.setContentType("text/html;charset=UTF-8");
								response.getWriter().write("配置信息保存成功");	
								return null;
							}
							//检测黑白名单不能同时选择
							for(int i=0;i<tStrIds.length;i++)
							{
								for(int j=0;j<whiteStrIds.length;j++)
								{
									if(tStrIds[i].equals(whiteStrIds[j])&&!tStrIds[0].equals("")){
										HttpServletResponse response = ServletActionContext.getResponse();
										response.setContentType("text/html;charset=UTF-8");
										response.getWriter().write("黑白名单不可以同时选择");	
										return null;
									}
								}
							}
							//检测补丁的OS版本要一致
							if(!patchStr[0].equals("")){
							PatchList patchlist=softService.findPatchList(Integer.parseInt(patchStr[0]));
							String checkOs=patchlist.getOs();
							for(int i=1;i<patchStr.length;i++)
							{
							 patchlist=softService.findPatchList(Integer.parseInt(patchStr[i]));
							if(!checkOs.equals(patchlist.getOs())){
								HttpServletResponse response = ServletActionContext.getResponse();
								response.setContentType("text/html;charset=UTF-8");
								response.getWriter().write("在策略中请选择相同版本的补丁");	
								return null;
							}
							}}
							//将策略软件关系表中以前的数据都清空，根据tactics和status
							for(int z=0;z<3;z++)
							tacticsSoftService.deleteByTacticsStatu(tacticsid,z);
							//保存黑名单并发送到客户端
							if(!tStrIds[0].equals("")){
							this.time=tacticsList.getBlacktime();
							this.together="";
							this.type=3;
							for(int  i=0;i<tStrIds.length;i++)
							{
							softlist=softService.findSoft(Integer.parseInt(tStrIds[i]));
							together=together+"|"+softlist.getThreadname()+"*"+softlist.getSoftname();
							tacticsSoftService.saveList(tacticsid,Integer.parseInt(tStrIds[i]),0);
							}
							sendMessage(tacticsid,time+together,type);
							}
							
							//保存白名单并发送到客户端
							if(!whiteStrIds[0].equals(""))
							{
							this.together="";
							this.type=1;
							this.time=tacticsList.getWhitetime();
							for(int i=0;i<whiteStrIds.length;i++)
							{
							softlist=softService.findSoft(Integer.parseInt(whiteStrIds[i]));
							together=together+"|"+softlist.getThreadname()+"*"+softlist.getSoftname();
							tacticsSoftService.saveList(tacticsid,Integer.parseInt(whiteStrIds[i]),1);
							}
							sendMessage(tacticsid,time+together,type);
							}
							
							if(!patchStr[0].equals("")){
							this.together="";
							//保存补丁表并发送到客户端
							this.time=tacticsList.getPatchtime();
							for(int i=0;i<patchStr.length;i++)
							{
							PatchList patchlist=softService.findPatchList(Integer.parseInt(patchStr[i]));
							together=together+"|"+patchlist.getPatchname();
							tacticsSoftService.saveList(tacticsid,Integer.parseInt(patchStr[i]),2);
							}
							this.type=5;
							System.out.println(tacticsid+time+together+type);
							sendMessage(tacticsid,time+together,type);
							}
//							Message message = new Message();		
//							message.setMessage("30|RavMonD.exe*瑞星杀毒软件|vrvsafec.exe*北信源桌面安全防护系统");
//							message.setMac("20-6A-8A-4F-20-AE");
//							message.setType(4);
//							Services service01=new Services();
//							service01.send(message);
//							System.out.println("这里调用了软件提交事件，为什么");	
							HttpServletResponse response = ServletActionContext.getResponse();
							response.setContentType("text/html;charset=UTF-8");
							response.getWriter().write("配置信息保存成功");	
							System.out.println("程序跑到配置信息保存成功这里了");
							return null;
	}
	public String deleteSoft() throws IOException{
		int i=0;
		SoftList softlist=new SoftList();
		PatchList patchlist=new PatchList();
		HttpServletRequest request = ServletActionContext.getRequest();
		String strIds=request.getParameter("recordIds");
		String patchIds=request.getParameter("patchid");
		if(strIds.equals("")&&patchIds.equals(""))
		{
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("请选择要删除的软件或补丁");	
			return null;
		}
		if(!(patchIds.equals(""))){
		String[]patchStr=patchIds.split(",");
		for( i=0;i<patchStr.length;i++)
		{
			System.out.println(patchStr[i]);
			patchlist=softService.findPatchList(Integer.parseInt(patchStr[i]));
			softService.deletePatchlist(patchlist);
			tacticsSoftService.deleteByPatchlist((Integer.parseInt(patchStr[i])));
		}
		}
		if(!(strIds.equals(""))){
		String[] tStrIds = strIds.split(",");
		for( i=0;i<tStrIds.length;i++)
		{
		System.out.println(tStrIds[i]);
		softlist=softService.findSoft(Integer.parseInt(tStrIds[i]));
		softService.deleteSoftlist(softlist);
		tacticsSoftService.deleteBySoft((Integer.parseInt(tStrIds[i])));
		}
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("信息删除成功");	
		System.out.println("程序跑到配置信息保存成功这里了");
		return null;
	}
	public TacticsService getTacticsService() {
		return tacticsService;
	}
	public void setTacticsService(TacticsService tacticsService) {
		this.tacticsService = tacticsService;
	}
	private void sendMessage(int tacticsid2, String string, int type2) {
		Message message = new Message();
		System.out.println("这里是mac地址"+mac+"这里是发送内容"+string);
		System.out.println(tacticsPcService.findMacByTactics(tacticsid2).size());
		for(int index=0;index<tacticsPcService.findMacByTactics(tacticsid2).size();index++){
		String mac=(String) tacticsPcService.findMacByTactics(tacticsid2).get(index);
		if(mac!=null){
		message.setMac(mac);
//		message.setMac("20-6A-8A-4F-20-AE");
		message.setMessage(string);
		message.setType(type2);
		System.out.println("这里是mac地址"+mac+"这里是发送内容"+string);
		services.send(message);}
		else return;
}

	}
	public TacticsPcService getTacticsPcService() {
		return tacticsPcService;
	}
	public void setTacticsPcService(TacticsPcService tacticsPcService) {
		this.tacticsPcService = tacticsPcService;
	}
	public String getTogether() {
		return together;
	}
	public void setTogether(String together) {
		this.together = together;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public Services getServices() {
		return services;
	}
	public void setServices(Services services) {
		this.services = services;
	}
	public TacticsSoftService getTacticsSoftService() {
		return tacticsSoftService;
	}
	public void setTacticsSoftService(TacticsSoftService tacticsSoftService) {
		this.tacticsSoftService = tacticsSoftService;
	}
	public int getStatu() {
		return statu;
	}

	public void setStatu(int statu) {
		this.statu = statu;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getTacticsid() {
		return tacticsid;
	}

	public void setTacticsid(int tacticsid) {
		this.tacticsid = tacticsid;
	}

	public void setId(int id) {
		this.id = id;
	}
	public SoftService getSoftService() {
		return softService;
	}
	public void setSoftService(SoftService softService) {
		this.softService = softService;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}



}
