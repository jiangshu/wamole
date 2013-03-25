package com.baidu.jstest.task;

import com.baidu.jstest.result.ResultTable;

public interface Processor {
	public void process();
	
	public ResultTable getResultTable();
}
