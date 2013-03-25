package com.baidu.jstest.browser;

import java.util.HashMap;
import java.util.Map;

public class BrowserHolderFactory {

	private static Map<String, BrowserHolder> map = new HashMap<String, BrowserHolder>();

	@SuppressWarnings("deprecation")
	public static BrowserHolder getBrowserHolderInstance(String browser) {
		if (!map.containsKey(browser)) {
			map.put(browser, new BrowserHolderImpl(browser));
		}
		return map.get(browser);
	}
}
