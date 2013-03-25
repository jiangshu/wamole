package com.baidu.jstest.tangram;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.baidu.jstest.dependence.TangramDependStrategy;
import com.baidu.jstest.project.Project;

public class TangramDependenceTest {
//	@Test
//	public void getValues_01() {
//		Project project = new Project();
//		Properties prop = new Properties();
//		project.setSrcdir("F:\\Workplaces\\Workplace\\tangram\\src");
//		TangramDependStry dep = new TangramDependStry(project);
//		dep.parse("F:\\Workplaces\\Workplace\\tangram\\src\\baidu\\ajax\\form.js");
//		Map map = dep.getDependence();
//		Set<String> keys = map.keySet();
//		for (String s : keys) {
//			System.out.println(map.get(s));
//		}
////		Set keySet = prop.keySet();
////		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
////			String str = (String) iterator.next();
////			System.out.println(prop.get(str));
////		}
////		System.out.println(prop.size());
//	}
//	@Test
//	public void test_parse_01(){
//		Project project = new Project();
//		project.setSrcdir("F:\\Workplaces\\Workplace\\tangram-base\\src");
//		project.setBasedir("F:\\Workplaces\\Workplace\\tangram-base");
//		project.setName("tangram");
//		TangramDependStrategy dep = new TangramDependStrategy(project);
//		List<String> list = dep.getDepends("F:\\Workplaces\\Workplace\\Tangram-base\\src\\baidu\\page\\createStyleSheet.js");
//		System.out.println(list.size());
//		Assert.assertEquals(7, list.size());
//		for (String string : list) {
//			System.out.println(string);
//		}
//	}
//	@Test
//	public void test_parse_02(){
//		Project project = new Project();
//		project.setSrcdir("F:\\Workplaces\\Workplace\\tangram-base\\src");
//		project.setBasedir("F:\\Workplaces\\Workplace\\tangram-base");
//		project.setName("tangram");
//		Project depend = new Project();
//		depend.setSrcdir("F:\\Workplaces\\Workplace\\tangram-component\\src");
//		depend.setBasedir("F:\\Workplaces\\Workplace\\tangram-component");
//		depend.setName("tangram-component");
//		depend.setDependProject(project);
//		TangramDependStrategy dep = new TangramDependStrategy(depend);
//		List<String> list = dep.getDepends("F:\\Workplaces\\Workplace\\Tangram-component\\src\\baidu\\ui\\Table.js");
//		System.out.println(list.size());
//		Assert.assertEquals(56, list.size());
//		for (String string : list) {
//			System.out.println(string);
//		}
//	}
//	@Test
//	public void test_parse_03(){
//		Project project = new Project();
//		project.setSrcdir("F:\\Workplaces\\Workplace\\tangram-base\\src");
//		project.setBasedir("F:\\Workplaces\\Workplace\\tangram-base");
//		project.setName("tangram");
//		Project depend = new Project();
//		depend.setSrcdir("F:\\Workplaces\\Workplace\\tangram-component\\src");
//		depend.setBasedir("F:\\Workplaces\\Workplace\\tangram-component");
//		depend.setName("tangram-component");
//		depend.setDependProject(project);
//		TangramDependStrategy dep = new TangramDependStrategy(depend);
//		List<String> list = dep.getDepends("F:\\Workplaces\\Workplace\\Tangram-component\\src\\baidu\\ui\\Carousel\\Carousel$scrollByItem.js");
//		System.out.println(list.size());
////		Assert.assertEquals(56, list.size());
//		for (String string : list) {
//			System.out.println(string);
//		}
//	}
	
	//baidu.ui.Carousel
//	/**
//	 * 有回路
//	 */
//	@Test
//	public void test_parse_03(){
//		Project project = new Project();
//		project.setSrcdir("F:\\Workplaces\\Workplace\\tangram-base\\src");
//		project.setBasedir("F:\\Workplaces\\Workplace\\tangram-base");
//		project.setName("tangram");
//		Project depend = new Project();
//		depend.setSrcdir("F:\\Workplaces\\Workplace\\tangram-component\\src");
//		depend.setBasedir("F:\\Workplaces\\Workplace\\tangram-component");
//		depend.setName("tangram-component");
//		depend.setDependProject(project);
//		TangramDependStrategy dep = new TangramDependStrategy(depend);
//		List<String> list = dep.getDepends("F:\\Workplaces\\Workplace\\Tangram-component\\src\\baidu\\ui\\Tree.js");
//		System.out.println(list.size());
////		Assert.assertEquals(56, list.size());
//		for (String string : list) {
//			System.out.println(string);
//		}
//	}
}
