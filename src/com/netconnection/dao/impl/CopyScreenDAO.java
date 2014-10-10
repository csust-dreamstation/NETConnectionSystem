package com.netconnection.dao.impl;

import java.sql.Timestamp;
import java.util.List;


import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netconnection.entity.CopyScreen;

public class CopyScreenDAO extends HibernateDaoSupport {
	public static final int READ = 1;
	public static final int NO_READ = 0;
	
	private static final Logger log = LoggerFactory.getLogger(CopyScreenDAO.class);
	public boolean save(CopyScreen copyScreen){
		log.debug("saving Message instance");
		try {
			getHibernateTemplate().save(copyScreen);
			log.debug("save successful");
			return true;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	/**
	 * 通过mac地址查询到最新没有读取的截屏消息
	 * @param mac
	 * @return
	 */
	public CopyScreen findNearNoReadByMac(String mac,Timestamp sendTime){
		String hql = "from CopyScreen as c where (mac = ?) and (state = 0 )order by recordtime DESC";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, mac);
		query.setMaxResults(1);
		List list = query.list();
		if(list != null&&list.size()>0){
			CopyScreen cs =  (CopyScreen) list.get(0);
			//比较查找出来的数据记录的时间如果是在发送命令之后就
			if(cs.getRecordtime().after(sendTime)){
				return cs;
			}else{
				 return null;
			}		
		}else{
			return null;			
		}
	}
	
	public void updateState(CopyScreen cs){
		this.getHibernateTemplate().update(cs);
	}
	
	public void delete(CopyScreen cs){
		this.getHibernateTemplate().delete(cs);
	}
	
	public List<CopyScreen> findAll(){
		return this.getHibernateTemplate().find("from CopyScreen");
	}
}

