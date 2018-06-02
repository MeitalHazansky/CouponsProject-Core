package com.facades;

import com.MainCoupons.UserType;
import com.exceptions.ConnectionException;
import com.exceptions.DatabaseException;

public interface CouponClientFacade {
/**
 * A methods that checks if user with specified user type exists in database.
 * @param name User-name of the user that is going to be checked.
 * @param password Password of the user that is going to be checked.
 * @param type Type of user.
 * @return Returns CouponClientFacade if user exists and if not returns null
 * @throws DatabaseException  If there is a problem with the database   
 * @throws ConnectionException if there is a problem with connection
 */
public CouponClientFacade login(String name, String password, UserType type) throws ConnectionException, DatabaseException;

}
