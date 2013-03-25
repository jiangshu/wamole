package com.baidu.jstest.exception;


public class TestException extends Exception{

	/**  */
	private static final long serialVersionUID = -5160936842249276512L;
	public TestException() {
		super();
	}
	public TestException(Exception e) {
		super(e);
	}
	public TestException(String msg) {
		super(msg);
	}
	public TestException(String string, Exception exc) {
		super(string + exc.getMessage());
	}

}
