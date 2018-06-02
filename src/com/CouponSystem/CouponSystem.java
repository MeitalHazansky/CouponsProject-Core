package com.CouponSystem;

import com.MainCoupons.UserType;
import com.coupons.connector.ConnectionPoolSingleton;
import com.exceptions.ConnectionException;
import com.exceptions.DatabaseException;
import com.facades.AdminFacade;
import com.facades.CompanyFacade;
import com.facades.CouponClientFacade;
import com.facades.CustomerFacade;
import com.threads.DailyCouponExpirationTask;

public class CouponSystem {

	private static CouponSystem couponSystem;
	private DailyCouponExpirationTask dailyTask;
	
	private CouponSystem() {
		this.dailyTask = new DailyCouponExpirationTask();
		new Thread(dailyTask).start();
	}
	
	public static CouponSystem getInstance() {
		if(couponSystem==null)
			couponSystem = new CouponSystem();
		return couponSystem;
	}
	
	
	public CouponClientFacade login(String username, String password, UserType userType) throws ConnectionException, DatabaseException {
		switch(userType) {
		case ADMIN: return new AdminFacade().login(username, password, userType);
		case COMPANY: return new CompanyFacade().login(username, password, userType);
		default: return new CustomerFacade().login(username, password, userType);
		}
	}
	
	public void shutDown() throws ConnectionException {
		dailyTask.stopTask();
		ConnectionPoolSingleton.closeAllConnections();	
	}
	
	
	
}
