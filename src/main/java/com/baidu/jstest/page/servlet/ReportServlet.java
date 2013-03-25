package com.baidu.jstest.page.servlet;

import java.io.File;
import java.io.IOException;

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

import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.XMLConstants;

/**
 * @author dailiqi
 */
public class ReportServlet extends AbstractServlet {

	/** */
	private static final long serialVersionUID = -3201157152247201709L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Project project = (Project) request.getAttribute("project");
		String savePath = project.getProject().getProperties()
				.getProperty(XMLConstants.REPORT_DIR)
				+ "/report.xml";
		TransformerFactory tFac = TransformerFactory.newInstance();

		Source xslSource = new StreamSource(Thread
				.currentThread()
				.getClass()
				.getResourceAsStream(
						"/com/baidu/jstest/page/ftl/junit-noframes.xsl"));
		try {
			Transformer t = tFac.newTransformer(xslSource);
			File xmlFile = new File(savePath);
			// File htmlFile = new File(Thread
			// .currentThread()
			// .getClass()
			// .getResource(
			// "/com/baidu/jstest/page/ftl/report.html").toString());
			Source source = new StreamSource(xmlFile);
			// response.getWriter()
			Result result = new StreamResult(response.getWriter());

			t.transform(source, result);
			System.out.print(result.toString());
			// response.getWriter().
			// Result result = new StreamResult(htmlFile);
			// System.out.println(result.toString());
			// t.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
