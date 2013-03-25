package com.baidu.jstest.browser.invoker;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.browser.BrowserHolder;
import com.baidu.jstest.browser.TargetURL;
import com.baidu.jstest.exception.TestException;

public class BatchInvoker implements BrowserInvoker {
	Logger logger = LoggerFactory.getLogger(BatchInvoker.class);

	@Override
	public void invoke(BrowserHolder browser, TargetURL url)
			throws TestException {
		StringBuffer sb = new StringBuffer();
		sb.append("\"");
		sb.append(browser.declaredBrowser().getPath());
		sb.append("\"");
		sb.append(" ");
		sb.append("\"");
		sb.append(url.toString());
		sb.append("\"");
		logger.info("invoke command :" + sb.toString());
		try {
			Runtime.getRuntime().exec(sb.toString());
		} catch (IOException e) {
			throw new TestException("batch invoke error ,cmd:" + sb.toString()
					+ ".", e);
		}
	}

	@Override
	public void restart(BrowserHolder browser, TargetURL url)
			throws TestException {
		try {
			stop(browser);
			Thread.sleep(1000);
		} catch (TestException e) {
			throw new TestException(e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			invoke(browser, url);
		}
	}

	@Override
	public void invoke(BrowserHolder browser) throws TestException {
		this.invoke(browser, TargetURL.getURL(browser.declaredBrowser()));
	}

	@Override
	public void restart(BrowserHolder browser) throws TestException {
		this.restart(browser, TargetURL.getURL(browser.declaredBrowser()));
	}

	@Override
	public void stop(BrowserHolder browser) throws TestException {
		StringBuffer sb = new StringBuffer();
		sb.append("TASKKILL");
		sb.append(" ");
		sb.append("/F");
		sb.append(" ");
		sb.append("/IM");
		sb.append(" ");
		String path = browser.declaredBrowser().getPath();
		path = path.replace("\\", "/");

		String browserName = path.substring(path.lastIndexOf("/") + 1);
		sb.append("\"");
		sb.append(browserName);
		sb.append("\"");
		logger.info("invoke command :" + sb.toString());
		try {
			Runtime.getRuntime().exec(sb.toString());
		} catch (IOException e) {
			throw new TestException("batch stop error, cmd:" + sb.toString()
					+ ".", e);
		}
	}

}
