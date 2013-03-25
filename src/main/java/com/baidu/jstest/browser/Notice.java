package com.baidu.jstest.browser;

import com.baidu.jstest.result.Result;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 浏览器通知消息
 * 
 * @author dailiqi
 */
public class Notice {
	private String browserId;
	private String browserName;
	private int step;
	private Result result;

	public Notice() {
	}
	public Notice(String browser, int step) {
		this.browserName = browser;
		this.step = step;
		// this.currentKiss = currentKiss;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getBrowserId() {
		return browserId;
	}

	public void setBrowserId(String browserId) {
		this.browserId = browserId;
	}
}
