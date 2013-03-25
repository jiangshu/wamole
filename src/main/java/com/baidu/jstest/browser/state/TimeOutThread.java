package com.baidu.jstest.browser.state;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.browser.BrowserHolder;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.project.Projects;

/**
 * 超时校验
 * 
 * @author dailiqi
 */
public class TimeOutThread extends Thread {
	Logger logger = LoggerFactory.getLogger(TimeOutThread.class);
	private List<BrowserHolder> list;

	public TimeOutThread(Projects projects) {
		list = projects.getEnv().getBrowserHolders();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (list) {
				for (int i = 0; i < list.size(); i++) {
					BrowserHolder holder = list.get(i);
					try {
						holder.check();
					} catch (TestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// QueuePool pool = QueuePool.getInstace();
			// synchronized (pool) {
			// Map<String, TaskQueue> map = pool.getQueueList();
			// for (String key : map.keySet()) {
			// TaskQueue queue = map.get(key);
			// if (queue.isTimeOut())
			// queue.setState(new TimeOutState());
			// try {
			// queue.getState().transfer(queue);
			// logger.debug("thread run queueName = "
			// + queue.getName() + "&& state = "
			// + queue.getState().getClass());
			// } catch (TestException e) {
			// e.printStackTrace();
			// }
			// }
			// }
		}
	}
}
