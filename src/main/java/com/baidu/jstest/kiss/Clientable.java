package com.baidu.jstest.kiss;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 表示可在浏览器客户端运行的
 * 
 * @author dailiqi
 */
public interface Clientable {
	public void setRequest(ServletRequest request);

	public void setResponse(ServletResponse response);

	public ServletRequest getRequest();

	public ServletResponse getResponse();
}
