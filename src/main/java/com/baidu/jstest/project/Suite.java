package com.baidu.jstest.project;

import java.util.List;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.kiss.Kiss;

/**
 * 可测试单元模块
 * 
 * @author dailiqi
 */
public interface Suite {
//	public void test(HttpServletRequest request,
//			HttpServletResponse response) throws TestException;

	public String getName();

	public Project getProject();

	public void setProject(Project project);

	public void addChild(Suite suite) throws TestException;

	public List<Suite> child();

	public Suite getChild(String name);

	public Kiss getKiss(String name);
}
