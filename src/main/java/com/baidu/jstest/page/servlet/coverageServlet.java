package com.baidu.jstest.page.servlet;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.page.ConfigurationFactory;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.XMLConstants;

import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * @author jiangshuguang
 */

public class coverageServlet extends AbstractServlet {

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
	   
//		response.getWriter().println("OK");
		Template template = ConfigurationFactory.getInstance().getTemplate("coverage_html.ftl");
		Writer out = response.getWriter();
		try {
			template.process(map, out);
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
