package com.baidu.jstest.task;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.browser.BrowserHolder;
import com.baidu.jstest.browser.BrowserHolderFactory;
import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.page.handler.CaptureHandler;
import com.baidu.jstest.project.XMLConstants;
import com.baidu.jstest.result.ResultTable;
import com.baidu.jstest.result.ResultTableImpl;
import com.baidu.jstest.result.coverage.CoverageResultTableImpl;

/**
 * 根据当前任务，生成结果表，注册到具体browser中
 * 
 * @author dailiqi
 * 
 */
public class TaskProcessor implements Processor {

	private Task task;
	private ResultTable resultTable;
//	private CoverageResultTable covResultTable;
	private List<String> browserList;
	Logger logger = LoggerFactory.getLogger(TaskProcessor.class);
	
	public TaskProcessor(Task task) {
		this.task = task;
		this.browserList = task.getBrowserList();
		geneResultTable();
	}

	private void geneResultTable() {
//		String savePath = task.getProject().getTestdir() + "/tools/br/report";
		String savePath = task.getProject().getProperties().getProperty(XMLConstants.REPORT_DIR);
		
		if(null == savePath || "".equals(savePath)) {
			savePath = task.getProject().getTestdir() + "/tools/br/report";
		}
//		System.out.println(savePath);
		String intStr;
		int interval = 4;
		if (null != (intStr = task.getProject().getProperties()
				.getProperty("interval"))) {
			interval = Integer.parseInt(intStr);
		}

		List<Kiss> kissList = getKissList();
		if (task.isCov()) {
//			resultTable = new CoverageResultTableImpl(task.getId(),
//					browserList, kissList, interval, savePath);
			String covPath = task.getProject().getProperties()
			     .getProperty(XMLConstants.COVERAGE_DIR);
			String srcPath =  task.getProject().getSrcdir();
			String testPath =  task.getProject().getTestdir();
			resultTable = new CoverageResultTableImpl(task.getId(),
					browserList, kissList, interval, savePath,srcPath,testPath,covPath);
		} else {
			resultTable = new ResultTableImpl(task.getId(), browserList,
					kissList, interval, savePath);
		}
	}

	private List<Kiss> getKissList() {
		List<Kiss> kissList = new ArrayList<Kiss>();
		for (int i = 0; i < task.size(); i++) {
			kissList.add(task.get(i));
		}
		return kissList;
	}

	@Override
	public void process() {
		for (String br : browserList) {
			BrowserHolder holder = BrowserHolderFactory
					.getBrowserHolderInstance(br);
			holder.setProcessor(this);
		}
	}

	public ResultTable getResultTable() {

		return resultTable;
	}
}
