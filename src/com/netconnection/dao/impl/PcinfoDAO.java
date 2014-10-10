package com.netconnection.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netconnection.entity.Pcinfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * Pcinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.netconnection.entity.Pcinfo
 * @author MyEclipse Persistence Tools
 */

public class PcinfoDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(PcinfoDAO.class);
	// property constants
	public static final String IP = "ip";
	public static final String MAC = "mac";
	public static final String CLIENTNAME = "clientname";
	public static final String OS = "os";
	public static final String LOADFLOW = "loadflow";
	public static final String UPFLOW = "upflow";
	public static final String ONLINESTATE = "onlinestate";

	protected void initDao() {
		// do nothing
	}

	public boolean save(Pcinfo transientInstance) {
		log.debug("saving Pcinfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return true;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public boolean delete(Pcinfo persistentInstance) {
		log.debug("deleting Pcinfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
			return true;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public boolean update(Pcinfo persistentInstance) {	
		log.debug("updating Pcinfo instance");
		try {
			this.getHibernateTemplate().update(persistentInstance);
			log.debug("update successful");
			return true;
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public Pcinfo findById(java.lang.Integer id) {
		log.debug("getting Pcinfo instance with id: " + id);
		try {
			Pcinfo instance = (Pcinfo) getHibernateTemplate().get(
					"com.netconnection.entity.Pcinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	/**
	 * 通过分页拿到所有的pc信息
	 */
	@SuppressWarnings("unchecked")
	public List<Pcinfo> findPcListByPaging(final String hql,final int first,final int max){
		List<Pcinfo> pcList = null;
		
		pcList = this.getHibernateTemplate().executeFind(new HibernateCallback() {			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return query.list();
			}
		});	
		return pcList;
	}
	
	/**
	 * 拿到在线的人数
	 * @return
	 */
	public BigInteger findOnlineNum(){	
		String sql = "select count(id) from pcinfo where onlinestate = 1";
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		return (BigInteger) (query.list().get(0));
	}
	
	/**
	 * 拿到表里的所有记录数
	 * @return
	 */
	public BigInteger findAllCount(){
		String sql = "select count(id) from pcinfo where onlinestate=1 or onlinestate=0";
		Session session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		return (BigInteger) (query.list().get(0));
	}
	

	public List findByExample(Pcinfo instance) {
		log.debug("finding Pcinfo instance by example");
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
		log.debug("finding Pcinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Pcinfo as model where model."
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

	public List findByClientname(Object clientname) {
		return findByProperty(CLIENTNAME, clientname);
	}

	public List findByOs(Object os) {
		return findByProperty(OS, os);
	}

	public List findByLoadflow(Object loadflow) {
		return findByProperty(LOADFLOW, loadflow);
	}

	public List findByUpflow(Object upflow) {
		return findByProperty(UPFLOW, upflow);
	}

	public List findByOnlinestate(Object onlinestate) {
		return findByProperty(ONLINESTATE, onlinestate);
	}

	public List findAll() {
		log.debug("finding all Pcinfo instances");
		try {
			String queryString = "from Pcinfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Pcinfo merge(Pcinfo detachedInstance) {
		log.debug("merging Pcinfo instance");
		try {
			Pcinfo result = (Pcinfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Pcinfo instance) {
		log.debug("attaching dirty Pcinfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Pcinfo instance) {
		log.debug("attaching clean Pcinfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PcinfoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PcinfoDAO) ctx.getBean("PcinfoDAO");
	}


}