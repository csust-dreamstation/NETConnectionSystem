package com.netconnection.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.netconnection.dao.IUserDAO;
import com.netconnection.entity.User;
import com.netconnection.exception.NoFindUserByNameException;
import com.netconnection.service.IUserService;
import com.netconnection.util.EncryptionUtil;

public class UserServiceImpl implements IUserService {
	private IUserDAO userDAO = null;
	
	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public boolean saveUser(User user) {
		//将user的密码加密后存入数据库
		try {
			user.setPassword(EncryptionUtil.eccryptByMD5(user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("加密不成功");
			e.printStackTrace();
		}
		
		return this.userDAO.save(user);
	}

	@Override
	public boolean updateUser(User user) {
		
		return this.userDAO.update(user);
	}

	@Override
	public boolean deleteUser(User user) {
		System.out.println("userservice is ok!");
		return this.userDAO.delete(user);
	}

	@Override
	public User findByUserId(int id) {
		return this.userDAO.findById(id);
	}
	
	@Override
	public List<User> findAllUserList() {
		return this.userDAO.findAllUser();
	}

	@Override
	public User login(String userName, String password) throws NoSuchAlgorithmException, NoFindUserByNameException{
			User user = null;
			user = this.userDAO.findByName(userName);
			if(user != null){
				if(!EncryptionUtil.eccryptByMD5(password).equals(user.getPassword()))
					user = null;
			}else{
				throw new NoFindUserByNameException("用户名不存在!");
			}
			return user;
	}

	@Override
	public User findUserByName(String name) {
		return userDAO.findByName(name);
	}

	

}

