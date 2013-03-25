package com.baidu.jstest.task;

import java.util.List;

import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.project.Project;

/**
 * 
 * @author dailiqi
 */
public interface Task {

	public String getId();

	public int size();

	public Project getProject();

	public int getIndex(String name);

	public Kiss get(int index);

	public boolean isCov();
	
//	public boolean isCove();

	public List<String> getBrowserList();
}
