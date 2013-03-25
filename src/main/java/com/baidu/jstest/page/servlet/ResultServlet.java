package com.baidu.jstest.page.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.project.Project;

/**
 * @author dailiqi
 */
public class ResultServlet extends HttpServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("result do");
		// 监听结果
		Project project = (Project) request.getAttribute("project");
		String kiss = request.getParameter("kiss");
		String total = request.getParameter("total");
		String fail = request.getParameter("fail");
		String group = request.getParameter("group");
		
		// 内部触发 可能会有问题
		if (null != kiss) {
//			try {
//				if (null == group || "".equals(group))
//					group = UUID.randomUUID().toString();
////				Result result = new TestResult(kiss, fail, total);
////				result.setGroup(group);
////				ResultPool.getInstance().addResult(result);
//			} catch (TestException e) {
//				e.printStackTrace();
//			}
			Kiss kissObj = (Kiss) project.getKiss(kiss);
//			if (kissObj.hasNext()) {
//				response.getWriter().print(
//						"run.do?case=" + kissObj.next().getName() + "&group="
//								+ group);
//			} else {
//				
//				response.getWriter().print("report.do?group=" + group);
////				
////				List<Result> list  = ResultPool.getInstance().getResultsByGroup(group);
////				for (Result result : list) {
////					System.out.println("executed result ~~~~  " + result.toString());
////				}
////				System.out.println();
//			}
		}
	}
}
