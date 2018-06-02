package com.DBDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.MainCoupons.Company;
import com.MainCoupons.Coupon;
import com.MainCoupons.CouponType;
import com.interfaces.CompanyDAO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.coupons.connector.*;
import com.exceptions.*;

public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPoolSingleton cpl;
	
	public CompanyDBDAO() {
		this.cpl = ConnectionPoolSingleton.getInstance();
	}
	
	
	@Override		
	public void createCompany(Company comp) throws ConnectionException, CompanyAlreadyExists, DatabaseException
	 {
	 
		 	String query = "INSERT INTO company (id, comp_name, password ,email) VALUES (?,?,?,?)";
			PreparedStatement preparedStmt;
			Connection con = null;
			try {
			
		    con = (Connection) cpl.getConnection();
			preparedStmt = (PreparedStatement) con.prepareStatement(query);
			
			preparedStmt.setLong(1, comp.getId());
			preparedStmt.setString(2, comp.getCompName());
			preparedStmt.setString(3, comp.getPassword());
			preparedStmt.setString(4, comp.getEmail());
			preparedStmt.execute(); 
				

		 } catch (SQLException e) {
			if(e.getErrorCode() == 1062) 
				throw new CompanyAlreadyExists();			
			throw new DatabaseException();
		 }  finally {
			 if(con!=null) {
				 cpl.returnConnection(con);
			 }
		 }
				 
	 }
	 
	

	@Override
	public void removeCompany(Company comp) throws ConnectionException, CompanyDoesntExist, DatabaseException{		
		removeCompany(comp.getId());
	}
	
	
	@Override
	public void removeCompany(long id) throws ConnectionException, CompanyDoesntExist, DatabaseException {		
		    String query = "DELETE company_coupon.*, coupon.*, customer_coupon.* "
	 					 + "FROM company_coupon "
	 					 + "INNER JOIN coupon ON coupon.id = company_coupon.coupon_id "
	 					 + "LEFT JOIN customer_coupon ON customer_coupon.coupon_id=company_coupon.coupon_id "
	 					 + "WHERE company_coupon.comp_id=" + id;
			Connection con = null;
			
			try {		
				con = (Connection) cpl.getConnection();
				Statement st = (Statement) con.createStatement();
				st.executeUpdate(query);
				
				query= "DELETE FROM company WHERE id=" + id;   				
				int result = st.executeUpdate(query);

				if(result == 0)
					throw new CompanyDoesntExist();

			} catch (SQLException e) {
				throw new DatabaseException();
			} finally {
				if(con!=null) {
					cpl.returnConnection(con);
				}
			}
	
	 }
	
	
	@Override
	public void updateCompany(Company comp) throws ConnectionException, CompanyDoesntExist, DatabaseException {
		
		String query= "UPDATE company SET password='" + comp.getPassword() + "', email='" + comp.getEmail() + "' WHERE id=" + comp.getId();	
		Connection con = null;
			try {
				con = (Connection) cpl.getConnection();
				Statement st= (Statement) con.createStatement();
				int result = st.executeUpdate(query);
				if(result == 0)
					throw new CompanyDoesntExist();				

				
			} catch (SQLException e) {
				throw new DatabaseException();
			} finally {
				if(con!=null)
					cpl.returnConnection(con);
			}
		
	}
		
	@Override
	public Company getCompany(long id) throws CompanyDoesntExist, DatabaseException, ConnectionException{
	
		Company company = null;
		Connection con = null;
		
		String query = "SELECT * FROM company WHERE id=" + id;
		try {
			
		ArrayList<Coupon> company_coupons = getCoupons(id);			
		Statement st= (Statement) cpl.getConnection().createStatement();		
		ResultSet rs = st.executeQuery(query);
		
		if(rs.first()){
			company = new Company(rs.getLong("id"), rs.getString("comp_name"), rs.getString("password"), rs.getString("email"), company_coupons);
			return company;
		}
		else
			throw new CompanyDoesntExist();
		
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(con!=null)
				cpl.returnConnection(con);
		}
	}
	
	@Override
	public Company getCompany(String name) throws CompanyDoesntExist, DatabaseException, ConnectionException {
	
		Company company = null;
		Connection con = null;
		
		String query = "SELECT * FROM company WHERE comp_name='" + name + "'";
		try {
				
		Statement st= (Statement) cpl.getConnection().createStatement();		
		ResultSet rs = st.executeQuery(query);
		
		if(rs.first()){
			company = new Company(rs.getLong("id"), rs.getString("comp_name"), rs.getString("password"), rs.getString("email"), null);
			return company;
		}
		else
			throw new CompanyDoesntExist();
		
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(con!=null)
				cpl.returnConnection(con);
		}
	}
	
	@Override
	public void companyCreateCoupon(long company_id, Coupon coup) throws ConnectionException, DatabaseException, CouponAlreadyExists {
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

		query = "INSERT INTO company_coupon (comp_id, coupon_id) VALUES (?,?)";
		preparedStmt = (PreparedStatement) con.prepareStatement(query);
		preparedStmt.setLong(1, company_id);
		preparedStmt.setLong(2, coup.getId());
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
	public void companyRemoveCoupon(long company_id, long coupon_id) throws CouponDoesntExist, ConnectionException, DatabaseException {
		String query = "DELETE FROM company_coupon "
				     + "WHERE company_coupon.coupon_id = "+ coupon_id;
		Connection con = null;
		
			try {
				con = (Connection) cpl.getConnection();
				Statement st = (Statement) con.createStatement();
				st.executeUpdate(query);
				
				query = "DELETE customer_coupon.* FROM customer_coupon "
					  + "WHERE customer_coupon.coupon_id = " + coupon_id;
				st.executeUpdate(query);
				
				query= "DELETE FROM coupon WHERE id=" + coupon_id;   				
				int result = st.executeUpdate(query);

				if(result == 0)
					throw new CouponDoesntExist();
				
			} catch (SQLException e) {	
				e.printStackTrace();
				throw new DatabaseException();
			} finally {
				if(con!=null) {
					cpl.returnConnection(con);	
				}
			}
		
	}
	
	@Override
	public void companyUpdateCoupon(long company_id, Coupon coup) throws ConnectionException, DatabaseException, CouponDoesntExist {
		ArrayList<Coupon> companyCoupons = getCoupons(company_id);
		int i = 0, length = companyCoupons.size();
		Coupon couponFound = null;
		while(i < length) {
			if(companyCoupons.get(i).getId() == coup.getId()) {
				couponFound = companyCoupons.get(i);
			}
			i++;
		}
		if(couponFound == null)
			throw new CouponDoesntExist();
		
		String query= "UPDATE coupon "
	    		    + "SET end_date='" + new java.sql.Date(coup.getEndDate().getTime()) + "', "
	    		    + "price=" + coup.getPrice() + " "
	    		    + "WHERE id=" + coup.getId();	
		Connection con = null;
		
	    try {
	    	con = (Connection) cpl.getConnection();
			Statement st= (Statement) con.createStatement();
	        int result = st.executeUpdate(query);
	        
		    if(result == 0)
		    	throw new CouponDoesntExist();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		} finally {
			if(con!=null) {
				cpl.returnConnection(con);
			}
		}
		
	}
	
	@Override	
	public ArrayList<Company> getAllCompanies() throws ConnectionException, DatabaseException {

	    ArrayList<Company> allCompanies = new ArrayList<Company>();
		String query = "SELECT * FROM company";	    
		Connection con = null;
		
		try{
		con = (Connection) cpl.getConnection();
		Statement st = (Statement) con.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while (rs.next()) {
			long id = rs.getLong("id");
			String compName = rs.getString("comp_name");
			String password = rs.getString("password");
			String email = rs.getString("email");
			ArrayList<Coupon> company_coupons = getCoupons(id);
			allCompanies.add(new Company(id, compName, password, email, company_coupons)); 
		}
		
		return allCompanies;
			
		}catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(con!=null)
				cpl.returnConnection(con);
		}		
	}
	   
		
	
	//Gets specific company coupons. If returns null that company has no coupons
	@Override
	public ArrayList<Coupon> getCoupons(long id) throws DatabaseException, ConnectionException {

		Connection con = null;
		String query = "SELECT coupon.* "
				     + "FROM company_coupon "
				      +"INNER JOIN coupon ON company_coupon.coupon_id = coupon.id "
				      +"WHERE company_coupon.comp_id=" + id;		
		ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();
		
		try{
			Statement st = (Statement) cpl.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
			allCoupons.add(new Coupon(rs.getLong("coupon.id"), rs.getString("coupon.title"),
						  new java.util.Date(rs.getDate("coupon.start_date").getTime()), new java.util.Date(rs.getDate("coupon.end_date").getTime()),
						  rs.getInt("coupon.amount"), rs.getString("coupon.message"),
						  rs.getDouble("coupon.price"), rs.getString("coupon.image"),  CouponType.valueOf(rs.getString("coupon.type"))) );
			}			
			return allCoupons;
			
		} catch (SQLException e) {
			
			throw new DatabaseException();
		} finally {
			if(con!=null)
				cpl.returnConnection(con);
		}		
	}


	@Override
	public boolean login(String compName, String password) throws ConnectionException, DatabaseException {
		Connection con = null;
		try {
			Statement st = (Statement) cpl.getConnection().createStatement();	
			ResultSet rs = st.executeQuery("SELECT * FROM company WHERE comp_name='" + compName + "' AND password='" + password + "'");
			if(rs.first())
				return true;			
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException();
		} finally {
			if(con!=null)
				cpl.returnConnection(con);
		}
	}
}

