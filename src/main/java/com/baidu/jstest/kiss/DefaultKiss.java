package com.baidu.jstest.kiss;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.dependence.DependStragry;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.execute.ClientExecutor;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.Suite;
import com.baidu.jstest.project.XMLConstants;
import com.baidu.jstest.utils.FileUtil;

/**
 * 基于Tangram定制，项目中代码为单独文件， case为与之对应的唯一文件，除公共测试文件外，case无其他依赖文件
 * 
 * @author dailiqi
 */
public class DefaultKiss extends ClientKiss {
	Logger logger = LoggerFactory.getLogger(DefaultKiss.class);
	private String src;
	private String testsrc;
	private String name;
	private Project project;
	private ClientExecutor executor = null;

	public DefaultKiss(String name, String src, String testsrc) {
		this.src = src;
		this.testsrc = testsrc;
		this.name = name;
		executor = ClientExecutor.getInstance();
	}

	public boolean haveCase() {
		boolean have = false;
		if (testsrc != null && !"".equals(testsrc))
			have = FileUtil.existsFile(testsrc);
//		logger.info("Case " + name + " haveCase:" + have);
		return have;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTestsrc() {
		return testsrc;
	}

	public void setTestsrc(String testsrc) {
		this.testsrc = testsrc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDependence() {
		Class<? extends DependStragry> depend = getProject().getDs();
		DependStragry dep = null;
		try {
			dep = depend.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		dep.setProject(project);
		return dep.getDepends(src);
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public void addChild(Suite suite) throws TestException {
		throw new TestException("Kiss doesn't support add suite method");
	}

	public List<Suite> child() {
		return null;
	}

	public Suite getChild(String name) {
		return this.getKiss(name);
	}

	public Kiss getKiss(String name) {
		if (this.name.equals(name))
			return this;
		else
			return null;
	}

	/*
	 * 相对路径资源列表
	 * 
	 * @see com.baidu.jstest.Kiss#getResource(boolean)
	 */
	public List<String> getResource() {
		List<String> list = new ArrayList<String>();
		list.addAll(getDependence());
		list.add(src.replace(project.getBasedir(), project.getRoot()));
		return list;
	}

	public List<String> getTestResource() {
		List<String> list = new ArrayList<String>();
//		String tools = testsrc.substring(0, testsrc.lastIndexOf("/"))
//				+ "/tools.js";
//		if (FileUtil.existsFile(tools)) {
//			list.add(tools.replaceAll(project.getBasedir(), project.getRoot()));
//		}
		list.add(testsrc.replace(project.getBasedir(), project.getRoot()));
		return list;
	}

	public String runnableURI() {
		return this.getProject().getRoot() + "/run.do?case=" + this.getName();
	}

	@Override
	public List<String> getCommonResource() {
		List<String> list = new ArrayList<String>();
		List<String> resource = project.getResource();
		for (int i = 0; i < resource.size(); i++) {
			String path = resource.get(i);
			if (path.contains(XMLConstants.CASE_DIR)) {
				path = path.replace("${" + XMLConstants.CASE_DIR + "}",
						testsrc.substring(0, testsrc.lastIndexOf("/")));
				try {
					path = FileUtil.normalize(path).replace("\\", "/");
				} catch (TestException e) {
					e.printStackTrace();
				}
			}
			if(FileUtil.existsFile(path)) {
				path = path.replace(project.getBasedir(), project.getRoot());
				list.add(path);
			}
		}
		return list;
	}

	@Override
	public void test() throws TestException {
		executor.execute(this);
	}
}
