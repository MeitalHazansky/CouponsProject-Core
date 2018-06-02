package com.facades;

import java.util.Collection;

import com.DBDAO.CouponDBDAO;
import com.MainCoupons.Coupon;
import com.exceptions.ConnectionException;
import com.exceptions.DatabaseException;
import com.interfaces.CouponDAO;

public class Shop {

	private CouponDAO couponDAO;

	public Shop() {
		this.couponDAO = new CouponDBDAO();
	}

	public Collection<Coupon> getAllCoupons() throws ConnectionException, DatabaseException {
		return this.couponDAO.GetAllCoupon();
	}
}
