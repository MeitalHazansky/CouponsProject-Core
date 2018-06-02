package com.exceptions;

public class CouponOutOfStock extends Exception{

	private static final long serialVersionUID = 1L;

	public CouponOutOfStock() {
		super("We are sorry but coupon you are trying to purchase is out of stock.");
	}
	
	public CouponOutOfStock(String message) {
		super(message);
	}
	
}
