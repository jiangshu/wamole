package com.baidu.jstest;

import org.junit.Test;

import com.baidu.jstest.kiss.DefaultKiss;
import com.baidu.jstest.kiss.Kiss;

public class DefaultKissTest {

	@Test
	public void test_getTestResource_01() {
		String name = "baidu.ui.Table.Table$title";
		String src = "";
		String testsrc = "";
		Kiss kiss = new DefaultKiss(name, src, testsrc);
		
		
		
	}
}
