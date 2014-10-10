package com.netconnection.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.netconnection.entity.Log;
import com.netconnection.entity.Pcinfo;
import com.netconnection.entity.Softinstallstate;
import com.netconnection.entity.User;

public class ServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//testAddUser();
		//testLogDAO();
		//addPcData();
		//pageTest();
		//onlinetimeTest();
		//testGetMsg();
		pcinformationTest();
	}
	
	public static void testAddUser(){
		User user = new User();
		user.setUsername("lisi");
		user.setPassword("12345678");
		ApplicationContext cxt = new ClassPathXmlApplicationContext("application*.xml");
		IUserService userService = (IUserService)cxt.getBean("userService");
		userService.saveUser(user);
	}
	
	public static void testGetMsg(){
	
		ApplicationContext cxt = new ClassPathXmlApplicationContext("application*.xml");
		IMessageService messageService = (IMessageService)cxt.getBean("messageService");
		for(com.netconnection.entity.Message m :messageService.findNoShowByMac("20-6A-8A-4F-20-AE")){
			System.out.println("content:"+m.getContent());
		}
	}
	
	
	
	public static void testLogDAO(){
		Log log = new Log();
		log.setContent("��ӹ���Ա");
		log.setOperation("���");
		log.setOperationername("zhangsan");
		Date now = new Date();
		log.setTime(now.toLocaleString());
		ApplicationContext cxt = new ClassPathXmlApplicationContext("application*.xml");
		ILogService logService = (ILogService)cxt.getBean("logService");
		logService.saveLog(log);
	}
	//����pcservice
	public static void testPc(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("application*.xml");
		IPCInfoService pcinfoService = (IPCInfoService)cxt.getBean("pcinfoService");
//		List<Pcinfo> list = pcinfoService.findOnline();
//		for(Pcinfo pc:list){
//			System.out.println(pc.getIp());
//			System.out.println(pc.getOs());
//		}
		Pcinfo pc = new Pcinfo();
		pc.setIp("123791873070");
		pc.setMac("abcdefg");
		pc.setClientname("zhangsan");
		pc.setLoadflow(1234L);
		pc.setUpflow(3456L);
		pc.setOnlinestate(pcinfoService.ONLINESTATE);
		pc.setOs("windows 7");
		Date now = new Date();
		Timestamp time = new Timestamp(now.getTime());
		pc.setRecordtime(time);
		
		System.out.println("save:"+pcinfoService.savePCInfo(pc));
		
	}
	
	public static void addPcData(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("application*.xml");
		IPCInfoService pcinfoService = (IPCInfoService)cxt.getBean("pcinfoService");
		
		for(int i = 1;i<500;i++){
			Pcinfo pc = new Pcinfo();
			if(i<256){
				pc.setIp("192.168.1."+i);				
			}else{
				pc.setIp("192.168.2."+(i-256));	
			}
			pc.setMac("20-6B-8A-4F-20-AB");
			pc.setClientname("zhangsan"+i);
			pc.setLoadflow(0L);
			pc.setUpflow(0L);
			pc.setOnlinestate(pcinfoService.ONLINESTATE);
			pc.setOs("windows 7");
			Timestamp time = new Timestamp(System.currentTimeMillis());
			pc.setRecordtime(time);
			pcinfoService.savePCInfo(pc);
		}
		
	}
	
	public static void pageTest(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("application*.xml");
		IPCInfoService pcinfoService = (IPCInfoService)cxt.getBean("pcinfoService");
		List<Pcinfo> pclist = pcinfoService.findByPaging(0, 50);
		for(Pcinfo pc:pclist){
			System.out.println("clientname:"+pc.getClientname());
		}
		System.out.println("size:"+pclist.size());
	}
	//����δͨ��
	public static void onlinetimeTest(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("application*.xml");
		IOnlineTimeService onlineTimeService = (IOnlineTimeService)cxt.getBean("onlinetimeService");
		
//		Onlinetime online = new Onlinetime();
//		online.setMac("20-6B-8A-4F-20-AB");
//		online.setIp("127.0.0.1");
//		Date startdate = new Date(2014-1900, 5, 5, 15, 0, 0);
//		online.setBegintime(new Timestamp(startdate.getTime()));
//		Date enddate = new Date(2014-1900, 5, 5, 19, 0, 0);
//		online.setEndtime(new Timestamp(enddate.getTime()));
		
//		System.out.println("save:"+onlineTimeService.saveOnlineTime(online));
		Date startdate = new Date(2014-1900, 5, 5, 0, 0, 0);
		Date enddate = new Date(2014-1900, 5, 5, 16, 0, 0);
		
		List<Pcinfo> pclist = onlineTimeService.findByTime(new Timestamp(startdate.getTime()), new Timestamp(enddate.getTime()));
		if(pclist == null){
			System.out.println("true");
		}else{
			System.out.println(pclist.get(0).getClientname());
			
		}
		
		
	}
	
	public static void pcinformationTest(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("application*.xml");
		ISoftInstallStateService softService = (ISoftInstallStateService)cxt.getBean("softInstallStateService");
		List<Softinstallstate> softlist = softService.findSoftinstallstateByMac("20-6A-8A-4F-20-AE");
		
		for(Softinstallstate s:softlist){
			System.out.println(s.getSoftname());
		}
	}
	

}
