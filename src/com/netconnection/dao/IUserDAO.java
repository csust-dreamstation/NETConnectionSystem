package com.netconnection.dao;

import java.util.List;

import com.netconnection.entity.User;

public interface IUserDAO {
	public boolean save(User user);
	public boolean update(User user);
	public boolean delete(User user);
	public User findById(int id);
	public List<User> findAllUser();
	public User checkLogin(String userName,String password);
	public User findByName(String name);
	
}
