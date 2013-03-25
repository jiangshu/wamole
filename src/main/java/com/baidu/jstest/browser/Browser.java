package com.baidu.jstest.browser;

import com.baidu.jstest.browser.Notice;

/**
 * 浏览器信息
 * 
 * @author dailiqi
 */
public interface Browser {

	public String getId();// 获取ID

	public String getName();// 获取名字

	public boolean isAnonymous();// 是否是匿名capture

	/**
	 * 通知，返回该浏览器ID
	 * 
	 * @param notice
	 * @return
	 */
	public String notice(Notice notice);

	/**
	 * 判断是否超时
	 * 
	 * @return
	 */
	public boolean isTimeOut();
}
