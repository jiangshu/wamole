package com.baidu.jstest.result;
import org.json.JSONObject;

/**
 * 存储结果的2维表
 * @author dailiqi
 */
public interface ResultTable {
	/**
	 * 存储一个结果，并且对超时时间减少一个interval,并返回下个可执行的kiss
	 * @param result
	 */
	public String store(Result result);
//	public String store(JSONObject json);

	/**
	 * 获取浏览器Index
	 * @param browser
	 * @return
	 */
	public int getBrowserIndex(String browser);

	/**
	 * 获取CaseIndex
	 * @param kissName
	 * @return
	 */
	public int getKissIndex(String kissName);

	/**
	 * 根据index获取结果
	 * @param browserIndex
	 * @param kissIndex
	 * @return
	 */
	public Result getResult(int browserIndex, int kissIndex);

	/**
	 * 报告
	 */
	public void report();
	
	/**
	 * 获取下个需要执行的kiss
	 * @param browser 浏览器种类
	 * @return
	 */
	public String getNextExcutableKiss(String browser);
	
	public boolean isDead(String browser);
}
