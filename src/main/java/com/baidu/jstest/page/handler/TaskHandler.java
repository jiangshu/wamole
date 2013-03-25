package com.baidu.jstest.page.handler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.Main;
import com.baidu.jstest.browser.BrowserHolder;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.page.AttrConst;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.ProjectParser;
import com.baidu.jstest.project.Projects;
import com.baidu.jstest.project.ProjectsHelper;
import com.baidu.jstest.project.Suite;
import com.baidu.jstest.project.XMLConstants;
import com.baidu.jstest.result.coverage.CopyFile;
import com.baidu.jstest.result.coverage.CoverageManager;
import com.baidu.jstest.task.Task;
import com.baidu.jstest.task.TaskAdapter;
import com.baidu.jstest.task.TaskQueue;
import java.io.File;
import com.baidu.jstest.result.coverage.SourceCodeTransiton;
/**
 * @author dailiqi
 */
public class TaskHandler extends AbstractHandler {

	Logger logger = LoggerFactory.getLogger(TaskHandler.class);

	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 将server attr 转化到 Servlet request中
		TaskQueue queue = (TaskQueue) this.getServer().getAttribute(
				AttrConst.TASK_QUEUE);
		this.getServer();
		
		if ("/addTask".equals(target)) {
			String suiteName = request.getParameter("suite");// 测试组件
			String proj = request.getParameter("proj");// 测试项目
			String release = request.getParameter("release");// 是否是release
			String cov = request.getParameter("cov");// 是否是覆盖率
//			String cove = request.getParameter("cove");//是否进行覆盖率统计（与cov意义一致，处理方式不同）
			String browser = request.getParameter("browser");// 是否指定浏览器
			logger.info("addTask, suite:" + suiteName + ",project :" + proj
					+ ",isCov : " + cov + ",is release : " + release + ".");
			Projects projects = (Projects) this.getServer().getAttribute(
					AttrConst.PROJECTS_NAME);
			Project project = projects.getEntity(proj);
			
			try {
				ProjectParser.parse(project);
			} catch (TestException e1) {
				e1.printStackTrace();
			}
			
//			ProjectsHelper.configureProjects(projects, Main.getDefineXML());
			
			// 判断是否是覆盖率测试
			boolean bcov = false;
			if (containValue(cov)) {
				bcov = Boolean.parseBoolean(cov);
			}
			
			if(bcov){
//				try{
//				  CoverageManager.resetCoverage(project);
//				}catch(TestException e1){
//					e1.printStackTrace();
//				}
				//解决tangram的编码问题
				String covPath=project.getProperties().getProperty(XMLConstants.COVERAGE_DIR);
				String srcPath=project.getProperties().getProperty(XMLConstants.SRC_DIR);
				covPath=covPath+"/baidu/url/escapeSymbol.js";
				srcPath=srcPath+"/baidu/url/escapeSymbol.js";	
				if((new File(covPath).exists())){
					CopyFile.excutor(srcPath,covPath);	
				}
											
//				if((new File(Path1).exists())){
//					String sor1 = "return String(source).replace(/[#%&+=\\/\\\\\\ \\\\u3000\\f\\r\\n\\t]/g, (function (all) {";
//					String des1=  "return String(source).replace(/[#%&+=\\/\\\\\\ \\　\\f\\r\\n\\t]/g, (function(all) {";
//					SourceCodeTransiton.codeTransition(Path1, sor1, des1);
//				}
//				
//				String Path2=covPath+"/baidu/url/escapeSymbol.js";
//				if((new File(Path1).exists())){
//					String sor1 = "return (\"%\" + (256 + all.charCodeAt()).toString(16).substring(1).toUpperCase());";
//					String des1= "return '%' + (0x100 + all.charCodeAt()).toString(16).substring(1).toUpperCase();";
//					SourceCodeTransiton.codeTransition(Path2, sor1, des1);
//				}
			}
			
			
			if (null != project.getEnv()) {
				List<BrowserHolder> list = project.getEnv().getBrowserHolders();
				for (BrowserHolder browserHolder : list) {
					try {
					
						browserHolder.invoke();
					} catch (TestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Suite suite = null;
				if (null != suiteName && !"".equals(suiteName)) {
					suite = project.getChild(suiteName);
				} else {
					suite = project;
				}
				// 判断是否是release
				boolean brelease = false;
				if (containValue(release)) {
					brelease = Boolean.parseBoolean(release);
				}
                  
				List<String> browserList = new ArrayList<String>();
				// 判断浏览器类型
				if (containValue(browser)) {
					browserList.add(browser);
				} else {
					List<BrowserHolder> bhlist = project.getEnv()
							.getBrowserHolders();
					for (BrowserHolder browserHolder : bhlist) {
						browserList.add(browserHolder.getName());
					}
				}
				Task task = new TaskAdapter(suite, brelease, bcov,browserList);
				queue.addTask(task);
				// 注册browser
//				task.registerBrowser();
			}
			baseRequest.setHandled(true);
		}
	}

	private boolean containValue(String s) {
		return null != s && !"".equals(s)
				&& !"UNDEFINED".equals(s.toUpperCase());
	}
}
