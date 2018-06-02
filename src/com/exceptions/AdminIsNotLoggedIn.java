package com.exceptions;

public class AdminIsNotLoggedIn extends Exception{

	private static final long serialVersionUID = 1L;

	public AdminIsNotLoggedIn() {
		super("Sorry you must log in as admin to use Admin Facade.");
	}
	
	public AdminIsNotLoggedIn(String message) {
		super(message);
	}
	
}
