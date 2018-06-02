package com.exceptions;

public class CustomerAlreadyBroughtCoupon extends Exception {

	private static final long serialVersionUID = 1L;

	
	public CustomerAlreadyBroughtCoupon() {
		super("We are sorry but you have already brought this coupon.");
	}
	
	public CustomerAlreadyBroughtCoupon(String message) {
		super(message);
	}
}
