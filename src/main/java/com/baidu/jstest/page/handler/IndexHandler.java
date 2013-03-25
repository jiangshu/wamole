package com.baidu.jstest.page.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.page.AttrConst;
import com.baidu.jstest.project.Projects;

public class IndexHandler extends AbstractHandler {
	Logger logger = LoggerFactory.getLogger(IndexHandler.class);
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		baseRequest.setQueryEncoding("UTF-8");
		Projects projects = (Projects) this.getServer().getAttribute(
				AttrConst.PROJECTS_NAME);
//		logger.info("Index handler --- target: " + target);
		target = target.substring(1);
		target = target.indexOf("/") > 0 ? target.substring(0,
				target.indexOf("/")) : target;
		request.setAttribute("projects", projects);
		
		request.setAttribute("project", projects.getEntity(target));
		if (target.equals("/") || target.equals("/index.html")
				|| target.equals("/index.do") || target.equals("/index")) {
			// 首页
			response.setContentType("text/html;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			((Request) baseRequest).setHandled(true);
			//TODO IndexPage
			response.getWriter().println(
					);
		}
	}

}
