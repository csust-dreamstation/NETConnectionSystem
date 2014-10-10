package com.netconnection.exception;

public class NoFindUserByNameException extends Exception{
	private static final long serialVersionUID = 1L;

	public NoFindUserByNameException(String msg){
		super(msg);
	}

}
