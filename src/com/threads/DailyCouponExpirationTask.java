package com.threads;

import java.util.ArrayList;

import com.DBDAO.CouponDBDAO;
import com.MainCoupons.Coupon;
import com.exceptions.ConnectionException;
import com.exceptions.CouponDoesntExist;
import com.exceptions.DatabaseException;
import com.interfaces.CouponDAO;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDAO couponDAO;
	private boolean quit;
	private Thread currentThread;

	public DailyCouponExpirationTask() {
		this.couponDAO = new CouponDBDAO();
		this.quit = false;
	}

	@Override
	public void run() {
		currentThread = Thread.currentThread();
		ArrayList<Coupon> coupons = null;
		do {
			try {
				coupons = couponDAO.GetAllCoupon();
			} catch (ConnectionException | DatabaseException e) {
				coupons = null;
			}
			if (coupons != null) {
				int i = 0, length = coupons.size();
				while (i < length) {
					if (coupons.get(i).getEndDate().getTime() <= System.currentTimeMillis()) {
						try {
							couponDAO.removeCoupon(coupons.get(i));
						} catch (ConnectionException | DatabaseException | CouponDoesntExist e) {
							// Nothing needs to be done. Thread can continue safely.
						}
					}
					i++;
				}
			}

			if (!quit) {
				try {
					Thread.sleep(60 * 60 * 24 * 1000);
				} catch (InterruptedException e) {
					return;
				}
			}
		} while (!quit);
	}

	public void stopTask() {
		quit = true;
		currentThread.interrupt();
	}
}
