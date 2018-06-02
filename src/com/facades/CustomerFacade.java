package com.facades;

import java.util.ArrayList;
import java.util.Collection;

import com.DBDAO.CustomerDBDAO;
import com.MainCoupons.Coupon;
import com.MainCoupons.CouponType;
import com.MainCoupons.Customer;
import com.MainCoupons.UserType;
import com.exceptions.ConnectionException;
import com.exceptions.CouponDoesntExist;
import com.exceptions.CouponOutOfDate;
import com.exceptions.CouponOutOfStock;
import com.exceptions.CustomerAlreadyBroughtCoupon;
import com.exceptions.CustomerDoesNotExist;
import com.exceptions.CustomerIsNotLoggedIn;
import com.exceptions.DatabaseException;
import com.interfaces.CustomerDAO;

public class CustomerFacade implements CouponClientFacade {

	private CustomerDAO customerDAO;
	private long loggedInCustomerID;
	private Customer currentCustomer;
	
	public CustomerFacade() {
		this.customerDAO = new CustomerDBDAO();
		this.loggedInCustomerID = -1;
		this.currentCustomer = null;
	}
	
	@Override
	public CouponClientFacade login(String name, String password, UserType type) throws ConnectionException, DatabaseException {
		if(customerDAO.login(name, password)) {
			try {
				this.loggedInCustomerID = customerDAO.getCustomer(name).getId();
				this.currentCustomer = customerDAO.getCustomer(name);
			} catch (CustomerDoesNotExist e) {
				return null;
			}			
			return this;			
		}
		return null;
	}
	
	
	public void purchaseCoupon(Coupon coupon) throws CustomerIsNotLoggedIn, CouponDoesntExist, CouponOutOfStock, CouponOutOfDate, CustomerAlreadyBroughtCoupon, ConnectionException, DatabaseException{
		if(loggedInCustomerID != -1) 
			purchaseCoupon(coupon.getId());
		else
			throw new CustomerIsNotLoggedIn();
		
	}
	
	

	public void purchaseCoupon(long id) throws CustomerIsNotLoggedIn, CouponDoesntExist, CouponOutOfStock, CouponOutOfDate, CustomerAlreadyBroughtCoupon, ConnectionException, DatabaseException{
		if(loggedInCustomerID != -1) {
			customerDAO.purchaseCustomerCoupon(loggedInCustomerID, id);
		}
		else
			throw new CustomerIsNotLoggedIn();
	}
	

	public Collection<Coupon> getAllPurchasedCoupons() throws CustomerIsNotLoggedIn, ConnectionException, DatabaseException, CustomerDoesNotExist{
		if(loggedInCustomerID != -1) 
			return customerDAO.getCoupons(loggedInCustomerID);
		else
			throw new CustomerIsNotLoggedIn();
	}

	

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws CustomerIsNotLoggedIn, ConnectionException, DatabaseException, CustomerDoesNotExist{
		if(loggedInCustomerID != -1) {
			ArrayList<Coupon> customerCoupons = customerDAO.getCoupons(loggedInCustomerID);
			ArrayList<Coupon> customerCouponsToSend = new ArrayList<Coupon>();
			for (int i = 0, length = customerCoupons.size(); i < length; i++) {
				if(customerCoupons.get(i).getType() == couponType)
					customerCouponsToSend.add(customerCoupons.get(i));
			}
			return customerCouponsToSend;						
		}
		else
			throw new CustomerIsNotLoggedIn();
	}


	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws  CustomerIsNotLoggedIn, ConnectionException, DatabaseException, CustomerDoesNotExist{
		if(loggedInCustomerID != -1){
			ArrayList<Coupon> customerCoupons = customerDAO.getCoupons(loggedInCustomerID);
			ArrayList<Coupon> customerCouponsToSend = new ArrayList<Coupon>();
			for (int i = 0, length = customerCoupons.size(); i < length; i++) {
				if(customerCoupons.get(i).getPrice() == price)
					customerCouponsToSend.add(customerCoupons.get(i));
			}
			return customerCouponsToSend;						
		}
		else
			throw new CustomerIsNotLoggedIn();
	}
	

	public Collection<Coupon> getAllPurchasedCouponUpToPrice(double price) throws CustomerIsNotLoggedIn, ConnectionException, DatabaseException, CustomerDoesNotExist{
		if(loggedInCustomerID != -1) {
			ArrayList<Coupon> couponsLoad = (ArrayList<Coupon>) customerDAO.getCoupons(loggedInCustomerID);
			ArrayList<Coupon> couponsSend = new ArrayList<Coupon>();
			for (int i = 0, length = couponsLoad.size(); i < length; i++) 
				if(couponsLoad.get(i).getPrice() <= price)
					couponsSend.add(couponsLoad.get(i));
			
			return couponsSend;
			
		} else
			throw new CustomerIsNotLoggedIn();
	}
	
	public Customer getCurrentCustomer() {
		return this.currentCustomer;
	}
	

}
