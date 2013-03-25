package com.baidu.jstest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.browser.BrowserHolder;
import com.baidu.jstest.browser.state.TimeOutThread;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.page.AttrConst;
import com.baidu.jstest.page.handler.CaptureHandler;
import com.baidu.jstest.page.handler.FileHandler;
import com.baidu.jstest.page.handler.IndexHandler;
import com.baidu.jstest.page.handler.RestartHandler;
import com.baidu.jstest.page.handler.TaskHandler;
import com.baidu.jstest.page.handler.WebAppContextFactory;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.ProjectParser;
import com.baidu.jstest.project.Projects;
import com.baidu.jstest.project.ProjectsHelper;
import com.baidu.jstest.task.TaskQueue;


import com.baidu.jstest.browser.invoker.STAFInvoker;

public class Main {
	Logger logger = LoggerFactory.getLogger(Main.class);
	private WebAppContext context;
	public static Projects projects;
	private static Main instance = new Main();
	private static Server server;
	private HandlerCollection handlers;
	private static int SERVER_PORT;
	private static HandlerCollection handlerCollection = new HandlerCollection();
	private static boolean multiload = true;
	private static boolean autorun;
	private static String DEFINE_XML;

	private Main() {
		projects = new Projects();
		handlers = new HandlerCollection();
	}

	public static int getServerPort() {
		if (SERVER_PORT == 0)
			SERVER_PORT = 8080;
		return SERVER_PORT;
	}

	public static String getDefineXML() {
		if (null == DEFINE_XML || "".equals(DEFINE_XML))
			DEFINE_XML = System.getProperty("user.dir") + "/define.xml";
		return DEFINE_XML;
	}

	public static Main getInstance() {
		if (instance == null)
			return new Main();
		else
			return instance;
	}

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equals("--help") || arg.equals("-h")) {
				printUsage();
				return;
			} else if (arg.equals("--port") || arg.equals("-p")) {
				try {
					SERVER_PORT = Integer.parseInt(args[i + 1]);
					i++;
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					String msg = "You must specify a port when "
							+ "using the --port,-p argument,default port is 80";
					System.out.println(msg);
					return;
				}
			} else if (arg.equals("--auto") || arg.equals("-a")) {
				autorun = true;
			} else if (arg.equals("--unload") || arg.equals("-u")) {
				multiload = false;
			} else if (arg.equals("--configfile") || arg.equals("-c")
					|| arg.equals("-f")) {
				try {
					DEFINE_XML = args[i + 1];
					i++;
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					String msg = "You must specify a config file when "
							+ "using the --configfile,-f,-c argument";
					System.out.println(msg);
					return;
				}
			}
		}		
		Main shell = Main.getInstance();
		shell.start();
		
//		STAFInvoker test = new STAFInvoker();
//		test.invoketest();
	 
	}

	/**
	 * 根据XML配置项目
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void configProjects(String path) throws Exception {
		configProjects(new File(path));
	}

	/**
	 * 根据XML配置项目
	 * 
	 * @param file
	 * @throws Exception
	 */
	public void configProjects(File file) throws Exception {
		ProjectsHelper.configureProjects(file);
	}

	/**
	 * 根据项目配置Web环境
	 * 
	 * @param project
	 */
	public void configContext(Project project) {
		context = WebAppContextFactory.getInstance(project);
		server.setAttribute("currentProj", project);
		handlers.addHandler(context);
	}

	public void initServletHandlers() {
		server.setAttribute("projects", projects);
		handlers.addHandler(new FileHandler());
		handlers.addHandler(new CaptureHandler());
		handlers.addHandler(new RestartHandler());
		handlers.addHandler(new IndexHandler());
		handlers.addHandler(new TaskHandler());
	}

	/**
	 * 载入资源
	 */
	public void resourceContext() {
		// jar写法
		WebAppContext common = new WebAppContext(Thread.currentThread()
				.getClass().getResource("/resource/").toString(), "/resource");
		common.setDefaultsDescriptor(Thread.currentThread().getClass()
				.getResource("/resource/jetty/webdefault.xml").toString());
		handlerCollection.addHandler(common);
		// handlers.addHandler(common);
	}

	public void configContexts() {
		for (String key : projects.keySet()) {
			Project project = projects.getEntity(key);
			WebAppContext context = WebAppContextFactory.getInstance(project);
			handlerCollection.addHandler(context);
		}
	}

	private static void printUsage() {
		String lSep = System.getProperty("line.separator");
		StringBuffer msg = new StringBuffer();
		msg.append("java -jar JSTA.jar [options] [target]" + lSep);
		msg.append("Options: " + lSep);
		msg.append("  --help,-h                 print this message" + lSep);
		msg.append("  --port, -p                specify a port to use" + lSep);
		msg.append("  --auto,-a                 auto run all the project kiss");
		msg.append("                            the diff browsers" + lSep);
		// msg.append("  --output,-o               specify a output file to save the result xml,");
		// msg.append("                            default is jar file" + lSep);
		msg.append("  -configfile,-c,-f <file>  use given buildfile" + lSep);

		System.out.println(msg.toString());
	}

	/**
	 * 开始
	 * 
	 * @param port
	 * @throws Exception
	 */
	public void start() throws Exception {
		// set default port 80
		logger.info("server start. port is " + SERVER_PORT);
		server = new Server(getServerPort());
		QueuedThreadPool pool = new QueuedThreadPool();
		pool.setMinThreads(10);
		pool.setMaxThreads(1000);
		configProjects(getDefineXML());
		initServletHandlers();
		resourceContext();
		configContexts();
		createTaskQueue();
		server.setThreadPool(pool);
		handlers.addHandler(handlerCollection);
		server.setHandler(handlers);
		server.start();
		server.join();
	}

	/**
	 * 默认新建一个任务序列池
	 * 
	 * @throws TestException
	 */
	private void createTaskQueue() throws TestException {
		TaskQueue queue = TaskQueue.getInstace();
		// 解析当前所有project
		for (String key : projects.keySet()) {
			Project project = projects.getEntity(key);
			try {
				ProjectParser.parse(project);
//				CoverageManager.resetCoverage(project);
			} catch (TestException e1) {
				e1.printStackTrace();
			}
		}
		// 如果是自动执行 唤醒浏览器
		if (autorun) {
			List<BrowserHolder> list = projects.getEnv().getBrowserHolders();
			for (int i = 0; i < list.size(); i++) {
				list.get(i).invoke();
			}
		}
		// 启用浏览器状态监视
		Thread thread = new TimeOutThread(projects);
		thread.start();
		server.setAttribute(AttrConst.TASK_QUEUE, queue);
	}

	/**
	 * 针对初始化时未添加的Context动态添加
	 * @throws Exception 
	 */
	public static void addExtContext(Project project) throws Exception {
//		Handler[] hs = handlerCollection.getHandlers();
//		for (int i = 0; i < hs.length; i++) {
//			WebAppContext handler = (WebAppContext) hs[i];
//			String contextPath = handler.getContextPath();
//			String path = "/" + project.getName();
//			if (path.equals(contextPath)) {
//				return;
//			}
//		}
		server.stop();
		WebAppContext context = WebAppContextFactory.getInstance(project);
		handlerCollection.addHandler(context);
		server.setHandler(handlerCollection);
		server.start();
		server.join();
		try {
			ProjectParser.parse(project);
//			CoverageManager.resetCoverage(project);
		} catch (TestException e1) {
			e1.printStackTrace();
		}
	}
}
