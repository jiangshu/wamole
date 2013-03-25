package com.baidu.jstest.kiss;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



public abstract class ClientKiss implements Kiss,Clientable {

	protected ServletRequest request;
	protected ServletResponse response;

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public void setResponse(ServletResponse response) {
		this.response = response;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public ServletResponse getResponse() {
		return response;
	}

	public abstract String runnableURI();
}
