package com.baidu.jstest.task;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 针对浏览器，任务队列
 * 
 * @author dailiqi
 */
public class TaskQueue {

	Logger logger = LoggerFactory.getLogger(TaskQueue.class);
	private Queue<Task> queue;
	private static TaskQueue instance = new TaskQueue();
	private Task current;
	public Task current() {
		return current;
	}
	private TaskQueue() {
		logger.info("TASK QUEUE construction");
		queue = new LinkedList<Task>();
	}

	public static TaskQueue getInstace() {
		if (instance == null)
			instance = new TaskQueue();
		return instance;
	}

	public synchronized boolean isEmpty() {
		return queue.isEmpty();
	}

	public synchronized Task getTask() {
		current = queue.poll();
		return current;
	}
    
	public synchronized int size() {
		return queue.size();
	}
	public synchronized void addTask(Task task) {
		queue.add(task);
		if(null == current) {
			TaskProcessor processor = new TaskProcessor(this.getTask());
			processor.process();
		}		
		logger.info("Now, queue size:" + queue.size());
	}
}
