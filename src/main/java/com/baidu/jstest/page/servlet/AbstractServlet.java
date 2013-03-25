package com.baidu.jstest.page.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;

public abstract class AbstractServlet extends HttpServlet {
	/**  */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
	}

	public abstract void service(HttpServletRequest request,
			HttpServletResponse response) throws IOException;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}
}
