package com.exceptions;

public class CustomerAlreadyExists extends Exception{


	private static final long serialVersionUID = 1L;

	public CustomerAlreadyExists() {
		super("We are sorry but customer you are trying to add already exists in the database.");
	}
	
	public CustomerAlreadyExists(String message) {
		super(message);
	}
}
