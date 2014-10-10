package com.netconnection.entity;

import java.sql.Timestamp;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */

public class Message implements java.io.Serializable {

	// Fields

	private Integer id;
	private String mac;
	private Integer type;
	private String content;
	private int readstate;//��ʾ����Ϣ�Ƿ�����ҳ����ʾ
	private Timestamp recordtime;
	private String clientname;

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** full constructor */
	public Message(String mac, Integer type, String content, int readstate,
			Timestamp recordtime,String clientname) {
		this.mac = mac;
		this.type = type;
		this.content = content;
		this.readstate = readstate;
		this.recordtime = recordtime;
		this.clientname = clientname;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getRecordtime() {
		return this.recordtime;
	}

	public void setRecordtime(Timestamp recordtime) {
		this.recordtime = recordtime;
	}

	public int getReadstate() {
		return readstate;
	}

	public void setReadstate(int readstate) {
		this.readstate = readstate;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	
	
}