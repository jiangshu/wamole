package com.baidu.jstest.dependence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.project.Project;
import com.baidu.jstest.utils.FileUtil;

/**
 * tangram的默认依赖策略，按照import去获取相关依赖文件
 * 
 * @author dailiqi
 */
public class TangramDependStrategy implements DependStragry {
	Logger logger = LoggerFactory.getLogger(TangramDependStrategy.class);
	private Project project;// 项目
	private List<String> list = new ArrayList<String>();// 给出排序后的map
	private List<String> matched = new ArrayList<String>();

	public TangramDependStrategy() {

	}

	public TangramDependStrategy(Project project) {
		setProject(project);
	}

	private String regex = "///import\\s([^;]+);";

	/**
	 * 递归解析，入口为用例对应的源码文件，文件因为存在递归解析问题，所以使用中间变量 <li>读取文件 <li>匹配依赖 <li>查找依赖文件 <li>
	 * 递归依赖
	 * 
	 * @param path
	 */
	private void parse(final String path) {
		logger.debug("parsing path = " + path);
//		matched.add(path);
		Matcher matcher = Pattern.compile(regex).matcher(
				FileUtil.readFile(path));
		String filePath = null;
		String urlPath = null;
		while (matcher.find()) {
			String name = matcher.group(1);
			filePath = project.getSrcdir() + "/" + name.replace(".", "/")
					+ ".js";
			if (new File(filePath).exists()) {
				urlPath = filePath.replace(project.getBasedir(),
						project.getRoot());
				// 有项目依赖的解决方式
			} else if ((null != project.getDependProject())) {
				for (Project depends : project.getDependProject()) {
					String _path = filePath.replace(project.getBasedir(),
							depends.getBasedir());
					if (FileUtil.existsFile(_path)) {
						filePath = _path;
						urlPath = _path.replace(depends.getBasedir(),
								depends.getRoot());
						break;
					}
				}
			}
			if(urlPath == null) {
				logger.error("path parse fail : " + filePath);
			}
			//添加已经匹配过的matched 不进行重复过滤
			if (urlPath != null && !list.contains(urlPath) && !matched.contains(urlPath)) {
				matched.add(urlPath);
				parse(filePath);
				list.add(urlPath);
			}
		}

	}

	public List<String> getDepends(final String path) {
		parse(path);
		logger.debug("getDependence  list  size = " + list.size());
		return list;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
