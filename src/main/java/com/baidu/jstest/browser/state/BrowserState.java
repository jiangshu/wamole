package com.baidu.jstest.browser.state;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.browser.Notice;

public interface BrowserState {
	/**
	 * 状态处理
	 * 
	 * @param notice
	 * @throws TestException
	 */
	public void handle(Notice notice) throws TestException;

	/**
	 * Thread检查
	 * 
	 * @throws TestException
	 */
	public void transfer() throws TestException;
}
