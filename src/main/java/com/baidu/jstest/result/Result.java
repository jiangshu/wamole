package com.baidu.jstest.result;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Result {
	private String name;
	private int fail;
	private int total;
	private String browser;
	private long timeStamp;
	private String task;
	private boolean isError;
	private String Json;	
	private Map<String,Object> cov;
	
	public Result(){
		 cov = new HashMap<String,Object>();
	}
	
	@Override
	public String toString() {
		return "result: name = " + name + "	fail:" + fail + "	total:" + total+"    cov"+Json;
	}
    
	public Map<String,Object> getCovObject() {
		return cov;
	}
	
	public String getCov() {
		return Json;
	}
	public String getName() {
		return name;
	}

	public int getTotal() {
		return total;
	}

	public int getFail() {
		return fail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setCov(String covJson) {
		this.Json = covJson;
	}
	
	public void setCovObject(Map<String,Object> cov) {
		this.cov = cov;
	}
	
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public boolean isError() {
		return isError;
	}

}
