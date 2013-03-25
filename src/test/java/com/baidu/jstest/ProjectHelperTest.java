package com.baidu.jstest;

import org.junit.Test;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.ProjectParser;

public class ProjectHelperTest {

	@Test
	public void test() {
		Project project = new Project();
		project.setBasedir("F:/Workplaces/Workplace/tangram");
		project.setSrcdir("F:/Workplaces/Workplace/tangram/src");
		project.setTestdir("F:/Workplaces/Workplace/tangram/test");
		try {
			ProjectParser.parse(project);
		} catch (TestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(project);
	}
}
