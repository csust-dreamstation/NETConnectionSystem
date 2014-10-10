package com.netconnection.dao.impl;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.netconnection.dao.ExportReportManage;
import com.netconnection.entity.Pcinfo;
/**
 * @author penicillus榛戝悕鍗曠鐞嗗姛鑳藉疄鐜�
 *
 */
public class ExportReportManageImpl extends HibernateDaoSupport implements ExportReportManage{
	
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
	
	public List findSoftByPaging(final int start, final int number) {
		
		final String hql="from Pcinfo as pc ";
		List softReport = this.getHibernateTemplate().executeFind(new HibernateCallback() {			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(number);
				return query.list();
			}
		});
				
				
		return softReport;
	}

	@Override
	public List findUnclosedByPaging(final int start, final int number) {
		List<Pcinfo> pcinfo=findAll();
		for(int i=0;i<pcinfo.size();i++){
		List<String> list=findSoftnameByMac(pcinfo.get(i).getMac());
		pcinfo.get(i).setSoftlist(list.toString());}
		final String hql="from Pcinfo as pc ";
		List softReport = this.getHibernateTemplate().executeFind(new HibernateCallback() {			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(number);
				return query.list();
			}
		});
		return softReport;
	}


	@SuppressWarnings("unchecked")
	private List<String> findSoftnameByMac(final String mac) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="select distinct a.softname from Softinstallstate a where a.mac=:mac";
					Query query=s.createQuery(queryString);
					query.setParameter("mac", mac);
					List list=query.list();
					return list;
				}
			});
		}catch (RuntimeException re){throw re;}
	
	}


	@Override
	public List findInstallReport(final int start, final int number) {
		List<Pcinfo> pcinfo=findAll();
		for(int i=0;i<pcinfo.size();i++){
		List<String> black=findRunBlackByMac(pcinfo.get(i).getMac());
		List<String> white=findUnrunWhiteByMac(pcinfo.get(i).getMac());
		pcinfo.get(i).setSoftlist(black.toString());
		pcinfo.get(i).setEsoftlist(white.toString());}
		final String hql="from Pcinfo as pc ";
		List softReport = this.getHibernateTemplate().executeFind(new HibernateCallback() {			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(number);
				return query.list();
			}
		});
		return softReport;
	}


	@SuppressWarnings("unchecked")
	private List<String> findUnrunWhiteByMac(final String mac) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="select distinct a.softname from Softinstallstate a where a.mac=:mac and a.installstate=1 and a.state='WLCR'";
//					String queryString="select distinct a.softname from Softinstallstate a where a.mac=:mac and a.installstate=1";
					Query query=s.createQuery(queryString);
					query.setParameter("mac", mac);
					List list=query.list();
					return list;
				}
			});
		}catch (RuntimeException re){throw re;}
	}


	@SuppressWarnings("unchecked")
	private List<String> findRunBlackByMac(final String mac) {
		try{
			return getHibernateTemplate().executeFind(new HibernateCallback(){
				public Object doInHibernate(Session s) throws HibernateException, SQLException{   
					String queryString="select distinct a.softname from Softinstallstate a where a.mac=:mac and a.installstate=0 and a.state='BLCR'";
//					String queryString="select distinct a.softname from Softinstallstate a where a.mac=:mac";
					Query query=s.createQuery(queryString);
					query.setParameter("mac", mac);
					List list=query.list();
					return list;
				}
			});
		}catch (RuntimeException re){throw re;}
	}


	@Override
	public List findIllegalAceessReport(final int start, final int number) {
//		List<Pcinfo> pcinfo=findAll();		
//		for(int i=0;i<pcinfo.size();i++){
//			
//		List<String> black=findRunBlackByMac(pcinfo.get(i).getMac());
//		List<String> white=findUnrunWhiteByMac(pcinfo.get(i).getMac());
//		pcinfo.get(i).setSoftlist(black.toString());
//		pcinfo.get(i).setEsoftlist(white.toString());
//		
//		}
		final String hql="from Illegal as pc where pc.illegal=1";
		List softReport = this.getHibernateTemplate().executeFind(new HibernateCallback() {			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(number);
				return query.list();
			}
		});
		return softReport;
	}



	
}

		

	

