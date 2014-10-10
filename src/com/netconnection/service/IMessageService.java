package com.netconnection.service;

import java.util.List;

import com.netconnection.entity.Message;

public interface IMessageService {
	//该消息属于没有被阅读
	public final static int NO_READ = 0;
	//该消息已经被阅读了
	public final static int READED = 1;
	//该消息是服务器传送给客户端的
	public final static int MESSAGE_TO_CLIENT = 0;
	//该消息是客户端传送给服务器的
	public final static int MESSAGE_TO_SERVER = 1;
	
	public boolean saveMessage(Message msg);
	public boolean deleteMessage(Message msg);
	public boolean update(Message msg);
	public boolean sendMessage(Message msg);
	
	//通过mac找到所有消息内容
	public List<Message> findByMac(String mac);
	
	//通过mac找到未显示过的消息内容
	public List<Message> findNoShowByMac(String mac);
    public List<Message> findAll();
}
