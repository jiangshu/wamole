package com.baidu.jstest.browser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.browser.invoker.BrowserInvoker;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.page.handler.CaptureHandler;
import com.baidu.jstest.task.Processor;
import com.baidu.jstest.task.Task;
import com.baidu.jstest.task.TaskProcessor;
import com.baidu.jstest.task.TaskQueue;

/**
 * 针对一类型浏览器只存在一个BrowserHolder
 * 
 * @author dailiqi
 */
public class BrowserHolderImpl implements BrowserHolder {
	private String name;
	Map<String, Browser> instanceMap;
	private Class<? extends BrowserInvoker> invoker;
	private DeclaredBrowser declaredBrowser;
	private Processor processor;
	Logger logger = LoggerFactory.getLogger(BrowserHolderImpl.class);
	
	@Deprecated
	BrowserHolderImpl() {
		instanceMap = new HashMap<String, Browser>();
	}

	/**
	 * 请使用BrowserHolderFactory获取实例
	 * 
	 * @param name 浏览器名称
	 */
	@Deprecated
	BrowserHolderImpl(String name) {
		this();
		this.name = name;
	}

	public Class<? extends BrowserInvoker> getInvoker() {
		return invoker;
	}

	public void setInvoker(Class<? extends BrowserInvoker> invoker) {
		this.invoker = invoker;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 删除浏览器
	 * 
	 * @param browser
	 */
	public synchronized void remove(Browser browser) {
		instanceMap.remove(browser.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baidu.jstest.browser.BrowserHolder#register(com.baidu.jstest.browser
	 * .Notice)
	 */
	public synchronized String register(Notice notice) {
		Browser b = instanceMap.get(notice.getBrowserId());
		if (null == b) {
			b = new AnonymousBrowser(this.getName());
			instanceMap.put(notice.getBrowserId(), b);
		}
		return b.notice(notice);
		// instanceMap.put(browser.getId(), browser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baidu.jstest.browser.BrowserHolder#notice(com.baidu.jstest.browser
	 * .Notice)
	 */
	public synchronized String notice(Notice notice) {
		String next = null;
		register(notice);
		// 如果有需要处理的task
		if (null != processor) {
			// 如果有结果，则去取得下一个用例
			if (null != notice.getResult()) {
				next = processor.getResultTable().store(notice.getResult());
			} else {
				next = processor.getResultTable().getNextExcutableKiss(this.getName());
			}
			if (next == null) {
				// 如果当前结果表已经遍历结束，则空唤醒新的，否则空轮询
				if (processor.getResultTable().isDead(notice.getBrowserName())) {
					Task newTask = TaskQueue.getInstace().getTask();
					if (null != newTask) {
						TaskProcessor processor;
						processor = new TaskProcessor(newTask);
						processor.process();
					} else {
						this.processor = null;
					}
				}
			}
		}
		return next;
	}

	@Override
	public DeclaredBrowser declaredBrowser() {
		return declaredBrowser;
	}

	public void setDeclaredBrowser(DeclaredBrowser browser) {
		this.declaredBrowser = browser;
	}

	@Override
	public int size() {
		return instanceMap.size();
	}

	@Override
	public void clear() {
		instanceMap.clear();
	}

	@Override
	public void invoke() throws TestException{
		try {
			Set<String> keys = instanceMap.keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				if(!instanceMap.get(key).isAnonymous()) {
					return;
				}
			}
			TargetURL url = TargetURL.getURL(declaredBrowser);
			this.getInvoker().newInstance().invoke(this,url);
			this.instanceMap.put(declaredBrowser.getId(), declaredBrowser);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new TestException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new TestException(e);
		}
	}

	@Override
	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	@Override
	public synchronized void check() throws TestException {
		instanceMap.keySet();
		for (Iterator<String> iterator = instanceMap.keySet().iterator(); iterator
				.hasNext();) {
			String key = iterator.next();
			Browser browser = instanceMap.get(key);
			// 如果浏览器超时
			if (browser.isTimeOut()) {
				// 如果是声明过的，则restart
				if (!browser.isAnonymous()) {
					try {
						this.getInvoker().newInstance().restart(this);
					} catch (InstantiationException e) {
						e.printStackTrace();
						throw new TestException(e);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new TestException(e);
					}
					// 否则从holder中删除
				} else {
					iterator.remove();
					browser = null;
				}
			}
		}
	}
}
