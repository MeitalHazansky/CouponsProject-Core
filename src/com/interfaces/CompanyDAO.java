package com.interfaces;

import java.util.ArrayList;

import com.exceptions.*;
import com.MainCoupons.Company;
import com.MainCoupons.Coupon;

public interface CompanyDAO {

	public void createCompany(Company comp) throws ConnectionException, CompanyAlreadyExists, DatabaseException;
	public void removeCompany(Company comp) throws ConnectionException, CompanyDoesntExist, DatabaseException;
	public void removeCompany(long id) throws ConnectionException, CompanyDoesntExist, DatabaseException;
	public void updateCompany(Company comp) throws ConnectionException, CompanyDoesntExist, DatabaseException;
	public Company getCompany(long id) throws ConnectionException, CompanyDoesntExist, DatabaseException;
	public Company getCompany(String name) throws ConnectionException, CompanyDoesntExist, DatabaseException;
	public void companyCreateCoupon(long company_id, Coupon coup) throws ConnectionException, DatabaseException, CouponAlreadyExists;
	public void companyRemoveCoupon(long company_id, long coupon_id) throws ConnectionException, DatabaseException, CouponDoesntExist;
	public void companyUpdateCoupon(long company_id, Coupon coup) throws ConnectionException, DatabaseException, CouponDoesntExist;
	public ArrayList<Company> getAllCompanies() throws ConnectionException, DatabaseException;
	public ArrayList<Coupon> getCoupons(long id) throws ConnectionException, DatabaseException;
	public boolean login (String compName , String password) throws ConnectionException, DatabaseException;

}
