package com.baidu.jstest.project;

public class SuiteFactory {
	
	public static Suite getSuite(String name, String src, String testsrc) {
		return new DefaultSuite(name,src,testsrc);
	}
}
