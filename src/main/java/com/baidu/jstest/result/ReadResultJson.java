package com.baidu.jstest.result;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.page.handler.CaptureHandler;

public class ReadResultJson {
	
	Logger logger = LoggerFactory.getLogger(CaptureHandler.class);

	public static Result getResult(JSONObject json) throws TestException {
		Result result = null;
		try {
			if (json.has("endtime")) {
				result = new Result();
				result.setBrowser(json.getString("browser"));
				result.setFail(json.getInt("fail"));
				result.setTotal(json.getInt("total"));
				result.setName(json.getString("name"));
				result.setTimeStamp(json.getLong("endtime")
						- json.getLong("starttime"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new TestException("convert to result info error :"
					+ e.getMessage(), e);
		}

		return result;
	}      
}
