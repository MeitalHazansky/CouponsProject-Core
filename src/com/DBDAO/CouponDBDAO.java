package com.DBDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.MainCoupons.Coupon;
import com.MainCoupons.CouponType;
import com.coupons.connector.ConnectionPoolSingleton;
import com.exceptions.ConnectionException;
import com.exceptions.CouponAlreadyExists;
import com.exceptions.CouponDoesntExist;
import com.exceptions.DatabaseException;
import com.interfaces.CouponDAO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class CouponDBDAO implements CouponDAO {

	private ConnectionPoolSingleton cpl;
	
	public CouponDBDAO() {
		this.cpl = ConnectionPoolSingleton.getInstance();
	}
	
	
	@Override
	public void createCoupon(Coupon coup) throws ConnectionException, CouponAlreadyExists, DatabaseException{
		String query = "INSERT INTO coupon (id, title, start_date, end_date, amount, type, message, price, image) VALUES(?,?,?,?,?,?,?,?,?)";
				
		PreparedStatement preparedStmt;
		Connection con = null;		    
		
		try {
			
		con = (Connection) cpl.getConnection();
		preparedStmt = (PreparedStatement) con.prepareStatement(query);
				
		preparedStmt.setLong(1, coup.getId());
		preparedStmt.setString(2, coup.getTitle());
		preparedStmt.setDate(3,  new java.sql.Date(coup.getStartDate().getTime()));
		preparedStmt.setDate(4, new java.sql.Date(coup.getEndDate().getTime()));
		preparedStmt.setInt(5, coup.getAmount());
		preparedStmt.setString(6, coup.getType().name());
		preparedStmt.setString(7, coup.getMessege());
		preparedStmt.setDouble(8, coup.getPrice());
		preparedStmt.setString(9, coup.getImage());
		preparedStmt.execute(); 

	
		
		} catch (SQLException e) {
			if(e.getErrorCode() == 1062) {
				throw new CouponAlreadyExists();
			}
			throw new DatabaseException();
		} finally {
			if(con!=null) {
				cpl.returnConnection(con);	
			}
		}
	}
	
	@Override
	public void removeCoupon(Coupon coup) throws ConnectionException, DatabaseException, CouponDoesntExist {
		removeCoupon(coup.getId());
	}

	/**
	 * This method removes coupons from customer_coupon table and company_coupon table.
	 * After that it removes the coupon itself from the coupon table.
	 */
	@Override
	public void removeCoupon(long coupon_id) throws ConnectionException, DatabaseException, CouponDoesntExist {
		
		Connection con = null;
		String query = "DELETE customer_coupon.*, company_coupon.* "
				     + "FROM customer_coupon "
				     + "INNER JOIN company_coupon ON company_coupon.coupon_id = customer_coupon.coupon_id "
				     + "WHERE company_coupon.coupon_id = "  + coupon_id;
		
		try {
			con = (Connection) cpl.getConnection();
			Statement st = (Statement) con.createStatement();
			st.executeUpdate(query);
			
			query = "DELETE FROM coupon WHERE id=" + coupon_id; 
			int result = st.executeUpdate(query);
		
		if(result == 0)
			throw new CouponDoesntExist() ; 		
	} catch (SQLException e) {
		throw new DatabaseException();
	} finally {
			if(con!=null) {
				cpl.returnConnection(con);
			}
		}
	
	}
	
	@Override
	public void updateCoupon(Coupon coup) throws ConnectionException, CouponDoesntExist, DatabaseException {
    String query= "UPDATE coupon "
    		    + "SET end_date='" + new java.sql.Date((coup.getEndDate().getTime())) + "', "
    		    + "price=" + coup.getPrice() + ", "
    		    + "WHERE id=" + coup.getId();	
	Connection con = null;
	
    try {
    	con = (Connection) cpl.getConnection();
		Statement st= (Statement) con.createStatement();
        int result = st.executeUpdate(query);
        
	    if(result == 0)
	    	throw new CouponDoesntExist();

	} catch (SQLException e) {
		throw new DatabaseException();
	} finally {
		if(con!=null) {
			cpl.returnConnection(con);
		}
	}
  
	}

	@Override
	public Coupon getCoupon(long id) throws ConnectionException, CouponDoesntExist, DatabaseException {
		
    	String query = "SELECT * FROM coupon WHERE id = " + id;
    	Coupon coupon = null;
    	Connection con = null;
    	
    	try {
    	con =  (Connection) cpl.getConnection();
		Statement st= (Statement) con.createStatement();		
		ResultSet rs = st.executeQuery(query);
		
		if(rs.first()){
			coupon=new Coupon(rs.getLong("coupon.id"), rs.getString("coupon.title"),
					 new java.util.Date(rs.getDate("coupon.start_date").getTime()), new java.util.Date(rs.getDate("coupon.end_date").getTime()),
					 rs.getInt("coupon.amount"), rs.getString("coupon.message"),
					 rs.getDouble("coupon.price"), rs.getString("coupon.image"),  CouponType.valueOf(rs.getString("coupon.type")));
			
			return coupon;
		}
		else
			throw new CouponDoesntExist();
		
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(con!=null) {
				cpl.returnConnection(con);
			}
		}
	}
	
	@Override
	public ArrayList<Coupon> GetAllCoupon() throws ConnectionException, DatabaseException {
		
		 	ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();
			String query = "SELECT * FROM coupon";	    
			Connection con = null;
			
			try{
			con = (Connection) cpl.getConnection();
			Statement st = (Statement) con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				
				long id = rs.getLong("id");
				String title = rs.getString("title");
				java.util.Date startDate= new java.util.Date(rs.getDate("start_date").getTime());
				java.util.Date endDate= new java.util.Date(rs.getDate("end_date").getTime());
				int amount= rs.getInt("amount");
				String messege= rs.getString("message") ;
				double price= rs.getDouble("price") ;
				String image= rs.getString("image") ;
				CouponType type= CouponType.valueOf(rs.getString("type"));

				allCoupons.add(new Coupon(id,title,startDate,endDate,amount,messege,price,image,type));
			}
			
			return allCoupons;
			
			} catch (SQLException e) {
				throw new DatabaseException();
			} finally {
				if(con!=null) {
					cpl.returnConnection(con);
				}
		    }
	}

	@Override
	public ArrayList<Coupon> getCouponsByType(CouponType type) throws DatabaseException, ConnectionException {
		
		ArrayList<Coupon> allCoupons= new ArrayList<Coupon>();
		String query = "SELECT * FROM coupon WHERE type='" + type.name() + "'" ;	    
		Connection con = null;
		
		try{
		con = (Connection) cpl.getConnection();
		Statement st = (Statement) con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while (rs.next()) {
			
			long id = rs.getLong("id");
			String title = rs.getString("title");
			java.util.Date startDate= new java.util.Date(rs.getDate("start_date").getTime());
			java.util.Date endDate= new java.util.Date(rs.getDate("end_date").getTime());
			int amount= rs.getInt("amount");
			String messege= rs.getString("messege") ;
			double price= rs.getDouble("price") ;
			String image= rs.getString("image") ;

			allCoupons.add(new Coupon(id, title, startDate, endDate, amount, messege, price, image, type));
		}
		
		return allCoupons;
		
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(con!=null) {
				cpl.returnConnection(con);
			}
	    }
	}

}
