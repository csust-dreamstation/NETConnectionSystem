package com.netconnection.dao.impl;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.netconnection.dao.SoftManage;
import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;
/**
 * @author penicillus榛戝悕鍗曠鐞嗗姛鑳藉疄鐜�
 *
 */
public class SoftManageImpl extends HibernateDaoSupport implements SoftManage{	

	
	@SuppressWarnings("unchecked")
	public List<SoftList> findAll() {
		try{
			String query="from SoftList";
			return getHibernateTemplate().find(query);
		}catch(RuntimeException re){
			throw re;
			}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SoftList> findByid(int id) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="from SoftList";
					Query query=s.createQuery(queryString);
					List list=query.list();
					return list;
				}
			});
		}catch (RuntimeException re){throw re;}
	}


		@Override
		public SoftList findSoft(int softid) {
			SoftList softList= new SoftList();
			softList = (SoftList)this.getHibernateTemplate().get("com.netconnection.entity.SoftList",softid);
			return softList;
		}
	//
		public void updateSoftList(SoftList softList) {	
			try{
				this.getHibernateTemplate().update(softList);
			}catch(RuntimeException e){
				e.printStackTrace();
				throw e;
			}
		}


		public void addSoftList(SoftList softList) {
				try{
					this.getHibernateTemplate().save(softList);
				}catch(RuntimeException re){
					throw re;
				}
	
			}


		@SuppressWarnings("unchecked")
		public List<PatchList> findPatchByid(int id) {
			try{
				return getHibernateTemplate().executeFind(new HibernateCallback(){
					public Object doInHibernate(Session s) throws HibernateException, SQLException{   
						String queryString="from PatchList";
						Query query=s.createQuery(queryString);
						List<PatchList> list=query.list();
						return list;
					}
				});
			}catch (RuntimeException re){throw re;}
		}


		public void intSoftList() {
			SoftList softlist=new SoftList();
			for(int i=0;i<findAll().size();i++){
				softlist=((SoftList) findByid(0).get(i));	
				softlist.setChoose("unchecked");
				updateSoftList(softlist);
			}

		}

		public void setPatchByid(int integer) {
			PatchList patchlist=new PatchList();
			patchlist=findPatchList(integer);
			if(patchlist==null){
				return;
			}
			patchlist.setChoose("checked");
			updatePatchList(patchlist);
			
		}


		private void updatePatchList(PatchList patchlist) {
			try {
				this.getHibernateTemplate().update(patchlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}


		public void setCheckedByid(int integer,int statu) {
			SoftList softlist=new SoftList();
			softlist=findSoft(integer);
			if(softlist==null)
				System.out.println("softlist为空,传过来的软件id为"+integer);
			else{
				softlist.setStatu(statu);
				softlist.setChoose("checked");
			updateSoftList(softlist);
			}
		}

		@Override
		public void setUnPatchByid(int patchid) {
			PatchList patchlist=new PatchList();
			patchlist=findPatchList(patchid);
			patchlist.setChoose("unchecked");
			updatePatchList(patchlist);
			
		}


		@Override
		public void addPatchList(PatchList patchList) {
			try{
				this.getHibernateTemplate().save(patchList);
			}catch(RuntimeException re){
				throw re;
			}

			
		}


		
		@SuppressWarnings("unchecked")
		public List<SoftList> findByPaging(final int start, final int number)  {
			List<SoftList> SoftList = null;
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="from SoftList";
					Query query=s.createQuery(queryString);
					query.setFirstResult(start);
					query.setMaxResults(number);
					List list=query.list();
					return list;
				}
			});
		}
	

		@Override
		public PatchList findPatchList(int parseInt) {
			try {
				PatchList test = (PatchList) this.getHibernateTemplate().get("com.netconnection.entity.PatchList",parseInt);
				return test;
			} catch (RuntimeException re) {
				throw re;
			}
		}


		@Override
		public void deleteSoftlist(SoftList softlist) {
			try{
				this.getHibernateTemplate().delete(softlist);
			}catch(RuntimeException e){
				e.printStackTrace();
				throw e;
			}
			
		}


		@Override
		public void deletePatchlist(PatchList patchlist) {
			try{
				this.getHibernateTemplate().delete(patchlist);
			}catch(RuntimeException e){
				e.printStackTrace();
				throw e;
			}
			
			
		}


		@Override
		public String findSoftNameByThreadName(String threadname){
			String hql = "select s.softname from SoftList as s where s.threadname = ?";
			Query query = this.getSession().createQuery(hql);
			query.setString(0, threadname);
			return (String)query.list().get(0);
		}


		@SuppressWarnings("unchecked")
		public List<PatchList> finPatchByPaging(final int start, final int number) {
				List<PatchList> PatchList = null;
				return getHibernateTemplate().executeFind(new HibernateCallback(){
					public Object doInHibernate(Session s) throws HibernateException, SQLException{   
						String queryString="from PatchList";
						Query query=s.createQuery(queryString);
						query.setFirstResult(start);
						query.setMaxResults(number);
						@SuppressWarnings("rawtypes")
						List list=query.list();
						return list;
					}
				});
				
			}
		
		}


		

	

