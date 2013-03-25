package com.baidu.jstest.browser;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BrowserHolderTest {

	private BrowserHolder holder;

	@Before
	public void init() {
		holder = BrowserHolderFactory.getBrowserHolderInstance("ff");
	}

	@After
	public void after() {
		holder = null;
	}

	@Test
	public void test_register_01() {
		
	}
//	@Test
//	public void test_notice_01() {
//		Browser b = new AnonymousBrowser("ff");
//		String bId = b.getId();
//		holder.register(b);
//		Assert.assertEquals(1, holder.size());
//		Notice notice = new Notice();
//		notice.setBrowserName("ff");
//		notice.setBrowserId(String.valueOf(bId));
//		String result = holder.notice(notice);
//		Assert.assertEquals(bId, result);
//		holder.clear();
//	}

//	@Test
//	public void test_notice_02() {
//		Notice notice = new Notice();
//		notice.setBrowserName("ff");
//		String result = holder.notice(notice);
//		Assert.assertNotNull(result);
//		Assert.assertEquals(1, holder.size());
//		// Assert.assertTrue(holder.)
//		 holder.clear();
//	}
//
//	@Test
//	public void test_getName_01() {
//		Assert.assertEquals("ff", holder.getName());
//	}

//	@Test
//	public void test_remove_01() {
//		Browser b = new AnonymousBrowser("ff");
//		holder.register(b);
//		Assert.assertEquals(1, holder.size());
//		holder.remove(b);
//		Assert.assertEquals(0, holder.size());
//		holder.clear();
//	}
}
