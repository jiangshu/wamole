package com.baidu.jstest.task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.baidu.jstest.kiss.Kiss;
import com.baidu.jstest.kiss.ReleaseKiss;
import com.baidu.jstest.project.Project;
import com.baidu.jstest.project.Suite;
import com.baidu.jstest.result.coverage.CoverageKiss;

public class TaskAdapter implements Task {

	private String id;
	private List<Kiss> kissList;
	private Project project;
	private List<Kiss> noKissList;
	private List<String> browserList;
	private boolean release;
	private boolean cov;
//	private boolean cove;

	public TaskAdapter(Suite suite, List<String> browserList) {
		this(suite, false, false, browserList);
	}

	public TaskAdapter(Suite suite, boolean release, boolean cov,
			List<String> browserList) {
		this.release = release;
		this.browserList = browserList;
		this.cov = cov;
		this.id = UUID.randomUUID().toString();
		kissList = new ArrayList<Kiss>();
		noKissList = new ArrayList<Kiss>();
		this.project = suite.getProject();
//		this.cove=cove;
		adapte(suite);
	}

	public boolean isCov() {
		return cov;
	}

//	public boolean isCove(){
//		return cove;
//	}
	
	private void adapte(Suite suite) {
		if (suite instanceof Kiss) {
			Kiss kiss = (Kiss) suite;
			if (cov) {
				kiss = new CoverageKiss((Kiss) suite);
			} else if (release) {
				kiss = new ReleaseKiss((Kiss) suite);
			}
			//kissList.add(kiss);
			if (((Kiss) suite).haveCase())
				kissList.add(kiss);
			else {
				noKissList.add(kiss);
			}
			
		} else {
			List<Suite> list = suite.child();
			for (Suite suite2 : list) {
				adapte(suite2);
			}
		}
	}

	public String getId() {
		return id;
	}

	public Project getProject() {
		return project;
	}

	public int size() {
		return kissList.size();
	}

	public Kiss get(int intdex) {
		return kissList.get(intdex);
	}

	public boolean isEmpty() {
		return null == kissList || kissList.size() <= 0;
	}

	@Override
	public List<String> getBrowserList() {
		return this.browserList;
	}

	@Override
	public int getIndex(String name) {
		for (int i = 0; i < kissList.size(); i++) {
			if (kissList.get(i).getName().equals(name))
				return i;
		}
		return -1;
	}

	// @Override
	// public void registerBrowser(List<String> browserList) {
	// this.browserList = browserList;
	// geneResultTable();
	// }

	// @Override
	// public ResultTable getResultTable() {
	// return resultTable;
	// }

	// @Override
	// public void perform() {
	// for (String br : browserList) {
	// BrowserHolder holder = BrowserHolderFactory
	// .getBrowserHolderInstance(br);
	// holder.addTask(new TaskWrapper(this));
	// }
	// }

	// @Override
	// public boolean hasNextKiss() {
	// return kissList.size() > 0;
	// }
	//
	// @Override
	// public Kiss nextKiss() {
	// return kissList.remove(0);
	// }
}
