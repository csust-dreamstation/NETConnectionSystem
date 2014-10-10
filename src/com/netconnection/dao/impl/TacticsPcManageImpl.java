package com.netconnection.dao.impl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netconnection.dao.TacticsPcManage;
import com.netconnection.entity.Pcinfo;
import com.netconnection.entity.SoftList;
import com.netconnection.entity.TacticsList;
import com.netconnection.entity.TacticsPc;

/**
 * @author penicillus榛戝悕鍗曠鐞嗗姛鑳藉疄鐜�
 *
 */
public class TacticsPcManageImpl extends HibernateDaoSupport implements TacticsPcManage{

	@Override
	public void saveTacticsPc(String string, int tacticsid) {
		TacticsPc tacticsPc=new TacticsPc();
		tacticsPc.setId(findTacticsPcAll().size());
		tacticsPc.setMac(string);
		tacticsPc.setTacticsid(tacticsid);
		try{
			this.getHibernateTemplate().save(tacticsPc);
		}catch(RuntimeException re){
			throw re;
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<TacticsPc> findTacticsPcAll() {
		try{
			String query="from TacticsPc";
			return getHibernateTemplate().find(query);
		}catch(RuntimeException re){
			throw re;
			}
	}
	@Override
		public List findMacByTactics(final int tacticsid) {
			try{
				return getHibernateTemplate().executeFind(new HibernateCallback(){
					public Object doInHibernate(Session s) throws HibernateException, SQLException{   
						String queryString="select distinct a.mac from TacticsPc a where a.tacticsid=:tacticsid";
						Query query=s.createQuery(queryString);
						query.setParameter("tacticsid", tacticsid);
						List list=query.list();
						return list;
					}
				});
			}catch (RuntimeException re){throw re;}
		}

	@Override
	public void setCheckedByMac(String mac) {
		Pcinfo pcinfo=new Pcinfo();
		List list=findIdByMac(mac);
		if(list != null && list.size()!=0)
		pcinfo=findPcInfoById((Integer)findIdByMac(mac).get(0));
		else{
			System.out.println("list为空");
			return;
		}
		pcinfo.setStatu("checked");
		updatePcInfo(pcinfo);
	}
	
	private void updatePcInfo(Pcinfo   pcinfo) {
		try {
			this.getHibernateTemplate().update(pcinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("unchecked")
	public List findIdByMac(final String mac) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="select distinct a.id from Pcinfo a where a.mac=:mac";
					Query query=s.createQuery(queryString);
					query.setParameter("mac", mac);
					List out=query.list();
					return out;
				}
			});
		}catch (RuntimeException re){throw re;}
	}
	
	
	public Pcinfo findPcInfoById(int id) {
		Pcinfo  pcinfo= null;
		pcinfo= (Pcinfo) this.getHibernateTemplate().get("com.netconnection.entity.Pcinfo",id);
		return 	pcinfo;
	}

	public List intPcinfo() {
		String updateHql="update Pcinfo t set t.statu = 'unchecked' where t.statu = 'checked'";
		try{
			this.getHibernateTemplate().bulkUpdate(updateHql);
		}catch(RuntimeException re){
			throw re;
		}
		return null;	
	}

	@SuppressWarnings("unchecked")
	public List<Pcinfo> findAll() {
		try{
			String query="from Pcinfo";
			return getHibernateTemplate().find(query);
		}catch(RuntimeException re){
			throw re;
			}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Pcinfo> findByid(int id) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="from Pcinfo";
					Query query=s.createQuery(queryString);
					List list=query.list();
					return list;
				}
			});
		}catch (RuntimeException re){throw re;}
	}

	@Override
	public void deleteBytacticsid(int tacticsid) {
		try{ 
			List tacticsPcList=new ArrayList<TacticsList>();
			tacticsPcList=findByTacticsid(tacticsid);
			if(tacticsPcList!=null){
			for(int i=0;i<tacticsPcList.size();i++)
			this.getHibernateTemplate().delete(tacticsPcList.get(i));}
			else return;
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	public List<TacticsPc> findByTacticsid(final int tacticsid){
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="from TacticsPc a where a.tacticsid=:tacticsid";
					Query query=s.createQuery(queryString);
					query.setParameter("tacticsid", tacticsid);
					List out=query.list();
					return out;
				}
			});
		}catch (RuntimeException re){throw re;}
	}
	@Override
	public void deleteByMac(String string) {
		TacticsPc tacticsPc=new TacticsPc();
		List<TacticsPc> tacticsPcList=new ArrayList<TacticsPc>();
		tacticsPcList=findTacticsPcListByMac(string);
		for(int i=0;i<tacticsPcList.size();i++)
			this.getHibernateTemplate().delete(tacticsPcList.get(i));
	}
	@SuppressWarnings("unchecked")
	private List<TacticsPc> findTacticsPcListByMac(final String string) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="from TacticsPc a where a.mac=:mac";
					Query query=s.createQuery(queryString);
					query.setParameter("mac",string);
					List out=query.list();
					return out;
				}
			});
		}catch (RuntimeException re){throw re;}
	}
	@Override
	public List findTacticsid(final String mac) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="select a.tacticsid from TacticsPc a where a.mac=:mac";
					Query query=s.createQuery(queryString);
					query.setParameter("mac",mac);
					List out=query.list();
					return out;
				}
			});
		}catch (RuntimeException re){throw re;}
	}

	
	}	


