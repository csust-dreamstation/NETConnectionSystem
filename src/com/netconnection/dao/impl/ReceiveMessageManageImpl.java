package com.netconnection.dao.impl;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.netconnection.dao.ReceiveMessageManage;
import com.netconnection.entity.Patchinstallstate;
import com.netconnection.entity.Softinstallstate;
/**
 * @author penicillus榛戝悕鍗曠鐞嗗姛鑳藉疄鐜�
 *
 */
public class ReceiveMessageManageImpl extends HibernateDaoSupport implements ReceiveMessageManage{

	@Override
	public void saveSoftInstallState(String state,String mac, String str, String string) {
		Softinstallstate softinstallstate=new Softinstallstate();
			softinstallstate=findInstallState(mac,str);
			if(softinstallstate!=null){
			softinstallstate.setMac(mac);
			softinstallstate.setSoftname(str);
			softinstallstate.setInstallstate(Integer.parseInt(string));
			softinstallstate.setRecordtime(new Timestamp(System.currentTimeMillis()));
			softinstallstate.setVersion("1.0.0");
			softinstallstate.setState(state);
			try{
				this.getHibernateTemplate().saveOrUpdate(softinstallstate);
			}catch(RuntimeException re){
				throw re;
			}}
			else{
				Softinstallstate softinstallstate1=new Softinstallstate();
				softinstallstate1.setMac(mac);
				softinstallstate1.setSoftname(str);
				softinstallstate1.setInstallstate(Integer.parseInt(string));
				softinstallstate1.setRecordtime(new Timestamp(System.currentTimeMillis()));
				softinstallstate1.setVersion("1.0.0");
				softinstallstate1.setState(state);
				try{
					this.getHibernateTemplate().saveOrUpdate(softinstallstate1);
				}catch(RuntimeException re){
					throw re;
				}
			}
		}

	@SuppressWarnings("unchecked")
	private Softinstallstate findInstallState(final String mac, final String str) {
			try{
				System.out.println(mac+"mac地址");
				System.out.println(str+"用户名");
				List<Softinstallstate> softlist = getHibernateTemplate().executeFind(new HibernateCallback(){
					public Object doInHibernate(Session s) throws HibernateException, SQLException{   
						String queryString="from Softinstallstate a where a.mac=:mac and a.softname=:softname";
						Query query=s.createQuery(queryString);
						query.setParameter("mac", mac);
						query.setParameter("softname", str);
						List<Softinstallstate> list=query.list();
						return list;
					}
				});
				
				if(softlist == null || softlist.size()==0){
					return null;
				}else{
					return softlist.get(0);
				}
				
			}catch (RuntimeException re){throw re;}
		}

	



	@Override
	public void savePatchInstallState(String mac, String str, String string) {
	
		Patchinstallstate patchinstallstate=new Patchinstallstate();
		patchinstallstate=findPatchInstallState(mac,str);
		if(patchinstallstate!=null){
			patchinstallstate.setMac(mac);
			patchinstallstate.setpatchname(str);
			patchinstallstate.setInstallstate(Integer.parseInt(string));
			patchinstallstate.setRecordtime(new Timestamp(System.currentTimeMillis()));
			patchinstallstate.setVersion("1.0.0");
			try{
				this.getHibernateTemplate().saveOrUpdate(patchinstallstate);
			}catch(RuntimeException re){
				throw re;
			}
		}
		else{
			Patchinstallstate patchinstallstate1=new Patchinstallstate();
			patchinstallstate1.setMac(mac);
			patchinstallstate1.setpatchname(str);
			patchinstallstate1.setInstallstate(Integer.parseInt(string));
			patchinstallstate1.setRecordtime(new Timestamp(System.currentTimeMillis()));
			patchinstallstate1.setVersion("1.0.0");
		}
		
	}

	@SuppressWarnings("unchecked")
	private Patchinstallstate findPatchInstallState(final String mac, final String str) {
		try{
			List<Patchinstallstate> patchlist = getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="from Patchinstallstate a where a.mac=:mac and a.patchname=:patchname";
					Query query=s.createQuery(queryString);
					query.setParameter("mac", mac);
					query.setParameter("patchname", str);
					List<Softinstallstate> list=query.list();
					return list;
				}
			});
			
			if(patchlist == null || patchlist.size() == 0){
				return null;
			}else{
				return patchlist.get(0);
			}
			
			
			 
		}catch (RuntimeException re){throw re;}
	}


		}

		

	

