package com.baidu.jstest.utils;

import com.baidu.jstest.exception.ParseException;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.XMLConstants;

public class PathUtil {

	/**
	 * 根据项目将${var}的形式转化成具体内容
	 * 
	 * @param project
	 * @param path
	 * @return
	 * @throws ParseException
	 */
	public static String converse(Project project, String path)
			throws ParseException {
		path = removeLastSplit(path);
		if (path.contains("${") && path.contains("}")
				&& path.indexOf("${") + 2 <= path.indexOf("}")) {
			String propKey = path.substring(path.indexOf("${") + 2,
					path.indexOf("}"));
			if (!propKey.equals(XMLConstants.CASE_DIR)) {
				String property = project.getProperties().getProperty(propKey);
				if (null != property && !"".equals(property)) {
					path = path.replace("${" + propKey + "}", property);
				} else {
					throw new ParseException("build the path: " + path
							+ " fails, beacuse " + propKey + " is undefined.");
				}
			}
		}
		return path;
	}

	public static String removeLastSplit(String path) {
		if (null != path && (path.endsWith("/") || path.endsWith("\\")))
			return path.substring(0, path.length() - 1);
		else
			return path;
	}

	public static String separator(String path) {
		return path.replace("\\", "/");
	}
}
