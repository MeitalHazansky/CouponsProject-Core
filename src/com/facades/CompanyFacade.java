package com.facades;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.DBDAO.CompanyDBDAO;
import com.MainCoupons.Company;
import com.MainCoupons.Coupon;
import com.MainCoupons.CouponType;
import com.MainCoupons.UserType;
import com.exceptions.CompanyDoesntExist;
import com.exceptions.CompanyIsNotLoggedIn;
import com.exceptions.ConnectionException;
import com.exceptions.CouponAlreadyExists;
import com.exceptions.CouponDoesntExist;
import com.exceptions.DatabaseException;
import com.interfaces.CompanyDAO;

public class CompanyFacade implements CouponClientFacade {

	private CompanyDAO companyDAO;
	private long loggedInCompanyID;
	private Company companyInfo;
	
	public CompanyFacade() {
		this.companyDAO = new CompanyDBDAO();
		this.loggedInCompanyID = -1;
	}
	
	@Override
	public CouponClientFacade login(String name, String password, UserType type) throws ConnectionException, DatabaseException {
		if(companyDAO.login(name, password)) {			
				try {
					this.companyInfo = companyDAO.getCompany(name);
					this.loggedInCompanyID = this.companyInfo.getId();
				} catch (CompanyDoesntExist e) {
					return null;
				}						
			return this;			
		}
		return null;
	}

	public void createCoupon(Coupon coupon) throws CompanyIsNotLoggedIn, CouponAlreadyExists, ConnectionException, DatabaseException {
		if(loggedInCompanyID != -1) {
			companyDAO.companyCreateCoupon(loggedInCompanyID, coupon);
		}
		else
			throw new CompanyIsNotLoggedIn();
		
	}

	public void removeCoupon(Coupon coupon) throws CompanyIsNotLoggedIn, CouponDoesntExist, ConnectionException, DatabaseException{
		if(loggedInCompanyID != -1) {
			companyDAO.companyRemoveCoupon(loggedInCompanyID, coupon.getId());
		}
		else
			throw new CompanyIsNotLoggedIn();
	}

	public void removeCoupon(long id) throws CompanyIsNotLoggedIn, CouponDoesntExist, ConnectionException, DatabaseException{
		if(loggedInCompanyID != -1) {
			companyDAO.companyRemoveCoupon(loggedInCompanyID, id);
		}
		else
			throw new CompanyIsNotLoggedIn();
	}
	

	public void updateCoupon(Coupon coupon) throws CompanyIsNotLoggedIn, CouponDoesntExist, ConnectionException, DatabaseException{
		if(loggedInCompanyID != -1) {
			companyDAO.companyUpdateCoupon(loggedInCompanyID, coupon);
		}
		else
			throw new CompanyIsNotLoggedIn();
	}


	public Coupon getCouponByID(long id) throws CompanyIsNotLoggedIn, ConnectionException, DatabaseException, CouponDoesntExist{
		if(loggedInCompanyID != -1) {
			
			ArrayList<Coupon> companyCoupons = companyDAO.getCoupons(loggedInCompanyID);
			int i = 0, length = companyCoupons.size();
			Coupon couponFound = null;
			while(i < length) {
				if(companyCoupons.get(i).getId() == id) {
					couponFound = companyCoupons.get(i);
				}
				i++;
			}
			if(couponFound == null)
				throw new CouponDoesntExist();
			return couponFound;
		
		}
		throw new CompanyIsNotLoggedIn();
	}
	

	public Collection<Coupon> getAllCoupons() throws CompanyIsNotLoggedIn, ConnectionException, DatabaseException{	
		if(loggedInCompanyID != -1)
			return companyDAO.getCoupons(loggedInCompanyID);
		throw new CompanyIsNotLoggedIn();
	}
	

	public Collection<Coupon> getCouponByType(CouponType couponType) throws CompanyIsNotLoggedIn, ConnectionException, DatabaseException{
		if(loggedInCompanyID != -1) {
			ArrayList<Coupon> coupons = (ArrayList<Coupon>) companyDAO.getCoupons(loggedInCompanyID);
			ArrayList<Coupon> couponsByType = new ArrayList<Coupon>();
			for (int i = 0, size = coupons.size(); i < size; i++) {
				if(coupons.get(i).getType().equals(couponType)) {
					couponsByType.add(coupons.get(i));
				}
			}
			return couponsByType;
		}
		throw new CompanyIsNotLoggedIn();
	}
	
	
	public Collection<Coupon> getCouponsUntilDate(Date date) throws CompanyIsNotLoggedIn, ConnectionException, DatabaseException{
		if(loggedInCompanyID != -1) {
			ArrayList<Coupon> company_coupons = (ArrayList<Coupon>) companyDAO.getCoupons(loggedInCompanyID);
			ArrayList<Coupon> company_coupons_helper = new ArrayList<Coupon>();
			
			for (int i = 0, length = company_coupons.size(); i < length; i++) {
				if(company_coupons.get(i).getEndDate().getTime() <= date.getTime()) {
					company_coupons_helper.add(company_coupons.get(i));
				}
			}
			return company_coupons_helper;		
		}
		throw new CompanyIsNotLoggedIn();
	}
	
	
	public Collection<Coupon> getCouponsUpToPrice(double price) throws CompanyIsNotLoggedIn, ConnectionException, DatabaseException{
		if(loggedInCompanyID != -1) {
			ArrayList<Coupon> company_coupons = (ArrayList<Coupon>) companyDAO.getCoupons(loggedInCompanyID);
			ArrayList<Coupon> company_coupons_helper = new ArrayList<Coupon>();
			
			for (int i = 0, length = company_coupons.size(); i < length; i++) {
				if(company_coupons.get(i).getPrice() <= price ) {
					company_coupons_helper.add(company_coupons.get(i));
				}
			}
			return company_coupons_helper;				
		}
		throw new CompanyIsNotLoggedIn();
	}
	
	public Company getCompanyInfo() throws CompanyIsNotLoggedIn {
		if(loggedInCompanyID != -1) 
			return companyInfo;	
		throw new CompanyIsNotLoggedIn();
	}
	
	
}
