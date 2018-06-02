package com.exceptions;

public class CustomerIsNotLoggedIn extends Exception{

	private static final long serialVersionUID = 1L;

	public CustomerIsNotLoggedIn() {
		super("We are sorry but you must log in to use Customer Facade");
	}
	
	public CustomerIsNotLoggedIn(String message) {
		super(message);
	}
}
