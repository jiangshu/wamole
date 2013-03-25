package com.baidu.jstest.result;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baidu.jstest.kiss.DefaultKiss;
import com.baidu.jstest.kiss.Kiss;


public class ResultTableImplTest {

//	private ResultTableImpl table;
//
//	@Before
//	public void init() {
//		List<String> browserList = new ArrayList<String>();
//		browserList.add("ff");
//		browserList.add("chrome");
//		List<Kiss> kissList = new ArrayList<Kiss>();
//		Kiss k1 = new DefaultKiss("baidu.array.each", null, null);
//		Kiss k2 = new DefaultKiss("baidu.array.empty", null, null);
//		kissList.add(k1);
//		kissList.add(k2);
//		table = new ResultTableImpl("teeee",browserList, kissList, 4, "c:/test");
//	}
//
//	@Test
//	public void test_getBrowserIndex_01() {
//		int i = table.getBrowserIndex("ff");
//		Assert.assertEquals(0, i);
//	}
//
//	@Test
//	public void test_getBrowserIndex_02() {
//		int i = table.getBrowserIndex("");
//		Assert.assertEquals(-1, i);
//	}
//
//	@Test
//	public void test_getKissIndex_01() {
//		int i = table.getKissIndex("baidu.array.each");
//		Assert.assertEquals(0, i);
//	}
//
//	@Test
//	public void test_getKissIndex_02() {
//		int i = table.getKissIndex("baidu.array.no");
//		Assert.assertTrue(i == -1);
//	}
//
//	@Test
//	public void test_store_01() {
//		Result result = new Result();
//		result.setBrowser("ff");
//		result.setName("baidu.array.each");
//		table.store(result);
//	}
//
//	@Test
//	public void test_getResult_01() {
//		int i = table.getBrowserIndex("ff");
//		int j = table.getKissIndex("baidu.array.each");
//		Result result = table.getResult(i, j);
//		Assert.assertNull(result);
//	}
//
//	@Test
//	public void test_getResult_02() {
//		Result result = new Result();
//		result.setBrowser("ff");
//		result.setName("baidu.array.each");
//		table.store(result);
//		int i = table.getBrowserIndex("ff");
//		int j = table.getKissIndex("baidu.array.each");
//		Result result1 = table.getResult(i, j);
//		Assert.assertNotNull(result1);
//		Assert.assertEquals("ff", result1.getBrowser());
//	}
////	@Test
////	public void test_getNextKiss_01() {
////		String kiss1 = table.getNextExcutableKiss("ff");
////		System.out.println(kiss1);
////		Assert.assertEquals("baidu.array.each", kiss1);
////		String kiss2 = table.getNextExcutableKiss("ff");
////		System.out.println(kiss2);
////		Assert.assertEquals("baidu.array.empty", kiss2);
////	}
////	@Test
////	public void test_getNextKiss_02() {
////		String kiss1 = table.getNextExcutableKiss("ff");
////		Assert.assertEquals("baidu.array.each", kiss1);
////		String kiss2 = table.getNextExcutableKiss("ff");
////		Assert.assertEquals("baidu.array.empty", kiss2);
////		String kiss3 = table.getNextExcutableKiss("ff");
////		Assert.assertEquals(null,kiss3);
////	}
//	@Test
//	public void test_isDead_01() {
//		boolean re = table.isDead();
//		Assert.assertTrue(!re);
//	}
//	
//	@Test
//	public void test_isDead_02() {
//		String kiss1 = table.getNextExcutableKiss("ff");
////		Assert.assertEquals("baidu.array.each", kiss1);
//		table.getNextExcutableKiss("ff");
//		table.getNextExcutableKiss("chrome");
//		table.getNextExcutableKiss("chrome");
//		Assert.assertTrue(!table.isDead());
//	}
//	
//	@Test
//	public void test_isDead_03() {
//		Result r1 = new Result();
//		r1.setBrowser("ff");
//		r1.setName("baidu.array.each");
//		table.store(r1);
//		Result r2 = new Result();
//		r2.setBrowser("chrome");
//		r2.setName("baidu.array.each");
//		table.store(r2);
//		//多浏览器的一个BUffer
//		Result r3 = new Result();
//		r3.setBrowser("chrome");
//		r3.setName("baidu.array.empty");
//		table.store(r3);
//		Assert.assertTrue(table.isDead());
//	}
}
