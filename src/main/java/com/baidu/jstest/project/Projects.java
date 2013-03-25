package com.baidu.jstest.project;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 项目组定义
 * 
 * @author dailiqi
 */
public class Projects implements Container<Project> {
	/** */
	private static final long serialVersionUID = -8069984656293568441L;
	Map<String, Project> map;
	Map<String, String> invokers;
	private Env env;// 测试环境
	// private List<Invoker> list ;

	public Projects() {
		map = new HashMap<String, Project>();
		invokers = new HashMap<String, String>();
	}

	public int size() {
		if (map.equals(null)) {
			return -1;
		} else {
			return map.size();
		}
	}

	public void addEntity(Project entity) throws Exception {		
		if (!map.containsKey(entity.getName())) {
//			throw new Exception(
//					"error on add project entity in projects,already have this project name");
//		} else {
			map.put(entity.getName(), entity);
		}
	}

	public Project getEntity(String name) {
		return map.get(name);
	}

	public boolean contains(Project entity) {
		return map.containsKey(entity.getName());
	}

	public boolean contains(String entryName) {
		return map.containsKey(entryName);
	}
	public Set<String> keySet() {
		return map.keySet();
	}

	public Env getEnv() {
		return env;
	}

	public void setEnv(Env env) {
		this.env = env;
	}

	public void addInvoker(String name, String clazz) {
		invokers.put(name, clazz);
	}
	public String getInvoker(String name) {
		return invokers.get(name);
	}
}
