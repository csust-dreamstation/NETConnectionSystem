package com.netconnection.dao.impl;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.netconnection.dao.TacticsManage;
import com.netconnection.entity.TacticsList;


/**
 * @author penicillus黑名单管理功能实现
 *
 */
public class TacticsManageImpl extends HibernateDaoSupport implements TacticsManage{	

	
	@SuppressWarnings("unchecked")
	public List<TacticsList> findAll() {
		try{
			String query="from TacticsList";
			return getHibernateTemplate().find(query);
			
		}catch(RuntimeException re){
			throw re;
			}
	}
	
	
	

	@SuppressWarnings("unchecked")
	public List<TacticsList> findByid(int id) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="from TacticsList";
					Query query=s.createQuery(queryString);
					List<TacticsList> list=query.list();
					return list;
				}
			});
		}catch (RuntimeException re){throw re;}
	}


	public void addtacticsList(TacticsList tacticsList) {
		try{
			this.getHibernateTemplate().save(tacticsList);
		}catch(RuntimeException re){
			throw re;
		}

	}
	
	public TacticsList findByTacticsid(int tacticsid){
		TacticsList tacticsList = null;
		tacticsList =(TacticsList) this.getHibernateTemplate().get(TacticsList.class, tacticsid);
		return tacticsList;
	}


	public void deletetacticsList(int tacticsid) {
		try{
			TacticsList tacticsList=new TacticsList();
			tacticsList=findByTacticsid(tacticsid);
			this.getHibernateTemplate().delete(tacticsList);
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}




	@Override
	public List findIdByName(final String tacticsname) {
		TacticsList tactics=new TacticsList();
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session s) throws HibernateException, SQLException{   
				String queryString="select distinct a.tacticsid from TacticsList a where a.tacticsname=:tacticsname";
				Query query=s.createQuery(queryString);
				query.setParameter("tacticsname", tacticsname);
				List<Integer> list=query.list();
				return list;
			}
		});
	}




	@Override
	public void updatetacticsList(TacticsList tacticsList) {
		try{
			this.getHibernateTemplate().update(tacticsList);
		}catch(RuntimeException re){
			throw re;
		}
		
	}
}

	

