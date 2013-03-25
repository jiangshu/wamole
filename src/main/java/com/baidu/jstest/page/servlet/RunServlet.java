package com.baidu.jstest.page.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.kiss.ClientKiss;
import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.kiss.ReleaseKiss;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.ProjectParser;
import com.baidu.jstest.result.coverage.CoverageKiss;

public class RunServlet extends HttpServlet {
	/** ID */
	private static final long serialVersionUID = -3786603584187187445L;
	Logger logger = LoggerFactory.getLogger(RunServlet.class);

	@Override
	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String caseName = request.getParameter("case");
//		response.getHeader(caseName);
//		System.out.println(request.toString());
		String release = request.getParameter("release");
		String cov = request.getParameter("cov");
		response.setContentType("text/html;charset=utf-8");
		Project project = (Project) request.getAttribute("project");		
		
		if (null != caseName && !"".equals(caseName)) {
			logger.debug("running case = " + caseName + ".");
			ClientKiss kiss = (ClientKiss)project.getKiss(caseName);
			if(null == kiss) {
				try {
					//如果无当前用例，则尝试重新解析项目
					logger.info("try re-parse the project");
					ProjectParser.parse(project);
				} catch (TestException e) {
					e.printStackTrace();
				}
				kiss = (ClientKiss)project.getKiss(caseName);
			}
			if (null != release && null != kiss
					&& Boolean.parseBoolean(release)) {
				logger.debug("running case release mode");
				kiss = new ReleaseKiss(kiss);
			} else if (null != cov && null != kiss
					&& Boolean.parseBoolean(cov)) {
				logger.debug("running case coverage mode");
				kiss = new CoverageKiss(kiss);
			}
			logger.debug("project base dir  = " + project.getBasedir());
			if (null != kiss) {
				try {
					kiss.setRequest(request);
					kiss.setResponse(response);
					kiss.test();
				} catch (TestException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
