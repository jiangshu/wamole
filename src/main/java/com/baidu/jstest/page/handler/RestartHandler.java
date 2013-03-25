package com.baidu.jstest.page.handler;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.WebAppContext;

import com.baidu.jstest.Main;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.project.ProjectParser;
import com.baidu.jstest.project.Projects;
import com.baidu.jstest.project.ProjectsHelper;
import com.baidu.jstest.result.coverage.CoverageManager;
import com.baidu.jstest.task.TaskQueue;

public class RestartHandler extends AbstractHandler {
	private Server jetty;

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if ("/restart".equals(target)) {
			//好像会有问题，当当前有任务在跑时
			jetty = this.getServer();
			TaskQueue queue = TaskQueue.getInstace();
			System.out.println("'queue'" + queue);
			if(queue.isEmpty()) {
				restart();
				response.getWriter().write("restart success");
			} else{
				response.getWriter().append("task queue is not empty ,can't restart the server!");
			}
			((Request) baseRequest).setHandled(true);
		}
	}

	private void restart() {
		if (jetty.isRunning()) {
			synchronized (jetty) {
				new Thread() {
					public void run() {
						try {
							jetty.stop();
							HandlerCollection handler = (HandlerCollection) jetty
									.getHandler();
							handler.removeHandler(handler
									.getChildHandlerByClass(HandlerCollection.class));
							HandlerCollection collection = new HandlerCollection();
							handler.addHandler(collection);
							Main.projects = new Projects();
							try {
								ProjectsHelper.configureProjects(
										new File(Main.getDefineXML()));
							} catch (TestException e) {
								e.printStackTrace();
							}
							WebAppContext common = new WebAppContext(Thread
									.currentThread().getClass()
									.getResource("/resource/").toString(),
									"/resource");
							common.setDefaultsDescriptor(Thread
									.currentThread()
									.getClass()
									.getResource(
											"/resource/jetty/webdefault.xml")
									.toString());
							collection.addHandler(common);
							// 配置project
							Set<String> keys = Main.projects.keySet();

							for (Iterator<String> iterator = keys.iterator(); iterator
									.hasNext();) {
								String string = iterator.next();
								WebAppContext context = WebAppContextFactory
										.getInstance(Main.projects.getEntity(string));
								collection.addHandler(context);
								try {
									ProjectParser.parse(Main.projects
											.getEntity(string));
									CoverageManager.resetCoverage(Main.projects
											.getEntity(string));
								} catch (TestException e1) {
									e1.printStackTrace();
								}
							}
							jetty.setHandler(handler);
							jetty.start();
							jetty.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		}
	}
}
