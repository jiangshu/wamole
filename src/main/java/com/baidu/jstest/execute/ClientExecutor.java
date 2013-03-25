package com.baidu.jstest.execute;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.kiss.ClientKiss;
import com.baidu.jstest.kiss.Clientable;
import com.baidu.jstest.page.ConfigurationFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ClientExecutor implements Executor<ClientKiss>, Clientable {
	Logger logger = LoggerFactory.getLogger(ClientExecutor.class);
	private static ClientExecutor instance = new ClientExecutor();

	private ServletRequest request;
	private ServletResponse response;

	public static ClientExecutor getInstance() {
		if (instance == null)
			instance = new ClientExecutor();
		return instance;
	}

	@Override
	public synchronized void execute(ClientKiss kiss) throws TestException {
		this.request = kiss.getRequest();
		this.response = kiss.getResponse();
		Map<String, Object> map = new HashMap<String, Object>();
		Template template = null;
		try {
			template = ConfigurationFactory.getInstance()
					.getTemplate("run.ftl");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		map.put("kiss", kiss);
		map.put("commons", kiss.getCommonResource());
		map.put("resources", kiss.getResource());
		map.put("tests", kiss.getTestResource());
				
		// 如果有Kiss得情况
		if (kiss.haveCase()) {
			try {
				logger.debug("Kiss is execute, name:" + kiss.getName());
				Writer out = response.getWriter();
				try {
					template.process(map, out);
				} catch (TemplateException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public ServletResponse getResponse() {
		return response;
	}

	public void setResponse(ServletResponse response) {
		this.response = response;
	}
}
