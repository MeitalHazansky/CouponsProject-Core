package com.exceptions;

public class CouponOutOfDate extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CouponOutOfDate() {
		super("We are sorry but coupon you are trying to purchase is out of date.");
	}

	public CouponOutOfDate(String message) {
		super(message);
	}
	
	
}
