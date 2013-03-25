package com.baidu.jstest.page;

import java.util.Locale;

import freemarker.template.Configuration;

public class ConfigurationFactory {
	private static Configuration instance;

	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
			instance.setClassForTemplateLoading(ConfigurationFactory.class, "/ftl");
			instance.setEncoding(Locale.CHINA, "UTF-8");
		}
		return instance;
	}
}
