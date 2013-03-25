package com.baidu.jstest.kiss;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.execute.ClientExecutor;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.Suite;
import com.baidu.jstest.project.XMLConstants;

/**
 * 基于Tangram定制，项目中代码为release文件， 除公共测试文件外，case无其他依赖文件
 * 
 * @author dailiqi
 */
public class ReleaseKiss extends ClientKiss {

	private Kiss kiss;

	public ReleaseKiss(Kiss kiss) {
		this.kiss = kiss;
	}

	public void test() throws TestException {
		ClientExecutor executor = ClientExecutor.getInstance();
		executor.setRequest(request);
		executor.setResponse(response);
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
		return kiss.haveCase();
	}

	public List<String> getResource() {
		//增加一个判断文件是否存在的判定
		List<String> list = new ArrayList<String>();
		Properties pro = kiss.getProject().getProperties();
		//这个路径事实上已经经过了转换
		String file = pro.getProperty(XMLConstants.RELEASE_FILE);
		// 判断该文件是否属于本项目
		if (file.startsWith(kiss.getProject().getBasedir())) {
			file = file.replace(kiss.getProject().getBasedir(), "/"
					+ kiss.getProject().getName());
		} else {
			// 否则查找该项目的依赖项目文件
			for (Project p : kiss.getProject().getDependProject()) {
				if(file.startsWith(p.getBasedir())){
					file = file.replace(p.getBasedir(), "/"+p.getName());
					break;
				}
				// file = file.replace(kiss.getProject().getDependProject()
				// .getBasedir(), "/"
				// + kiss.getProject().getDependProject().getName());
				// file = file.replace(p.getBasedir(), "/" + p.getName());

			}
		}
		// file = file.replace(kiss.getProject().getBasedir(), "/"
		// + kiss.getProject().getName());
		list.add(file);
		return list;
	}

	public List<String> getTestResource() {
		return kiss.getTestResource();
	}

	public String runnableURI() {
		return this.getProject().getRoot() + "/run.do?case=" + this.getName()
				+ "&release=true";
	}

	@Override
	public List<String> getCommonResource() {
		return kiss.getCommonResource();
	}
}
