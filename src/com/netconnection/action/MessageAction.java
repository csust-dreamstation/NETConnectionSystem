package com.netconnection.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.netconnection.entity.Message;
import com.netconnection.service.IMessageService;
import com.opensymphony.xwork2.ActionSupport;

//import cn.com.server.Services;
//import cn.com.vo.Message;

public class MessageAction extends ActionSupport{
	private IMessageService messageService;
	private String mac;
	private String macList;
	private String msgContent;
	private String retMsg;
	private List<String> retMsgList;
	private JSONObject resultObj;
	private List<Message> msgList;
	public String sendMsg(){
		System.out.println("nininininnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		Message message = new Message();
		message.setContent(msgContent);
		message.setMac(mac);
		message.setType(IMessageService.MESSAGE_TO_CLIENT);
		boolean bool = messageService.sendMessage(message);
		if(bool == true){
			retMsg = "success";			
		}else{
			retMsg = "fail";
		}
		System.out.println("8888888888888888888888retMsg:"+retMsg);
		return SUCCESS;	
	}
	
	public String sendMsgToSome(){
		String[] macl = parseList(macList);
		
		for(int i = 0;i<macl.length;i++){
			Message message = new Message();
			message.setContent(msgContent);
			message.setMac(macl[i]);
			message.setType(IMessageService.MESSAGE_TO_CLIENT);
			boolean bool = messageService.sendMessage(message);
			if(bool == true){
				retMsg = "success";			
			}else{
				retMsg = "fail";
			}				
		}
		return "success";	
	}
	
		private String[] parseList(String s){
			String str = s.substring(1, s.length()-1);
			str = str.replace("\"", "");
			String[] strl = str.split(",");
			return strl;
		}
	
	public String getMsg(){
		System.out.println("mac:"+mac);
		 msgList = messageService.findNoShowByMac(mac); 
		//�Ѷ������Ϣ״̬�ı�
		for(Message m:msgList){
			m.setReadstate(IMessageService.READED);
			if(messageService.update(m) == false){
				throw new RuntimeException("�޸���ʾ״̬ʧ�ܣ�");
			}
		}
		return SUCCESS;
	}
	
	public String getSomeMsg(){
		String[] macl = parseList(macList);
		
		for(int i = 0;i<macl.length;i++){
			if( i == 0){
				msgList = messageService.findNoShowByMac(macl[i]); 				
			}else{
				msgList.addAll(messageService.findNoShowByMac(macl[i]));
			}
			
		}
		//�Ѷ������Ϣ״̬�ı�
		for(Message m:msgList){
			m.setReadstate(IMessageService.READED);
			if(messageService.update(m) == false){
				throw new RuntimeException("�޸���ʾ״̬ʧ�ܣ�");
			}
		}
		return SUCCESS;
	}

	public JSONObject getResultObj() {
		return resultObj;
	}

	public void setResultObj(JSONObject resultObj) {
		this.resultObj = resultObj;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public List<Message> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<Message> msgList) {
		this.msgList = msgList;
	}

	public String getMacList() {
		return macList;
	}

	public void setMacList(String macList) {
		this.macList = macList;
	}
	
	
}
