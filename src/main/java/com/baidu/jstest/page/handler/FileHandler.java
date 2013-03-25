package com.baidu.jstest.page.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class FileHandler extends AbstractHandler {

	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(target.lastIndexOf(".") > 0) {
			String end = target.toLowerCase().substring(target.lastIndexOf(".") + 1);
			if (end.equals("js")) {
				response.setContentType("text/javascript");
			} else if (end.equals("css")) {
				response.setContentType("text/css");
			} else if (end.equals("gif")
					|| end.equals("jpg")
					|| end.equals("bmp")
					|| end.equals("jepg")) {
				response.setContentType("image/" + end);
			} else if (end.equals("xml")) {
				response.setContentType("application/xml");
			} else if (end.endsWith("txt")) {
				response.setContentType("text/plain");
			} else if (end.endsWith("swf")) {
				response.setContentType("application/x-shockwave-flash");
			}
		}
		
	}
}
