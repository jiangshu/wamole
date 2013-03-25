package com.baidu.jstest.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	Properties properties;

	public PropertyUtil(String path) {
		properties = new Properties();
		init(path);
	}

	public void init(String path) {
		//this.getClass().getClassLoader()
		InputStream inputStream = ClassLoader
				.getSystemResourceAsStream(path);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getProperty(String name) {
		if (properties.containsKey(name))
			return properties.getProperty(name);
		else
			return null;
	}
}
