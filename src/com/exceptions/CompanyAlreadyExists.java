package com.exceptions;

public class CompanyAlreadyExists extends Exception{

	private static final long serialVersionUID = 1L;

	public CompanyAlreadyExists() {
		super("We are sorry but company you are trying to create already exists in the database.");
	}
	
	public CompanyAlreadyExists(String message) {
		super(message);
	}
	
}
