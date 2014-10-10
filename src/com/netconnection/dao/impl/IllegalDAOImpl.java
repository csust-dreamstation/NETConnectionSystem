package com.netconnection.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netconnection.dao.IllegalDAO;
import com.netconnection.entity.Illegal;
import com.netconnection.entity.PatchList;

public class IllegalDAOImpl extends HibernateDaoSupport implements IllegalDAO {

	@Override
	public boolean saveIllegal(Illegal illegal) {
		try{
			this.getHibernateTemplate().save(illegal);
			return true;
		}catch(RuntimeException re){
			throw re;
		}
		
	}

	@Override
	public boolean updateIllegal(Illegal illegal) {
		try{
			this.getHibernateTemplate().update(illegal);
			return true;
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean deleteIllegal(Illegal illegal) {
		try{
			this.getHibernateTemplate().delete(illegal);
			return true;
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Illegal findByMac(String mac) {
		String hql = "from Illegal as s where s.mac = ?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, mac);
		if(query.list().size()>0){
			return (Illegal) query.list().get(0);
		}
		else return null;
	}

	@SuppressWarnings("unchecked")
	public List<Illegal> findByPaging(final int first, final int max) {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session s) throws HibernateException, SQLException{   
				String queryString="from Illegal";
				Query query=s.createQuery(queryString);
				query.setFirstResult(first);
				query.setMaxResults(max);
				@SuppressWarnings("rawtypes")
				List list=query.list();
				return list;
			}
		});
		
	}


	@SuppressWarnings("unchecked")
	public List<Illegal> findAll() {
		try{
			String query="from Illegal";
			return getHibernateTemplate().find(query);
		}catch(RuntimeException re){
			throw re;
			}
	}

}
