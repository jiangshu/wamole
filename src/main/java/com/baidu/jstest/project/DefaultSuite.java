package com.baidu.jstest.project;

import java.util.ArrayList;
import java.util.List;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.kiss.Kiss;

public class DefaultSuite implements Suite {
	private List<Suite> list = new ArrayList<Suite>();
	private String name;
	private String src;
	private String testsrc;
	private Project project;
//	private Executor executor;

	public DefaultSuite(String name, String src, String testsrc) {
		this.name = name;
		this.src = src;
		this.testsrc = testsrc;
//		executor = SuiteExecutor.getInstance();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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

//	public void test(HttpServletRequest request, HttpServletResponse response)
//			throws TestException {
//		executor.execute(this, request, response);
//	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void addChild(Suite suite) throws TestException {
		list.add(suite);
	}

	public List<Suite> child() {
		return list;
	}

	public Suite getChild(String name) {
		if (this.name.equals(name)) {
			return this;
		} else if (null != list && !list.isEmpty()) {
			for (Suite sun : list) {
				if (sun.getName().equals(name)) {
					return sun;
				} else if (null != sun.getChild(name))
					return sun.getChild(name);
			}
		}
		return null;

		// Suite suite = null;
		// for (Suite sun : list) {
		// if (sun.getName().equals(name)) {
		// suite = sun;
		// } else if (null != sun.getChild(name))
		// suite = sun.getChild(name);
		// }
		// return suite;
	}

	public Kiss getKiss(String name) {
		Kiss kiss = null;
		for (Suite suite : list) {
			if (suite.getName().equals(name) && suite instanceof Kiss)
				return (Kiss) suite;
			else if (null != suite.getKiss(name)) {
				kiss = suite.getKiss(name);
			}
		}
		return kiss;
	}
}
