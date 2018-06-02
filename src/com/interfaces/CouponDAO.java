package com.interfaces;

import java.util.ArrayList;


import com.MainCoupons.Coupon;
import com.MainCoupons.CouponType;
import com.exceptions.*;

/**
 * Coupon DAO is interface that is used by all methods that implement Database logics
 * Specifically for the Coupons tables.
 * @author Meital and Roei
 *
 */
public interface CouponDAO {
	
	/**
	 * This methods adds new Coupon to the database. If the title or ID of this coupon already exists throws CouponAlreadyExists exception.
	 * @param coup Coupon that is going to be added to the database.
	 * @throws ConnectionException This exception can be thrown from the ConnectionPoolSingleton method if there has been a problem with connection.
	 * @throws CouponAlreadyExists If the ID or Title of coupon exists in database then this exception is thrown.
	 * @throws DatabaseException Rare exception that can happen if there are problems with the database.
	 */
	public void createCoupon(Coupon coup) throws ConnectionException, CouponAlreadyExists, DatabaseException;
	
	/**
	 * A method that removes coupon with the specified ID from the database.
	 * @param coup Coupon that is going to be removed from the database.
	 * @throws ConnectionException This exception can be thrown from the ConnectionPoolSingleton method if there has been a problem with connection.
	 * @throws DatabaseException Rare exception that can happen if there are problems with the database.
	 * @throws CouponDoesntExist If the coupon doesn't exist in database then this exception is thrown.
	 */
	public void removeCoupon(Coupon coup) throws ConnectionException, DatabaseException, CouponDoesntExist;
	
	/**
	 * A method that removes coupon with the specified ID from the database.
	 * @param coupon_id Coupon with the specified ID that is going to be removed from the database.
	 * @throws ConnectionException This exception can be thrown from the ConnectionPoolSingleton method if there has been a problem with connection.
	 * @throws DatabaseException Rare exception that can happen if there are problems with the database.
	 * @throws CouponDoesntExist If the coupon doesn't exist in database then this exception is thrown.
	 */
	public void removeCoupon(long coupon_id) throws ConnectionException, DatabaseException, CouponDoesntExist;
	
	/**
	 * A method that updates coupon with the specified ID from the database. If coupon doesn't exist throws CouponDoesntExist exception.
	 * @param coup Coupon that is going to be updated in the database.
	 * @throws ConnectionException This exception can be thrown from the ConnectionPoolSingleton method if there has been a problem with connection.
	 * @throws CouponDoesntExist If the coupon doesn't exist in database then this exception is thrown.
	 * @throws DatabaseException Rare exception that can happen if there are problems with the database.
	 */
	public void updateCoupon(Coupon coup) throws ConnectionException, CouponDoesntExist, DatabaseException;
	
	/**
	 * A method that is taking coupon from the coupon database with the specified ID.
	 * @param id ID of coupon that is going to be selected from the database
	 * @return Coupon with the selected ID
	 * @throws ConnectionException This exception can be thrown from the ConnectionPoolSingleton method if there has been a problem with connection.
	 * @throws CouponDoesntExist If the coupon doesn't exist in database then this exception is thrown.
	 * @throws DatabaseException  Rare exception that can happen if there are problems with the database.
	 */
	public Coupon getCoupon(long id) throws ConnectionException, CouponDoesntExist, DatabaseException;
	
	/**
	 * A method that returns all coupons from the database
	 * @return ArrayList<Coupon> coupons that are going to be returned from the database.
	 * @throws ConnectionException This exception can be thrown from the ConnectionPoolSingleton method if there has been a problem with connection.
	 * @throws DatabaseException  Rare exception that can happen if there are problems with the database.
	*/
	public ArrayList<Coupon> GetAllCoupon() throws ConnectionException, DatabaseException;
	
	
	/**
	 * A method the returns all coupons with the specified type from the database.
	 * @param type Coupon Type that we want to return from the database.
	 * @return ArrayList<Coupon> Array List of coupons that is going to be returned from the database
	 * @throws ConnectionException This exception can be thrown from the ConnectionPoolSingleton method if there has been a problem with connection.
	 * @throws DatabaseException  Rare exception that can happen if there are problems with the database.
	 */
	public ArrayList<Coupon> getCouponsByType(CouponType type) throws DatabaseException, ConnectionException;
	
	
}
