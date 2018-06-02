package com.exceptions;

public class CouponDoesntExist extends Exception{

	private static final long serialVersionUID = 1L;

	public CouponDoesntExist() {
		super("We are sorry but the coupon you are trying to access does not exist in the database.");
	}
	
	public CouponDoesntExist(String message) {
		super(message);
	}
	
	
}
