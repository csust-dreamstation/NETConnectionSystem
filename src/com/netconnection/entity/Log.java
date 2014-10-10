package com.netconnection.entity;

/**
 * Log entity. @author MyEclipse Persistence Tools
 */

public class Log implements java.io.Serializable {

	// Fields

	private Integer id;
	private String operationername;
	private String operation;
	private String content;
	private String time;

	// Constructors

	/** default constructor */
	public Log() {
	}

	/** full constructor */
	public Log(String operationername, String operation, String content,
			String time) {
		this.operationername = operationername;
		this.operation = operation;
		this.content = content;
		this.time = time;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperationername() {
		return this.operationername;
	}

	public void setOperationername(String operationername) {
		this.operationername = operationername;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}