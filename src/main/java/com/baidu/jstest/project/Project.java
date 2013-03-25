package com.baidu.jstest.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.baidu.jstest.dependence.DependStragry;
import com.baidu.jstest.dependence.Dependable;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.kiss.Kiss;

/**
 * 项目定义
 * 
 * @author dailiqi
 */
public class Project implements Suite {
	private String basedir;// 项目基础路径
	private String srcdir;// 源代码路径
	private String testdir;// 测试代码路径
	private ArrayList<Project> dependProject;// 依赖的项目可空
	private Env env;// 测试环境
	private String name;// 项目名称 唯一
	private Properties properties;// 项目属性
	private List<Suite> suites;// 用户定义模块
	private Map<String, Dependable> depends;// 用户定义依赖
	private Class<? extends DependStragry> ds;// 依赖策略
	private String root;
	private List<String> resource; // 项目的运行载入资源
	public Project() {
		suites = new ArrayList<Suite>();
		this.properties = new Properties();
		depends = new HashMap<String, Dependable>();
		resource = new ArrayList<String>();
	}

	public String getBasedir() {
		return basedir;
	}

	public void setBasedir(String basedir) {
		this.basedir = basedir;
	}

	public String getSrcdir() {
		return srcdir;
	}

	public void setSrcdir(String srcdir) {
		this.srcdir = srcdir;
	}

	public String getTestdir() {
		return testdir;
	}

	public void setTestdir(String testdir) {
		this.testdir = testdir;
		setProperties("test.dir", testdir);
	}

	public void addReference(Project proj) {
	};

	public Env getEnv() {
		return env;
	}

	public void setEnv(Env env) {
		this.env = env;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.root = "/" + name;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setProperties(String key, String value) {
		this.properties.put(key, value);
	}

	public List<Project> getDependProject() {
		if(dependProject == null)
			dependProject = new ArrayList<Project>();
		return dependProject;
	}

	public void setDependProject(Project dependProject) {
		this.getDependProject().add(dependProject);
	}

	public Map<String, Dependable> getDepends() {
		return depends;
	}

	public void setDepends(Map<String, Dependable> depends) {
		this.depends = depends;
	}

	public Dependable getDependence(String key) {
		return depends.get(key);
	}

	public void setDependece(String key, Dependable dependece) {
		this.depends.put(key, dependece);
	}

//	public void test(HttpServletRequest request, HttpServletResponse response) {
//		for (Suite suite : suites) {
//			try {
//				suite.test(request, response);
//			} catch (TestException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	public Class<? extends DependStragry> getDs() {
		return ds;
	}

	public void setDs(Class<? extends DependStragry> ds) {
		this.ds = ds;
	}

	public Project getProject() {
		return this;
	}

	public void setProject(Project project) {
	}

	public void initStructure(){
		suites = new ArrayList<Suite>();
	}
	public void addChild(Suite suite) throws TestException {
		suites.add(0, suite);
	}

	public List<Suite> child() {
		return suites;
	}

	public Suite getChild(String name) {
		if (null != suites && !suites.isEmpty()) {
			for (Suite sun : suites) {
				if (sun.getName().equals(name)) {
					return sun;
				} else if (null != sun.getChild(name))
					return sun.getChild(name);
			}
		}
		return null;
	}

	public Kiss getKiss(String name) {
		if (null != suites && !suites.isEmpty()) {
			for (Suite suite : suites) {
				if (null != suite.getKiss(name))
					return suite.getKiss(name);
			}
		}
		return null;
	}

	public String getRoot() {
		return root;
	}
	public void addResource(String path) {
		resource.add(path);
	}
	public List<String> getResource() {
		return resource;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "project["+this.getName()+"]";
	}
}
