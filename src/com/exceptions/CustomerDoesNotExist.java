package com.exceptions;

public class CustomerDoesNotExist extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomerDoesNotExist () {
		super("We are sorry but selected customer does not exist in the database");
	}
	
	public CustomerDoesNotExist (String message) {
		super(message);
	}
}
