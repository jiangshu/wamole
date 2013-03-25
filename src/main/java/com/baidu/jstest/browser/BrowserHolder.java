package com.baidu.jstest.browser;

import com.baidu.jstest.browser.invoker.BrowserInvoker;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.task.Processor;

/**
 * 针对一类型浏览器只存在一个,需从BrowserHolderFacotry获取实例
 * 
 * @author dailiqi
 */
public interface BrowserHolder {

	/**
	 * 获取浏览器名称
	 * @return
	 */
	public String getName();

	/**
	 * 删除浏览器
	 * 
	 * @param browser
	 */
	public void remove(Browser instance);

	/**
	 * 注册浏览器
	 * 
	 * @param notice
	 * @return
	 */
	public String register(Notice notice);

	/**
	 * 通知浏览器，返回需要执行的下个用例
	 * 
	 * @param notice
	 */
	public String notice(Notice notice);

	/**
	 * 唤醒当前holder的declare浏览器
	 * @throws TestException 
	 */
	public void invoke() throws TestException;

	/**
	 * 设定一个invoker
	 * 
	 * @param invoker
	 */
	public void setInvoker(Class<? extends BrowserInvoker> invoker);

	/**
	 * 设定声明Browser
	 * 
	 * @param browser
	 */
	public void setDeclaredBrowser(DeclaredBrowser browser);

	/**
	 * 获取默认
	 * 
	 * @return
	 */
	public DeclaredBrowser declaredBrowser();

	public int size();

	public void clear();

	public void setProcessor(Processor processor);

	/**
	 * 校验是否超时，由Thread启动
	 * @throws TestException 
	 */
	public void check() throws TestException;

}
