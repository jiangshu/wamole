package com.baidu.jstest.project;

public interface Container<T> extends Cloneable, java.io.Serializable {

	public int size();

	public void addEntity(T entity) throws Exception;

	public T getEntity(String name);

	public boolean contains(T entity);
}
