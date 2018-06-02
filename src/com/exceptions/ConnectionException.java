package com.exceptions;

public class ConnectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConnectionException() {
		super("We are sorry but there is a problem with the connection.");
	}
	
	public ConnectionException(String message){
		super(message);
	}
}
