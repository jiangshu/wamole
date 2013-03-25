package com.baidu.jstest.browser;

/**
 * 经过xml声明的Browser
 * @author dailiqi
 */
public class DeclaredBrowser extends AnonymousBrowser {

	public DeclaredBrowser(String name) {
		super(name);
	}

	private String host;
	private String path;

	public String getHost() {
		return host;
	}
	@Override
	public boolean isAnonymous() {
		return false;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
