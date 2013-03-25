package com.baidu.jstest.result;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.HashMap;

/**
 * 报告的超时时间
 * 
 * @author dailiqi
 */
public class DeadLine {
	Logger log = LoggerFactory.getLogger(DeadLine.class);
	private int interval;// 每个结果最多等待时间
	private long deadline;// 总体超时时间
	private int count;
	private long buffer;
	private int size;
	Map<String,Integer>  bro; //test
	int i;

	/**
	 * Constructor
	 * 
	 * @param interval
	 */
	public DeadLine(int interval, int size) {
		long now = System.currentTimeMillis();
		bro = new HashMap();//test
		i=0;//test
		buffer = interval * 1000 * 2;
		this.size = size;
		this.deadline = now + 1000 * interval * size + buffer;
		this.interval = interval;
		log.debug("constructor, deadline : " + new Date(this.deadline)
				+ ",\n now : " + new Date(now));
	}

	/**
	 * 是否超时
	 * 
	 * @return
	 */
	public boolean isDead() {
//		log.info("isDead deadline : " + new Date(deadline));
//		log.info(this.count +":"+ this.size );
//		log.info("is dead :" + new Boolean(System.currentTimeMillis() > deadline || this.count > this.size));
		return System.currentTimeMillis() > deadline || this.count > this.size;
	}

	/**
	 * 尝试减少deadline的大小
	 * 
	 * @param count 当前最小count项
	 * @return
	 */
	public long decrease(int count) {
		
		if (count > this.count) {
			this.count = count;
			deadline = System.currentTimeMillis() + buffer + 1000 * interval
					* (size - count);
		}		
		return deadline;
	}
}
