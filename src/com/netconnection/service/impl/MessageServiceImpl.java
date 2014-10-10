package com.netconnection.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;



import cn.com.server.Services;

import com.netconnection.dao.impl.MessageDAO;
import com.netconnection.entity.Message;
import com.netconnection.service.IMessageService;

public class MessageServiceImpl implements IMessageService{
	private MessageDAO msgDAO;
	private Services services;
	@Override
	public boolean saveMessage(Message msg) {
		// TODO Auto-generated method stub
		return msgDAO.save(msg);
	}

	@Override
	public boolean deleteMessage(Message msg) {
		// TODO Auto-generated method stub
		return msgDAO.delete(msg);
	}
	
	@Override
	public boolean update(Message msg) {
		return msgDAO.update(msg);
	}

	@Override
	public List<Message> findByMac(String mac) {
		// TODO Auto-generated method stub
		return msgDAO.findByMac(mac);
	}
	
	@Override
	public List<Message> findNoShowByMac(String mac) {
		List<Message> msglist = msgDAO.findNoShowByMac(mac);
		return msglist;
	}
	
	@Override
	public boolean sendMessage(Message msg) {
		cn.com.vo.Message message = new cn.com.vo.Message(msg.getMac(),cn.com.vo.Message.MSG2,msg.getContent());
		boolean bool = services.send(message);
		if(bool == true){
			msg.setReadstate(this.READED);
			
			Timestamp time = new Timestamp(new Date().getTime());
			msg.setRecordtime(time);
			boolean b = this.saveMessage(msg);
			System.out.println("save:"+b);
			return b;
		}		
		return false;
	}

	public MessageDAO getMsgDAO() {
		return msgDAO;
	}

	public void setMsgDAO(MessageDAO msgDAO) {
		this.msgDAO = msgDAO;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public List<Message> findAll() {
		
		return msgDAO.findAll();
	}
	
}
