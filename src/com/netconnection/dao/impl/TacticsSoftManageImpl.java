package com.netconnection.dao.impl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.netconnection.dao.TacticsSoftManage;
import com.netconnection.entity.SoftList;
import com.netconnection.entity.TacticsSoftList;

/**
 * @author penicillus榛戝悕鍗曠鐞嗗姛鑳藉疄鐜�
 *
 */
public class TacticsSoftManageImpl extends HibernateDaoSupport implements TacticsSoftManage{	

	
	@SuppressWarnings("unchecked")
	public List<TacticsSoftList> findAll() {
		try{
			String query="from TacticsSoftList";
			return getHibernateTemplate().find(query);
			
		}catch(RuntimeException re){
			throw re;
			}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TacticsSoftList> findByid(int id) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="from TacticsSoftList";
					Query query=s.createQuery(queryString);
					List list=query.list();
					return list;
				}
			});
		}catch (RuntimeException re){throw re;}
	}

		public List findListId(final int tacticsid,final int statu) {
			try{
				return getHibernateTemplate().executeFind(new HibernateCallback(){
					public Object doInHibernate(Session s) throws HibernateException, SQLException{   
						String queryString="select a.listid from TacticsSoftList a where a.tacticsid=:tacticsid and a.statu=:statu";
						Query query=s.createQuery(queryString);
						query.setParameter("tacticsid", tacticsid);
						query.setParameter("statu", statu);
						List list=query.list();
						return list;
					}
				});
			}catch (RuntimeException re){throw re;}
		}


		@Override
		public void saveList(int tacticsid, int parseInt, int statu) {
			TacticsSoftList tacticsSoftList=new TacticsSoftList();
			tacticsSoftList.setTacticsid(tacticsid);
			tacticsSoftList.setListid(parseInt);
			tacticsSoftList.setStatu(statu);
			try{
				this.getHibernateTemplate().save(tacticsSoftList);
			}catch(RuntimeException re){
				throw re;
			}

			
			
		}

//根据tacticsId和status删除相应的记录；
		
		@Override
		public void deleteByTacticsStatu(int tacticsid, int statu) {
			try {
				List<TacticsSoftList> l = new ArrayList<TacticsSoftList>();
				l=findId(tacticsid,statu);
				if(l!=null)
				for(int i=0;i<l.size();i++){
					this.getHibernateTemplate().delete( (TacticsSoftList)this.getHibernateTemplate().get(TacticsSoftList.class,l.get(i)));
				}
				else{
					System.out.println("没找到策略相对应的黑名单或白名单");
					return;
				}
			} catch (RuntimeException re) {
				throw re;
			}
			
		}


@SuppressWarnings("unchecked")
//根据tacticsId和status找到相应的id；
private List<TacticsSoftList> findId(final int tacticsid, final int statu) {
	try{
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session s) throws HibernateException, SQLException{   
				String queryString="select distinct a.id from TacticsSoftList a where a.tacticsid=:tacticsid and a.statu=:statu";
				Query query=s.createQuery(queryString);
				query.setParameter("statu", statu);
				query.setParameter("tacticsid", tacticsid);
				List<TacticsSoftList> list=query.list();
				return list;
			}
		});
	}catch (RuntimeException re){throw re;}
}


@Override
public void deleteBySoft(int i) {
	List<TacticsSoftList> tacticsSoftlist= new ArrayList<TacticsSoftList>();
	tacticsSoftlist=findBySoftId(i,1);
	for(int j=0;j<tacticsSoftlist.size();j++){
		deleteTacticsSoft(tacticsSoftlist.get(j));
	}
	tacticsSoftlist=findBySoftId(i,0);
	for(int j=0;j<tacticsSoftlist.size();j++){
		deleteTacticsSoft(tacticsSoftlist.get(j));
	}
}

private void deleteTacticsSoft(TacticsSoftList tacticsSoftList) {
	try{
		this.getHibernateTemplate().delete(tacticsSoftList);
	}catch(RuntimeException e){
		e.printStackTrace();
		throw e;
	}
}


@SuppressWarnings("unchecked")
private List<TacticsSoftList> findBySoftId(final int i, final int j) {
	try{
		return (List<TacticsSoftList>) getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session s) throws HibernateException, SQLException{   
				String queryString="from TacticsSoftList a where a.listid=:listid and a.statu=:statu";
				Query query=s.createQuery(queryString);
				query.setParameter("listid", i);
				query.setParameter("statu", j);
				List<TacticsSoftList> list=query.list();
				return list;
			}
		});
	}catch (RuntimeException re){throw re;}
}


@Override
public void deleteByPatchlist(int i) {
	List<TacticsSoftList> tacticsSoftlist= new ArrayList<TacticsSoftList>();
	tacticsSoftlist=findBySoftId(i,2);
	for(int j=0;j<tacticsSoftlist.size();j++){
		deleteTacticsSoft(tacticsSoftlist.get(j));
	}
}

		
}
	

