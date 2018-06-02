package com.exceptions;

public class CouponAlreadyExists extends Exception {

	private static final long serialVersionUID = 1L;

	public CouponAlreadyExists() {
		super("We are sorry but coupon you are trying to create already exists in the database.");
	}
	
	public CouponAlreadyExists(String message) {
		super(message);
	}
	
	
}
