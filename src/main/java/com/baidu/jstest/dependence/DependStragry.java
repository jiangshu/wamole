package com.baidu.jstest.dependence;

import java.util.List;

import com.baidu.jstest.project.Project;

/**
 * 依赖策略
 * 
 * @author dailiqi
 */
public interface DependStragry {
	/**
	 * 解析后的依赖Map
	 * 
	 * @param path 解析路径
	 * @return
	 */
	public List<String> getDepends(String path);
	
	public void setProject(Project project);
}
