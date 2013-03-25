package com.baidu.jstest.page.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.page.ConfigurationFactory;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.Suite;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ListServlet extends AbstractServlet {

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Project project = (Project) request.getAttribute("project");
		String filter = request.getParameter("filter");
//		if (null != filter && !"".equals(filter)) {
//			suite = project.getChild(filter);
//		} else {
//			suite = project;
//		}
		// 填充数据类型
		Map<String, Object> map = new HashMap<String, Object>();
		Template template = ConfigurationFactory.getInstance().getTemplate("list.ftl");
		response.setContentType("text/html; charset=" + template.getEncoding());
		map.put("project", project.getName());
		if (null != filter) {
			map.put("filter", filter);
		}
		List<Kiss> kisses = new ArrayList<Kiss>();
		List<Kiss> noKisses = new ArrayList<Kiss>();
		converse(kisses, noKisses, project);
		if (kisses.size() > 0) {
			map.put("kisses", kisses);
		}
		if (noKisses.size() > 0) {
			map.put("noKisses", noKisses);
		}
		Writer out = response.getWriter();
		try {
			template.process(map, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	private void converse(List<Kiss> kisses, List<Kiss> noKisses, Suite suite) {
		if (suite instanceof Kiss) {
			if (!((Kiss) suite).haveCase()) {
				noKisses.add((Kiss) suite);
			} else {
				kisses.add((Kiss) suite);
			}
		} else {
			List<Suite> list = suite.child();
			for (int i = 0; i < list.size(); i++) {
				converse(kisses, noKisses, list.get(i));
			}
		}
	}

}
