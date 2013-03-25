package com.baidu.jstest.browser;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.browser.Notice;

public class AnonymousBrowser implements Browser {
	Logger logger = LoggerFactory.getLogger(AnonymousBrowser.class);
	private String id;// ID
	private String name;
	private long lastNotice = -1;// 最后一次通知时间
	private int step = 0;// 步长

	public AnonymousBrowser(String name) {
		id = String.valueOf((int) (Math.random() * 10000));
		this.name = name;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isAnonymous() {
		return true;
	}

	@Override
	public String notice(Notice notice) {
		lastNotice = System.currentTimeMillis();
		this.step = notice.getStep() * 1000;
		logger.debug(notice.getBrowserName() + " is notice!!!notice time = "
				+ new Date(System.currentTimeMillis()));
		logger.debug("last notice time = " + new Date(lastNotice));
		return this.getId();
	}

	@Override
	public boolean isTimeOut() {
		long current = System.currentTimeMillis();
		if (current > lastNotice + step * 3) {
			return true;
		}
		return false;
	}
}
