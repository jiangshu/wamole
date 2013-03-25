package com.baidu.jstest.page.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.kiss.ReleaseKiss;
import com.baidu.jstest.page.ConfigurationFactory;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.result.coverage.CoverageKiss;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FrameServlet extends AbstractServlet {
	/** ID */
	private static final long serialVersionUID = -3786603584187187445L;
	Logger logger = LoggerFactory.getLogger(FrameServlet.class);

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String caseName = request.getParameter("case");
		String release = request.getParameter("release");
		String cov = request.getParameter("cov");
		response.setContentType("text/html;charset=utf-8");
		Project project = (Project) request.getAttribute("project");
		Template template = null;
		try {
			template = ConfigurationFactory.getInstance().getTemplate("frame.ftl");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (null != caseName && !"".equals(caseName)) {
			logger.debug("frame case = " + caseName + ".");
			// 构建kiss
			Kiss kiss = project.getKiss(caseName);
			if (null != release && null != kiss
					&& Boolean.parseBoolean(release)) {
				logger.debug("frame case release mode");
				kiss = new ReleaseKiss(kiss);
			} else if (null != cov && null != kiss && Boolean.parseBoolean(cov)) {
				logger.debug("frame case coverage mode");
				kiss = new CoverageKiss(kiss);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("commons", kiss.getCommonResource());
			map.put("kiss", kiss);
			map.put("resources", kiss.getResource());
			// 如果有Kiss得情况
			try {
				logger.debug("do frame page, name:" + kiss.getName());
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
}
