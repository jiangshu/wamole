package com.baidu.jstest.project;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.jstest.exception.TestException;
import com.baidu.jstest.kiss.DefaultKiss;
import com.baidu.jstest.kiss.Kiss;

public class ProjectParser {
	Logger logger = LoggerFactory.getLogger(ProjectParser.class);
	private Project project;
    int i=0;

	public static void parse(Project project) throws TestException {
		ProjectParser parser = new ProjectParser();
		parser.project = project;
		parser.project.initStructure();
		String src = parser.project.getSrcdir();
		parser.parseDir(project, src);
		project = parser.project;
		//i++;
	}

	private Suite parseDir(Suite father, String path) throws TestException {
		
		if (new File(path).isDirectory()) {
			Suite suite = null;
			String name = src2name(path);
			if (name != null) {
				suite = SuiteFactory.getSuite(name, path, src2test(path));
				logger.debug("parseed suite ,name = " + name);
				// if (father.child().size() > 0)
				// suite.SetNext(father.child().get(0));
				if (null != suite)
					father.addChild(suite);
				suite.setProject(project);
			} else {
				suite = father;
			}
			File dir = new File(path);
			File[] files = dir.listFiles();
				
			for (int i = 0; i < files.length; i++) {
//		    for (int i = 0; i < 3; i++) {
				if (files[i].isDirectory()) {
					if (null != suite)
						parseDir(suite, files[i].getAbsolutePath());
					else
						parseDir(father, files[i].getAbsolutePath());
				} else {
					
					String srcpath = transSeprator(files[i].getAbsolutePath());
//					if(!(srcpath.substring(srcpath.length()-3, srcpath.length()).equals(".js")))
//						continue;  //屏蔽无关文件
//					System.out.println(srcpath.substring(srcpath.length()-3, srcpath.length()));
//					System.out.println(srcpath);
//					i++;
					String srcName = src2name(srcpath);
					Kiss kiss = new DefaultKiss(srcName, srcpath,
							src2test(srcpath));
					kiss.setProject(project);
					if (null != suite) {
						try {
							// 设置Kiss的next项
							// if ((suite.child().size()) > 0)
							// kiss.SetNext(suite.child().get(0));
							// else
							// kiss.SetNext(suite.next());
							suite.addChild(kiss);

						} catch (TestException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return suite;

		} else if (new File(path).isFile()) {
//			if(!(path.substring(path.length()-3, path.length()).equals(".js")))
//				return null; //屏蔽无关文件
			String srcpath = transSeprator(new File(path).getAbsolutePath());
			String srcName = src2name(srcpath);
			Kiss kiss = new DefaultKiss(srcName, srcpath, src2test(srcpath));
			kiss.setProject(project);
			father.addChild(kiss);
		}
		return null;
	}

	private String src2test(String src) {
		src = transSeprator(src);
		String testbase = transSeprator(project.getTestdir());
		String srcbase = transSeprator(project.getSrcdir());
		return src.replace(srcbase, testbase);
	}

	private String src2name(String path) {
		StringBuffer sb = new StringBuffer(transSeprator(path));
		String basesrc = transSeprator(project.getSrcdir());
		if (path.equals(basesrc)) {
			return null;
		} else {
			sb.delete(0, basesrc.length() + 1);
			// 去除 .js
			if (sb.lastIndexOf(".js") > 0) {
				sb.delete(sb.length() - 3, sb.length());
			}
			String result = sb.toString().replace("/", ".");
			return result;
		}
	}

	private static String transSeprator(String s) {
		return s.replace("\\", "/");
	}
}
