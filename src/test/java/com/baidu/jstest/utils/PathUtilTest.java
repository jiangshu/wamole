package com.baidu.jstest.utils;

import org.junit.Assert;
import org.junit.Test;

import com.baidu.jstest.exception.ParseException;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.utils.PathUtil;

public class PathUtilTest {

	@Test
	public void converse_1() {
		Project project = new Project();
		project.setProperties("23", "c:/test");
		try {
			Assert.assertEquals("c:/test/src",
					PathUtil.converse(project, "${23}/src"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
