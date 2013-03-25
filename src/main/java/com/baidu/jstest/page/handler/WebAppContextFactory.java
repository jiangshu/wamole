package com.baidu.jstest.page.handler;

import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import com.baidu.jstest.page.servlet.FrameServlet;
import com.baidu.jstest.page.servlet.ListServlet;
import com.baidu.jstest.page.servlet.ReportServlet;
import com.baidu.jstest.page.servlet.RunServlet;
import com.baidu.jstest.page.servlet.coverageServlet;
import com.baidu.jstest.project.Project;
import com.caucho.quercus.servlet.QuercusServlet;

public class WebAppContextFactory {

	/**
	 * 获取Web应用上下文
	 * 
	 * @param project
	 * @return
	 */
	public static WebAppContext getInstance(Project project) {
		WebAppContext context = null;
		if (null == project) {
			// 建立默认Web上下文
			//context = new WebAppContext(".", "/");
			// 如果和当前一致
		} else {
			context = new WebAppContext(project.getBasedir(), "/"
					+ project.getName());
		}
		// jar 
		context.setDefaultsDescriptor(Thread.currentThread().getClass()
				.getResource("/resource/jetty/webdefault.xml").toString());
		//调试
//		context.setDefaultsDescriptor("./resource/jetty/webdefault.xml");
		context.addServlet(RunServlet.class , "/run.do");
		context.addServlet(FrameServlet.class , "/frame.do");
		context.addServlet(ListServlet.class , "/list.do");
		context.addServlet(ReportServlet.class , "/report.do");
		context.addServlet(coverageServlet.class, "/coverage.do");
		ServletHolder holder = new ServletHolder(QuercusServlet.class);
		holder.setInitOrder(0);
		context.addServlet(holder, "*.php");
		return context;
	}
}
