package com.baidu.jstest.execute;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.kiss.Kiss;

/**
 * 执行逻辑  1:直接构建页面，
 *		   2:根据当前的ajax请求/或者当前页面不可用，直接href跳转
 * href跳转后，其实又重新走了1
 * 传入的有kiss，suite，以及
 *   run.do?suite=baidu.ajax   batch=true
 *   当这样的情况,必定是批量执行,那么,直接redrict to suite的第一个子节点
 *   run.do?case=baidu.ajax.post 并且希望访问下一个
 *   run.do?suite=baidu
 *   
 *   run.do?suite=baidu.ajax 那么是没有当前页面的，也没result 所以要redirect
 *   run.do?case=baidu.ajax.post 一般会有结果，有结果后，就ajax的href跳转
 *   
 *   
 * @author dailiqi
 *
 */
public interface Executor <T extends Kiss>{
	public void execute(T kiss) throws TestException;
}
