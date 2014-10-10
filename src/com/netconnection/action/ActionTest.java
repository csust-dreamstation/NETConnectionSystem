package com.netconnection.action;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ActionTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext cxt = new ClassPathXmlApplicationContext("application*.xml");
		UserAction user = (UserAction)cxt.getBean("userAction");
		user.updateUser();
	}

}
