package com.exceptions;

public class CompanyDoesntExist extends Exception{

	private static final long serialVersionUID = 1L;

	public CompanyDoesntExist() {
		super("We are sorry but this company does not exist in database.");
	}
	
	public CompanyDoesntExist(String message) {
		super(message);
	}
}
