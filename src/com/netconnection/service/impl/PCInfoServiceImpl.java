package com.netconnection.service.impl;

import java.math.BigInteger;
import java.util.List;

import com.netconnection.dao.impl.PcinfoDAO;
import com.netconnection.entity.Pcinfo;
import com.netconnection.service.IPCInfoService;

public class PCInfoServiceImpl implements IPCInfoService {
	private PcinfoDAO pcinfoDAO;
	
	@Override
	public List<Pcinfo> findOnline() {
		return pcinfoDAO.findByOnlinestate(ONLINESTATE);
	}
	
	@Override
	public boolean savePCInfo(Pcinfo pcinfo) {
		return pcinfoDAO.save(pcinfo);
	}
	
	@Override
	public boolean updatePCInfo(Pcinfo pcinfo) {
		// TODO Auto-generated method stub
		return pcinfoDAO.update(pcinfo);
	}
	
	@Override
	public boolean deletePCInfo(Pcinfo pcinfo) {
		// TODO Auto-generated method stub
		return pcinfoDAO.delete(pcinfo);
	}
	
	public List findAll(){
		return pcinfoDAO.findAll();
	}
	@Override
	public Pcinfo findByMac(String mac) {
		System.out.println("pcmac is ok");
		// TODO Auto-generated method stub
		List pclist = pcinfoDAO.findByMac(mac);
		if(pclist == null ||pclist.size() == 0){
			return null;
		}else{
			System.out.println("name:"+((Pcinfo)pclist.get(0)).getClientname());
			return (Pcinfo)(pclist.get(0));			
		}
		
	}
	
	@Override
	public List<Pcinfo> findByPaging(int first, int max) {
		String hql = "from Pcinfo as pc where pc.onlinestate=1 or pc.onlinestate=0";
		return pcinfoDAO.findPcListByPaging(hql, first, max);
	}
	
	public int findOnlineNum(){
		BigInteger in = pcinfoDAO.findOnlineNum();
		return in.intValue();
	}
	
	@Override
	public List<Pcinfo> findByConditionAndPage(String condition, String value,int first, int max) {
		String hql = "from Pcinfo as pc where pc."+condition+" like '%"+value+"%' and pc.onlinestate=1 or pc.onlinestate=0";
		return pcinfoDAO.findPcListByPaging(hql, first, max);
	}
	
	
	@Override
	public List<Pcinfo> findOnlinePcByPaging(int first,int max){
		String hql = "from Pcinfo as pc where pc.onlinestate=1";
		return pcinfoDAO.findPcListByPaging(hql, first, max);
	}
	
	@Override
	public int findAllCount() {
		return pcinfoDAO.findAllCount().intValue();
	}
		
	public PcinfoDAO getPcinfoDAO() {
		return pcinfoDAO;
	}
	
	public void setPcinfoDAO(PcinfoDAO pcinfoDAO) {
		this.pcinfoDAO = pcinfoDAO;
	}


	
}
