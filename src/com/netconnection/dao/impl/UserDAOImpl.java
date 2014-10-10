package com.netconnection.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.netconnection.dao.IUserDAO;
import com.netconnection.entity.User;

public class UserDAOImpl extends HibernateDaoSupport implements IUserDAO {

	@Override
	public boolean save(User user) {
		System.out.println("这里是Sava方法打印的"+exsist(user.getUsername()));
		boolean bool = false;
		if(exsist(user.getUsername()))
		{
			user.setOnlinestate(0);
			this.getHibernateTemplate().saveOrUpdate(user);
			bool = true;
		}
		System.out.println("这里是Sava方法打印的"+exsist(user.getUsername()));
		return bool;
	}

	private boolean exsist(String username) {
		User user=findByName(username);
		System.out.println("hellohello");
		if(user==null)
			{return true;}
		else {
			return false;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean update(User user) {
		boolean bool = false;
		System.out.println("这里是Update方法打印的"+exsist(user.getUsername()));
		if(exsist(user.getUsername()));
		{
		try{
			this.getHibernateTemplate().saveOrUpdate(user);
			bool = true;
		}catch(RuntimeException e){
			bool = false;
			e.printStackTrace();
			throw e;
		}
		
		}
		System.out.println("这里是Update方法打印的"+exsist(user.getUsername()));
		return bool;
	}

	@Override
	public boolean delete(User user) {
		boolean bool = false;
		try{
			this.getHibernateTemplate().delete(user);
			System.out.println("userDAO is ok!");
			bool = true;
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
		return bool;
	}

	@Override
	public User findById(int id) {
		User user = null;
		user = (User)this.getHibernateTemplate().get(User.class, id);
		return user;
	}



	@Override
	public User checkLogin(String userName, String password) {
		String hql = "from User as user where user.username = '"+userName+"' and user.password = '"+password+"'";
		User user = null;
		@SuppressWarnings("unchecked")
		List<User> userlist = this.getHibernateTemplate().find(hql);
		if(userlist.size()>0){
			user = new User();
			user = userlist.get(0);
		}
		return user;
	}

	@Override
	public User findByName(String name) {
		String hql = "from User as user where user.username = '"+name+"'";
		User user = null;
		@SuppressWarnings("unchecked")
		List<User> userlist = this.getHibernateTemplate().find(hql);
		if(userlist.size()>0){
			user = new User();
			user = userlist.get(0);
		}
		return user;
	}

	@Override
	public List<User> findAllUser() {
		String hql = "from User";
		return ((List<User>)this.getHibernateTemplate().find(hql));
	}

}
