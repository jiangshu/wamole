package com.baidu.jstest.browser.invoker;

import com.baidu.jstest.browser.BrowserHolder;
import com.baidu.jstest.browser.TargetURL;
import com.baidu.jstest.exception.TestException;

public interface BrowserInvoker {
	public void invoke(BrowserHolder browser, TargetURL url) throws TestException;

	public void restart(BrowserHolder browser, TargetURL url) throws TestException;

	// public void stop(Browser browser, TargetURL url) throws TestException;

	public void invoke(BrowserHolder browser) throws TestException;

	public void restart(BrowserHolder browser) throws TestException;

	public void stop(BrowserHolder browser) throws TestException;
}
