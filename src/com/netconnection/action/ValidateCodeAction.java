package com.netconnection.action;

import java.io.ByteArrayInputStream;

import com.netconnection.util.ValidateCode;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ValidateCodeAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private ValidateCode validateCode;
	private ByteArrayInputStream imageStream;
	
	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}
	public void setImageStream(ByteArrayInputStream imageStream) {
		this.imageStream = imageStream;
	}
	public ValidateCode getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(ValidateCode validateCode) {
		this.validateCode = validateCode;
	}

	public String validateImage() throws Exception {
		this.setImageStream(validateCode.getRandcode());
		ActionContext.getContext().getSession().put("sValidateCode", validateCode.getValidateCode()); 
		return SUCCESS;
	}
	
}
