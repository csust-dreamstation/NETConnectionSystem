package com.netconnection.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netconnection.entity.Patchinstallstate;

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

public class PatchinstallstateDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(PatchinstallstateDAO.class);
	// property constants
	public static final String MAC = "mac";
	public static final String PATCHNAME = "patchname";
	public static final String INSTALLSTATE = "installstate";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	public boolean save(PatchinstallstateDAO transientInstance) {
		log.debug("saving PatchinstallstateDAO instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
			return true;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public boolean delete(PatchinstallstateDAO persistentInstance) {
		log.debug("deleting PatchinstallstateDAO instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
			return true;
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public boolean update(PatchinstallstateDAO persistentInstance) {
		log.debug("updating PatchinstallstateDAO instance");
		try {
			getHibernateTemplate().update(persistentInstance);
			log.debug("update successful");
			return true;
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	

	public PatchinstallstateDAO findById(java.lang.Integer id) {
		log.debug("getting PatchinstallstateDAO instance with id: " + id);
		try {
			PatchinstallstateDAO instance = (PatchinstallstateDAO) getHibernateTemplate()
					.get("com.netconnection.entity.Patchinstallstate", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PatchinstallstateDAO instance) {
		log.debug("finding PatchinstallstateDAO instance by example");
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
		log.debug("finding PatchinstallstateDAO instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Patchinstallstate as model where model."
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

	public List findByPatchname(Object softname) {
		return findByProperty(PATCHNAME, softname);
	}

	public List findByInstallstate(Object installstate) {
		return findByProperty(INSTALLSTATE, installstate);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all PatchinstallstateDAO instances");
		try {
			String queryString = "from Patchinstallstate";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PatchinstallstateDAO merge(PatchinstallstateDAO detachedInstance) {
		log.debug("merging PatchinstallstateDAO instance");
		try {
			PatchinstallstateDAO result = (PatchinstallstateDAO) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PatchinstallstateDAO instance) {
		log.debug("attaching dirty PatchinstallstateDAO instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PatchinstallstateDAO instance) {
		log.debug("attaching clean PatchinstallstateDAO instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PatchinstallstateDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PatchinstallstateDAO) ctx.getBean("PatchinstallstateDAO");
	}
}