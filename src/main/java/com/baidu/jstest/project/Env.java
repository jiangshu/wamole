package com.baidu.jstest.project;

import java.util.ArrayList;
import java.util.List;

import com.baidu.jstest.browser.BrowserHolder;

/**
 * 测试环境信息
 * 
 * @author dailiqi
 */
public class Env {
	private List<BrowserHolder> holders;

	public Env() {
		holders = new ArrayList<BrowserHolder>();
	}

	public List<BrowserHolder> getBrowserHolders() {
		return holders;
	}

	public void addBrowserHolder(BrowserHolder browser) {
		if(!holders.contains(browser)) {
			this.holders.add(browser);	
		}
	}
	
	public BrowserHolder getBrowserHolder(String name) {
		for (int i = 0; i < holders.size(); i++) {
			BrowserHolder b = holders.get(i);
			if(b.getName().equals(name)) {
				return b;
			}
		}
		return null;
	}
}
