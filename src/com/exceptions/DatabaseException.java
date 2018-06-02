package com.exceptions;

public class DatabaseException extends Exception{

	private static final long serialVersionUID = 1L;

	public DatabaseException() {
		super("We are sorry but there is a problem with the database.");
	}
	
	public DatabaseException(String message) {
		super(message);
	}
	
}
