package com.baidu.jstest;

import org.junit.Before;
import org.junit.Test;

import com.baidu.jstest.project.DefaultSuite;
import com.baidu.jstest.project.Project;

public class DefaultSuiteTest {

	DefaultSuite suite;

	@Before
	public void init() {
		suite = new DefaultSuite("baidu.array",
				"F:\\Workplaces\\Workplace\\tangram\\src\\baidu\\ajax",
				"F:\\Workplaces\\Workplace\\tangram\\testsrc\\baidu\\ajax");
		Project project = new Project();
		project.setSrcdir("F:\\Workplaces\\Workplace\\tangram\\src");
		project.setTestdir("F:\\Workplaces\\Workplace\\tangram\\testsrc");
		suite.setProject(project);
	}

	@Test
	public void getChildren_01() {
		suite.child();
	}
}
