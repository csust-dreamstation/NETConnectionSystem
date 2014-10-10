package com.netconnection.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netconnection.entity.Log;
import com.netconnection.entity.Onlinetime;
import com.netconnection.entity.PatchList;

/**
 * A data access object (DAO) providing persistence and search support for
 * Onlinetime entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.netconnection.entity.Onlinetime
 * @author MyEclipse Persistence Tools
 */

public class OnlinetimeDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(OnlinetimeDAO.class);
	// property constants
	public static final String IP = "ip";
	public static final String MAC = "mac";
	public int conditioncount;
	
	protected void initDao() {
		// do nothing
	}

	public boolean save(Onlinetime transientInstance) {
		log.debug("saving Onlinetime instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return true;
	}

	public void delete(Onlinetime persistentInstance) {
		log.debug("deleting Onlinetime instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public boolean update(Onlinetime instance){
		log.debug("deleting Onlinetime instance");
		try {
			getHibernateTemplate().update(instance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
		return true;
	}

	public Onlinetime findById(java.lang.Integer id) {
		log.debug("getting Onlinetime instance with id: " + id);
		try {
			Onlinetime instance = (Onlinetime) getHibernateTemplate().get(
					"com.netconnection.entity.Onlinetime", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	

	public List findByExample(Onlinetime instance) {
		log.debug("finding Onlinetime instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Onlinetime instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Onlinetime as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByIp(Object ip) {
		return findByProperty(IP, ip);
	}

	public List findByMac(Object mac) {
		return findByProperty(MAC, mac);
	}

	public List findAll() {
		log.debug("finding all Onlinetime instances");
		try {
			String queryString = "from Onlinetime o order by o.begintime desc";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	/**
	 * ͨ��mac��ַ�ҵ����µļ�¼�����䷵��
	 * @param mac
	 * @return
	 */
	public Object findNewByMac(String mac){
		String hql = "from Onlinetime as o where o.mac = ? and o.endtime is NULL order by o.begintime desc";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, mac);
		List list = query.list();
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List findMacByTime(Timestamp starttime,Timestamp endtime){
		String sql = "select mac from onlinetime as o where o.begintime between unix_timestamp(?) and unix_timestamp(?)";
//		String hql = "from Onlinetime as o where o.begintime between "+starttime+"and "+ endtime;
	    SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setTimestamp(0, starttime);
		query.setTimestamp(1, endtime);
		return query.list();
	}
	
	public List findMacByTimeStatment(Timestamp starttime,long timelong){
		String sql = "select mac from onlinetime where begintime>? and TIMESTAMPDIFF(SECOND,endtime,begintime)>? and endtime != null";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setTimestamp(0, starttime);
		query.setLong(1, timelong);
		List  macList = query.list();
		System.out.println(macList);
		
		String sql2 = "select mac from onlinetime where begintime>? and TIMESTAMPDIFF(SECOND,?,begintime)>? and endtime is null";
		SQLQuery query2 = this.getSession().createSQLQuery(sql2);
		query2.setTimestamp(0, starttime);
		query2.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
		query2.setLong(2, timelong);
		
		if(macList == null){
			System.out.println("hakhdhio");
			return query2.list();
		}else{
			macList.addAll(query2.list());			
			return macList;
		}		
	}
	
	public List<Onlinetime> findOnlineTimeByCondition(String condition,int start, int number){
		System.out.println("--------findOnlineTimeByCondition-------------"+condition);
		String[] conditions = condition.split(";");
		Criteria c = this.getSession().createCriteria(Onlinetime.class);
		if(conditions == null || conditions.length ==0) return null;
		if(!"#".equals(conditions[0])){
	    	c.add(Restrictions.like("clientname", "%"+conditions[0]+"%"));
	    }
	    if(!"#".equals(conditions[1])){
	    	c.add(Restrictions.like("ip", "%"+conditions[1]+"%"));
	    }
	    if(!"#".equals(conditions[2])){
	    	c.add(Restrictions.like("mac", "%"+conditions[2]+"%"));
	    }
		String[] dates = conditions[3].split("to");
	    c.add(Restrictions.or(
			Restrictions.between("begintime", dates[0], dates[1]),
	        Restrictions.between("endtime", dates[0], dates[1])));
		c.addOrder(Order.desc("begintime"));
		this.setConditioncount(c.list().size());
		//做分页
		c.setFirstResult(start);
		c.setMaxResults(number);
		List<Onlinetime> list = c.list();
		if(list != null && list.size()>0){
			return list;
		}else{
			return null;			
		}
	}

	public Onlinetime merge(Onlinetime detachedInstance) {
		log.debug("merging Onlinetime instance");
		try {
			Onlinetime result = (Onlinetime) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Onlinetime instance) {
		log.debug("attaching dirty Onlinetime instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Onlinetime instance) {
		log.debug("attaching clean Onlinetime instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static OnlinetimeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (OnlinetimeDAO) ctx.getBean("OnlinetimeDAO");
	}

	@SuppressWarnings("unchecked")
	public List<Onlinetime> findOnlineTimeByPaging(final int start, final int number) {
		log.debug("finding part Onlinetime instances");
		try {
		return getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session s) throws HibernateException, SQLException{   
				String queryString="from Onlinetime o order by o.begintime desc";
				Query query=s.createQuery(queryString);
				query.setFirstResult(start);
				query.setMaxResults(number);
				System.out.println("hello world");
				@SuppressWarnings("rawtypes")
				List list=query.list();
				return list;
			}
		});
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getConditioncount() {
		return conditioncount;
	}

	public void setConditioncount(int conditioncount) {
		this.conditioncount = conditioncount;
	}
	
	
	
}