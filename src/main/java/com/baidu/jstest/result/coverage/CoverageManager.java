package com.baidu.jstest.result.coverage;

import java.io.File;
import java.io.IOException;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.XMLConstants;

public class CoverageManager {

	public static void resetCoverage(Project project) throws TestException {
		String coverage = project.getProperties().getProperty(
				XMLConstants.COVERAGE_DIR);
		if (null != coverage) {
			File cov_file = new File(coverage);
			if(cov_file.exists()) {
				cov_file.delete();
			}
			geneCoverage(project,coverage);
		}
	}
	
	private static void geneCoverage(Project project,String coverage) throws TestException {
		StringBuffer sb = new StringBuffer();
		String path = project.getTestdir() + "/tools/coverage/jscoverage.exe";
		//sb.append("resource/jscoverage.exe");
		//jar
		sb.append(path);
		sb.append(" ");
		sb.append("--encoding=UTF-8");
		sb.append(" ");
		sb.append(project.getSrcdir());
		sb.append(" ");
		sb.append(coverage);
		System.out.println(sb.toString());
		try {
			Process proc = Runtime.getRuntime().exec(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new TestException("coverage file generate fail!");
		} 
		String s=  "jscoverage.exe --encoding=UTF-8 ../../../src ../../coverage";
	}
}
