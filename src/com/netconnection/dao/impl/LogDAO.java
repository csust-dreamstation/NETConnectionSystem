package com.netconnection.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netconnection.entity.Log;

/**
 * A data access object (DAO) providing persistence and search support for Log
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.netconnection.entity.Log
 * @author MyEclipse Persistence Tools
 */

public class LogDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(LogDAO.class);
	// property constants
	public static final String OPERATIONERNAME = "operationername";
	public static final String OPERATION = "operation";
	public static final String CONTENT = "content";
	public static final String TIME = "time";
	private int conditionCount;

	protected void initDao() {
		// do nothing
	}

	public boolean save(Log transientInstance) {
		log.debug("saving Log instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return true;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;	
		}
	}

	public void delete(Log persistentInstance) {
		log.debug("deleting Log instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Log findById(java.lang.Integer id) {
		log.debug("getting Log instance with id: " + id);
		try {
			Log instance = (Log) getHibernateTemplate().get(
					"com.netconnection.entity.Log", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Log instance) {
		log.debug("finding Log instance by example");
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
		log.debug("finding Log instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Log as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOperationername(Object operationername) {
		return findByProperty(OPERATIONERNAME, operationername);
	}

	public List findByOperation(Object operation) {
		return findByProperty(OPERATION, operation);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByTime(Object time) {
		return findByProperty(TIME, time);
	}

	public List findAll() {
		log.debug("finding all Log instances");
		try {
			String queryString = "from Log";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Log merge(Log detachedInstance) {
		log.debug("merging Log instance");
		try {
			Log result = (Log) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Log instance) {
		log.debug("attaching dirty Log instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Log instance) {
		log.debug("attaching clean Log instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static LogDAO getFromApplicationContext(ApplicationContext ctx) {
		return (LogDAO) ctx.getBean("LogDAO");
	}

	@SuppressWarnings("unchecked")
	public List<Log> findLogListeByPaging(final String hql, final int start, final int number) {
		List<Log> logList = null;
		
		logList = this.getHibernateTemplate().executeFind(new HibernateCallback() {			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(number);
				return query.list();
			}
		});
		return logList;
	}

	public BigInteger findAllCount() {
		String sql = "select count(id) from log";
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		return (BigInteger) (query.list().get(0));
	}

	public List<Log> findLogByCondition(String condition,int start, int number) {
		System.out.println("---------------------"+condition);
		String[] conditions = condition.split(";");
		Criteria c = this.getSession().createCriteria(Log.class);
		if(conditions == null || conditions.length ==0) return null;
	    if(!"#".equals(conditions[0])){
	    	c.add(Restrictions.like("operationername", "%"+conditions[0]+"%"));
	    }
	    if(!"#".equals(conditions[1])){
	    	c.add(Restrictions.like("operation", "%"+conditions[1]+"%"));
	    }
	    if(!"#".equals(conditions[2])){
	    	c.add(Restrictions.like("content", "%"+conditions[2]+"%"));
	    }
		String[] dates = conditions[3].split("to");
		if(!"#".equals(conditions[3])){
			c.add(Restrictions.between("time", dates[0], dates[1]));
		}
		c.addOrder(Order.desc("time"));
		
		this.setConditionCount(c.list().size());
		
		//分页
		c.setFirstResult(start);
		c.setMaxResults(number);
		
		List<Log> list = c.list();
		if(list !=null && list.size()>0){
			for(Log log:list){
				System.out.println("name:"+log.getContent());
			}
			System.out.println("-------------success-----------------------");
			return list;
		}else{
			return null;
		}
		
	}
	
	public boolean deleteAllData(){
		String sql = "delete from log where 1=1";
		Session session = null;
		Statement state;
		
		session = this.getSession();
		try {
			state = session.connection().createStatement();
			return state.execute(sql);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			session.close();
		}
		return false;
	}

	public int getConditionCount() {
		return conditionCount;
	}

	public void setConditionCount(int conditionCount) {
		this.conditionCount = conditionCount;
	}
	
	

}