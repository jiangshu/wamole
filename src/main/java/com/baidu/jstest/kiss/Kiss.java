package com.baidu.jstest.kiss;

import java.util.List;

import com.baidu.jstest.dependence.Dependable;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.project.Suite;

public interface Kiss extends Suite, Dependable {
	public void test() throws TestException;

	public boolean haveCase();

	public List<String> getCommonResource();

	public List<String> getResource();

	public List<String> getTestResource();
}
