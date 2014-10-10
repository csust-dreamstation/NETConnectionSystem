package com.netconnection.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import cn.com.server.Server;

import com.netconnection.dao.impl.CopyScreenDAO;
import com.netconnection.entity.CopyScreen;
import com.netconnection.service.ICopyScreenService;

public class CopyScreenServiceImpl implements ICopyScreenService{
	private CopyScreenDAO copyscreenDao;
	
	@Override
	public boolean saveCopyScreen(CopyScreen copyScreen) {
		return copyscreenDao.save(copyScreen);
	}

	@Override
	public CopyScreen findNearNoReadByMac(String mac,Timestamp sendTime) {
		// TODO Auto-generated method stub
		CopyScreen cs = copyscreenDao.findNearNoReadByMac(mac,sendTime);
		return cs;
	}
	
	@Override
	public void updateReadState(CopyScreen copyScreen) {
		copyScreen.setState(copyscreenDao.READ);
		copyscreenDao.updateState(copyScreen);
		
	}
	
	@Override
	public void deleteCopyScreenInfo(CopyScreen copyScreen) {
		String path = Server.class.getResource("").getPath();
		path = path.substring(0,path.indexOf("/WEB-INF"));
		String filepath=path+"/copyscreen/"+copyScreen.getPath()+".bmp";
		
		File f = new File(filepath);
		if(f.exists() == true){
			f.delete();
		}
		copyscreenDao.delete(copyScreen);
		
	}
	
	@Override
	public List<CopyScreen> findRest(){
		return copyscreenDao.findAll();
	}

	public CopyScreenDAO getCopyscreenDao() {
		return copyscreenDao;
	}

	public void setCopyscreenDao(CopyScreenDAO copyscreenDao) {
		this.copyscreenDao = copyscreenDao;
	}

	

	
	
	

}
