package com.baidu.jstest.page.handler;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.browser.BrowserHolder;
import com.baidu.jstest.browser.BrowserHolderFactory;
import com.baidu.jstest.browser.Notice;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.kiss.ClientKiss;
import com.baidu.jstest.page.AttrConst;
import com.baidu.jstest.page.ConfigurationFactory;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.XMLConstants;
import com.baidu.jstest.result.Result;
import com.baidu.jstest.task.Task;
import com.baidu.jstest.task.TaskQueue;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CaptureHandler extends AbstractHandler {
	Logger logger = LoggerFactory.getLogger(CaptureHandler.class);
	
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		response.addHeader("Cache-Control", "no-store");
		// response.setContentType("text/html;charset=utf-8");
		// 将server attr 转化到 Servlet request中
		TaskQueue queue = (TaskQueue) this.getServer().getAttribute(
				AttrConst.TASK_QUEUE);
		request.setAttribute(AttrConst.TASK_QUEUE, queue);
		this.getServer();		
		
		if ("/capture".equals(target)) {
			response.setContentType("text/html;charset=utf-8");	
			try {
				Template template = ConfigurationFactory.getInstance()
						.getTemplate("capture.ftl");
				Writer out = response.getWriter();
				try {
					template.process(null, out);
				} catch (TemplateException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((Request) baseRequest).setHandled(true);
		}
		if ("/probe".equals(target)) {
			response.setContentType("text/html;charset=utf-8");
			try {
				probe(target, baseRequest, request, response);
			} catch (TestException e) {
				e.printStackTrace();
			}
		}
		if ("/debug".equals(target)) {

			BrowserHolder holder = BrowserHolderFactory
					.getBrowserHolderInstance("chrome");
			System.out.println("debug");
		}						
	}

	
	public Result getResult(JSONObject json) throws TestException {
		Result result = null;
		try {
			if (json.has("endtime")) {
				result = new Result();
				result.setBrowser(json.getString("browser"));
				result.setFail(json.getInt("fail"));
				result.setTotal(json.getInt("total"));
				result.setName(json.getString("name"));
				result.setTimeStamp(json.getLong("endtime")
						- json.getLong("starttime"));
				if(json.has("cov") && json.getString("cov").startsWith("{") && json.getString("cov").endsWith("}")){
					result.setCov(json.getString("cov"));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new TestException("convert to result info error :"
					+ e.getMessage(), e);
		}

		return result;
	}
	
	
	public void probe(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws TestException {
		
		TaskQueue queue = (TaskQueue) this.getServer().getAttribute(
				AttrConst.TASK_QUEUE);	
			
		String browser = "";
		String interval = "";
		String id = "";
		JSONObject json = null;
		try {
			json = readJson(request);
			browser = json.getString("browser");
			interval = json.getString("interval");
			if (json.has("id"))
				id = json.getString("id");
		} catch (JSONException e) {
			e.printStackTrace();
			throw new TestException("convert to beat info error :"
					+ e.getMessage(), e);
		}
		
		Notice notice = new Notice(browser, Integer.parseInt(interval));
		Result result = getResult(json);
		if (null != result){
			notice.setResult(result);
		}
		BrowserHolder holder = BrowserHolderFactory
				.getBrowserHolderInstance(browser);
		// 如果当前的probe无ID，则添加一个ID，说明该浏览器是匿名浏览器
		try{
			if (!containValue(id)) {
				String browserId = holder.register(notice);
				response.getWriter().print("register_success_id=#" + browserId);
				baseRequest.setHandled(true);
				// 如果有Id，则对该浏览器发送通知
			} else {
				String url = "";
				notice.setBrowserId(id);
				// 下一个Kiss的名字
				String next = holder.notice(notice);
				if (null != next) {
					Task current = queue.current();
					if(null != current) {
						ClientKiss kiss = (ClientKiss) current.get(current.getIndex(next));
						url = kiss.runnableURI();
					}
				} 
				else{  
					
                     url="";
				}
				response.getWriter().print(url);
				baseRequest.setHandled(true);
			}
		} catch(Exception e) {
			throw new TestException(e);
		}
	}

	
	protected JSONObject readJson(HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = new JSONObject();	
		Map<String, String[]> parameterMap = request.getParameterMap();
		// 通过循环遍历的方式获得key和value并set到JSONObject中
		Iterator<String> paIter = parameterMap.keySet().iterator();
		while (paIter.hasNext()) {
			String key = paIter.next();
			String[] values = (String[]) parameterMap.get(key);

			Object value = null;
			if (values[0].startsWith("{")) {

				value = new JSONObject(values[0]);

			} else {
				value = values[0];
			}
			jsonObject.accumulate(key, value);
		}
		logger.debug("从客户端获得json=" + jsonObject.toString());
		return jsonObject;
	}

	private static boolean containValue(String s) {
		return null != s && !"null".equals(s) && !"".equals(s)
				&& !"UNDEFINED".equals(s.toUpperCase());
	}
}


  
  
