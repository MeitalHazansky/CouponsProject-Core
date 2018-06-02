package com.interfaces;

import java.util.ArrayList;

import com.MainCoupons.Coupon;
import com.MainCoupons.Customer;
import com.exceptions.ConnectionException;
import com.exceptions.CouponDoesntExist;
import com.exceptions.CouponOutOfDate;
import com.exceptions.CouponOutOfStock;
import com.exceptions.CustomerAlreadyBroughtCoupon;
import com.exceptions.CustomerAlreadyExists;
import com.exceptions.CustomerDoesNotExist;
import com.exceptions.DatabaseException;

public interface CustomerDAO {


	public void createCustomer(Customer cust) throws ConnectionException, CustomerAlreadyExists, DatabaseException;
	public void removeCustomer(Customer cust) throws CustomerDoesNotExist, ConnectionException, DatabaseException;
	public void removeCustomer(long id) throws CustomerDoesNotExist, ConnectionException, DatabaseException;
	public void updateCustomer(Customer cust) throws CustomerDoesNotExist, ConnectionException, DatabaseException;
	public void purchaseCustomerCoupon(long customer_id, long coupon_id) throws CouponDoesntExist, CouponOutOfStock, CouponOutOfDate, CustomerAlreadyBroughtCoupon, ConnectionException, DatabaseException;
	public Customer getCustomer(long id) throws CustomerDoesNotExist, ConnectionException, DatabaseException;
	public Customer getCustomer(String name) throws CustomerDoesNotExist, ConnectionException, DatabaseException;
	public ArrayList<Customer> getAllCustomers() throws ConnectionException, DatabaseException;
	public ArrayList<Coupon> getCoupons(long id) throws CustomerDoesNotExist, ConnectionException, DatabaseException;
	public boolean login (String custName , String password) throws ConnectionException, DatabaseException;
}
