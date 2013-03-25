package com.baidu.jstest.dependence;

import java.util.List;

/**
 * 项目内资源有依赖关系
 * @author dailiqi
 */
public interface Dependable {
	public List<String>  getDependence();
}
