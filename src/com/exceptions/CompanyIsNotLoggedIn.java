package com.exceptions;

public class CompanyIsNotLoggedIn extends Exception{

	private static final long serialVersionUID = 1L;

	public CompanyIsNotLoggedIn() {
		super("We are sorry but you must log in to use Company Facade.");
	}
	
	public CompanyIsNotLoggedIn(String message) {
		super(message);
	}
	
}
