package com.baidu.jstest.result.coverage;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.execute.ClientExecutor;
import com.baidu.jstest.kiss.ClientKiss;
import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.Suite;
import com.baidu.jstest.project.XMLConstants;


/**
 * 基于Tangram定制，项目中代码为release文件， 除公共测试文件外，case无其他依赖文件
 * 
 * @author jiangshuguang
 * 
 */
public class CoverageKiss extends ClientKiss {

	private Kiss kiss;

	public CoverageKiss(Kiss kiss) {
		this.kiss = kiss;
	}

	public void test() throws TestException {
		ClientExecutor executor = ClientExecutor.getInstance();
//		executor.setRequest(request);
//		executor.setResponse(response);
		executor.execute(this);
	}

	public String getName() {
		return kiss.getName();
	}

	public Project getProject() {
		return kiss.getProject();
	}

	public void setProject(Project project) {
		kiss.setProject(project);
	}

	public void addChild(Suite suite) throws TestException {
		kiss.addChild(suite);
	}

	public List<Suite> child() {
		return kiss.child();
	}

	public Suite getChild(String name) {
		return kiss.getChild(name);
	}

	public Kiss getKiss(String name) {
		return kiss.getKiss(name);
	}

	public List<String> getDependence() {
		return null;
	}

	public boolean haveCase() {
		// TODO RELEASE判断可能有问题
		return true;
	}

	public List<String> getResource() {
		List<String> result = null;
		List<String> list = kiss.getResource();
		String coverage = kiss.getProject().getProperties()
				.getProperty(XMLConstants.COVERAGE_DIR);
		String basedir = kiss.getProject().getBasedir();
		String root = kiss.getProject().getRoot();
		if (null != coverage) {
			result = new ArrayList<String>();
			coverage = coverage.replace(kiss.getProject().getBasedir(), kiss
					.getProject().getRoot());
			for (int i = 0; i < list.size(); i++) {
				String s = list.get(i).replace(
						kiss.getProject().getRoot() + "/src", coverage);
				String kissdir = basedir + s.replaceFirst(root, "");
				if(!(new File(kissdir)).exists()){   //jiangshuguang 增加了排除某些文件的覆盖率的统计
					s = list.get(i);
				}
				result.add(s);
			}
		}
		return result;
	}

	public List<String> getTestResource() {
		return kiss.getTestResource();
	}

	public String runnableURI() {
		return this.getProject().getRoot() + "/run.do?case=" + this.getName()
				+ "&cov=true";
	}

	@Override
	public List<String> getCommonResource() {
		return kiss.getCommonResource();
	}

}
