package com.baidu.jstest.browser.state;

import com.baidu.jstest.browser.Notice;
import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.result.Result;

public class ReadyState implements BrowserState {

	private Result result;

	@Override
	public void handle(Notice notice) throws TestException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transfer() throws TestException {
		// TODO Auto-generated method stub
		
	}

	/*
	 * 
	 * @see
	 * com.baidu.jstest.task.state.QueueState#handle(com.baidu.jstest.task.TaskQueue
	 * , com.baidu.jstest.result.Result)
	 */
//	public Kiss handle(TaskQueue queue, Notice notice) throws TestException {
//		Kiss kiss = null;
//		this.result = notice.getResult();
//		// 如果无结果，转化至等待状态 并且当前有执行用例
//		if (null == result // && null != notice.getCurrentKiss()) {
//		) {
//			// queue.setState(new WaitState());
//		}
//		// 如果队列为空 直接返回null 等待下次轮询
//		if (queue.isEmpty()) {
//			return null;
//		} else {// 队列非空
//			// 队列第一个任务为空，将其弹出，递归调用第二个任务
//			if (queue.peek().isEmpty()) {
//				queue.poll();
//				return handle(queue, notice);
//				// 如果第一个任务非空，则pop出第一个队列中第一个kiss
//			} else {
//				Task task = queue.peek();
//				kiss = task.poll();
//				if (task.isRelease())
//					kiss = new ReleaseKiss(kiss);
//				else if (task.isCov())
//					kiss = new CoverageKiss(kiss);
//			}
//		}
//		return kiss;
//	}
//
//	public void transfer(TaskQueue queue) {
//		// queue.setState(new WaitState());
//	}
}
