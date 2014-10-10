package com.netconnection.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netconnection.entity.Softinstallstate;

/**
 * A data access object (DAO) providing persistence and search support for
 * Softinstallstate entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.netconnection.entity.Softinstallstate
 * @author MyEclipse Persistence Tools
 */

public class SoftinstallstateDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(SoftinstallstateDAO.class);
	// property constants
	public static final String MAC = "mac";
	public static final String SOFTNAME = "softname";
	public static final String INSTALLSTATE = "installstate";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	public boolean save(Softinstallstate transientInstance) {
		log.debug("saving Softinstallstate instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return true;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public boolean delete(Softinstallstate persistentInstance) {
		log.debug("deleting Softinstallstate instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
			return true;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public boolean update(Softinstallstate persistentInstance) {
		log.debug("updating Softinstallstate instance");
		try {
			getHibernateTemplate().update(persistentInstance);
			log.debug("update successful");
			return true;
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	

	public Softinstallstate findById(java.lang.Integer id) {
		log.debug("getting Softinstallstate instance with id: " + id);
		try {
			Softinstallstate instance = (Softinstallstate) getHibernateTemplate()
					.get("com.netconnection.entity.Softinstallstate", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Softinstallstate instance) {
		log.debug("finding Softinstallstate instance by example");
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
		log.debug("finding Softinstallstate instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Softinstallstate as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMac(Object mac) {
		return findByProperty(MAC, mac);
	}

	public List findBySoftname(Object softname) {
		return findByProperty(SOFTNAME, softname);
	}

	public List findByInstallstate(Object installstate) {
		return findByProperty(INSTALLSTATE, installstate);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all Softinstallstate instances");
		try {
			String queryString = "from Softinstallstate";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Softinstallstate merge(Softinstallstate detachedInstance) {
		log.debug("merging Softinstallstate instance");
		try {
			Softinstallstate result = (Softinstallstate) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Softinstallstate instance) {
		log.debug("attaching dirty Softinstallstate instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Softinstallstate instance) {
		log.debug("attaching clean Softinstallstate instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SoftinstallstateDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (SoftinstallstateDAO) ctx.getBean("SoftinstallstateDAO");
	}
}