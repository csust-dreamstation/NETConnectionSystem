package com.netconnection.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import com.netconnection.entity.User;
import com.netconnection.exception.NoFindUserByNameException;

public interface IUserService {
	public static final int ONLINE = 1;
	public static final int NO_ONLINE = 0;
	
	public boolean saveUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(User user);
	public User findByUserId(int id);
	public User findUserByName(String name);
	public List<User> findAllUserList();
	public User login(String userName,String password)throws NoSuchAlgorithmException,NoFindUserByNameException;
	
}
