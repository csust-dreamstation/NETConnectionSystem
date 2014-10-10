package com.netconnection.dao.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.netconnection.entity.Log;
import com.netconnection.entity.Onlinetime;
import com.netconnection.entity.Patchinstallstate;

public class DaoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//LogDAOTest();
//		PcinfoDAOTest();
		//copyScreenDAOTest();
//		onlineDAOTest();
		//patchinstallDAOTest();
	}
	
	public static void LogDAOTest(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		LogDAO logDao = (LogDAO)cxt.getBean("LogDAO");
		logDao.deleteAllData();
//		List<Log> loglist = logDao.findLogByCondition("李;#;#;2014-01-01 00:00:00to2014-06-23 00:00:00");
//		if(loglist.size()>0){
//			int i=0; 
//			for(Log log:loglist){
//				System.out.println("content"+(i++)+log.getContent());
//			}
//		}
	}
	
	public static void PcinfoDAOTest(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		PcinfoDAO pcDao = (PcinfoDAO)cxt.getBean("PcinfoDAO");
		System.out.println(pcDao.findOnlineNum());
	}
	
	public static void copyScreenDAOTest(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		CopyScreenDAO csDao = (CopyScreenDAO)cxt.getBean("CopyScreenDAO");
//		System.out.println(pcDao.findOnlineNum());
//		CopyScreen cs = new CopyScreen();
//		cs.setMac("20-6B-8A-4F-20-AE");
//		cs.setPath("1399367011770.bmp");
//		cs.setState(0);
//		cs.setRecordtime(new Timestamp(System.currentTimeMillis()));
//		System.out.println(csDao.save(cs));
		/*CopyScreen cs = csDao.findNearNoReadByMac("20-6B-8A-4F-20-AE");
		System.out.println("path:"+cs.getPath());*/
	}
	
//	public static void onlineDAOTest(){
//		ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
//		OnlinetimeDAO onlineDao = (OnlinetimeDAO)cxt.getBean("OnlinetimeDAO");
//		List<Onlinetime> list = onlineDao.findOnlineTimeByCondition("ad;#;#;2013-11-11 02:03:56to2014-07-07 02:03:56");
////		Onlinetime online = (Onlinetime) onlineDao.findNewByMac("44-37-E6-11-4F-30");
//		for(Onlinetime online:list){
//			System.out.println("online_ip:"+online.getIp()+" online_st:"+online.getBegintime()+" endt:"+online.getEndtime());	
//		}
//	}
	
	public static void patchinstallDAOTest(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		PatchinstallstateDAO patchDao = (PatchinstallstateDAO)cxt.getBean("PatchinstallstateDAO");
		List<Patchinstallstate> list = patchDao.findByMac("44-37-E6-11-4F-30");
		for(Patchinstallstate p:list){
			System.out.println(p.getpatchname());
		}
	}
}
