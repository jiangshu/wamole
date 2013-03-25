package com.baidu.jstest.result;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baidu.jstest.kiss.DefaultKiss;
import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.result.ReportGenerator;
import com.baidu.jstest.result.Result;

public class ReportGeneratorTest {

	/**
	 * 
	 */
	@Test
	public void generate_01() {
		List<String> browserList = new ArrayList<String>();
		browserList.add("chrome");
		browserList.add("firefox");
		List<Kiss> kissList = new ArrayList<Kiss>();
		Kiss k1 = new DefaultKiss("baidu.array.each", "", "");
		Kiss k2 = new DefaultKiss("baidu.array.some", "", "");
		kissList.add(k1);
		kissList.add(k2);
		Result[][] results = new Result[2][2];
		Result r00 = new Result();
		r00.setBrowser("chrome");
		r00.setFail(0);
		r00.setError(false);
		r00.setName("baidu.array.each");
		r00.setTask("123");
		r00.setTimeStamp(100000L);
		r00.setTotal(5);
		Result r01 = new Result();
		r01.setBrowser("chrome");
		r01.setFail(0);
		r01.setError(false);
		r01.setName("baidu.array.some");
		r01.setTask("123");
		r01.setTimeStamp(100000L);
		r01.setTotal(5);
//		Result r10 = new Result();
//		r10.setBrowser("firefox");
//		r10.setFail(0);
//		r10.setError(false);
//		r10.setName("baidu.array.each");
//		r10.setTask("123");
//		r10.setTimeStamp(100000L);
//		r10.setTotal(5);
		Result r11 = new Result();
		r11.setBrowser("firefox");
		r11.setFail(1);
		r11.setError(false);
		r11.setName("baidu.array.some");
		r11.setTask("123");
		r11.setTimeStamp(100000L);
		r11.setTotal(5);
		results[0][0] = r00;
		results[0][1] = r01;
//		results[1][0] = r10;
		results[1][1] = r11;
		ReportGenerator ge = new ReportGenerator();
		Writer writer = new OutputStreamWriter(System.out);
		ge.generate( writer, browserList, kissList, results);
	}
}
