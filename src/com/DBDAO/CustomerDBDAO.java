package com.DBDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.MainCoupons.Coupon;
import com.MainCoupons.CouponType;
import com.MainCoupons.Customer;
import com.coupons.connector.ConnectionPoolSingleton;
import com.exceptions.ConnectionException;
import com.exceptions.CouponDoesntExist;
import com.exceptions.CouponOutOfDate;
import com.exceptions.CouponOutOfStock;
import com.exceptions.CustomerAlreadyBroughtCoupon;
import com.exceptions.CustomerAlreadyExists;
import com.exceptions.CustomerDoesNotExist;
import com.exceptions.DatabaseException;
import com.interfaces.CustomerDAO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class CustomerDBDAO implements CustomerDAO {

	private ConnectionPoolSingleton cpl;
	
	public CustomerDBDAO() {
		this.cpl = ConnectionPoolSingleton.getInstance();	
	}
	
	
	@Override
	public void createCustomer(Customer cust) throws CustomerAlreadyExists, ConnectionException, DatabaseException {
		String query = "INSERT INTO customer (id, cust_name, password) VALUES (?,?,?)";
		
		PreparedStatement preparedStmt;
		Connection con = null;
		
		try {
			
		 con = (Connection) cpl.getConnection();
		 preparedStmt = (PreparedStatement) con.prepareStatement(query);
			
		 preparedStmt.setLong(1, cust.getId());
		 preparedStmt.setString(2, cust.getCustName());
		 preparedStmt.setString(3, cust.getPassword());
		 
		 preparedStmt.execute(); 
		 
		} catch (SQLException e) {
				if(e.getErrorCode() == 1062)
						throw new CustomerAlreadyExists();
				throw new DatabaseException();
		} finally {
			if(con!=null)
				cpl.returnConnection(con);
		}
	}

	
	@Override
	public void removeCustomer(Customer cust) throws CustomerDoesNotExist, ConnectionException, DatabaseException {
		removeCustomer(cust.getId());
	}
	
	
	public void removeCustomer(long id) throws CustomerDoesNotExist, ConnectionException, DatabaseException{
		Connection con = null;
		String query = "DELETE FROM customer_coupon WHERE cust_id=" + id;		
		try {
			con = (Connection) cpl.getConnection();	   
			Statement st = (Statement) con.createStatement();
			st.executeUpdate(query);
			query= "DELETE FROM customer WHERE id=" + id;	
			int result = st.executeUpdate(query);
		
			if(result == 0) 
				throw new CustomerDoesNotExist();
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(cpl!=null)
				cpl.returnConnection(con);
		}
	}

	@Override
	public void updateCustomer(Customer cust) throws CustomerDoesNotExist, ConnectionException, DatabaseException  {
		
		String query= "UPDATE customer SET password='" + cust.getPassword()+ "'  WHERE id=" + cust.getId();
		Connection con = null;
		
		try {
			con = (Connection) cpl.getConnection();
			Statement st= (Statement) con.createStatement();
			int result = st.executeUpdate(query);
			if(result == 0)
				throw new CustomerDoesNotExist();			
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(cpl!=null)
				cpl.returnConnection(con);
		}
	}
	
	public void purchaseCustomerCoupon(long customer_id, long coupon_id) throws CouponDoesntExist, CouponOutOfStock, CouponOutOfDate, CustomerAlreadyBroughtCoupon, ConnectionException, DatabaseException {
		String query  = "SELECT * FROM coupon WHERE id="+coupon_id;	
		Connection con = null;
		
		try {
			con = (Connection) cpl.getConnection();
			ResultSet rs = con.prepareStatement(query).executeQuery();
			if(!rs.first())
				throw new CouponDoesntExist();
			int amount = rs.getInt("amount");
			if(amount == 0)
				throw new CouponOutOfStock();
			java.util.Date couponEndDate = new java.util.Date(rs.getDate("end_date").getTime());
			if(couponEndDate.getTime() <=  System.currentTimeMillis())
				throw new CouponOutOfDate();
						
			query = "INSERT INTO customer_coupon (cust_id, coupon_id) VALUES (?, ?)";
			PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
			preparedStmt.setLong(1, customer_id);
			preparedStmt.setLong(2, coupon_id);

			preparedStmt.execute();

			query = "UPDATE coupon SET amount=" + (amount - 1) + " WHERE id=" + coupon_id;
			con.prepareStatement(query).executeUpdate();
		} catch (SQLException e) {
				if(e.getErrorCode() == 1062) 
					throw new CustomerAlreadyBroughtCoupon();			
			throw new DatabaseException();
		} catch (ConnectionException e) {
			throw new ConnectionException();
		} finally {		
				if (con != null)
					cpl.returnConnection(con);			
		}
	}

	@Override
	public Customer getCustomer(long id) throws CustomerDoesNotExist, ConnectionException, DatabaseException {
		
		Customer cust= null;
		String query="SELECT * FROM customer WHERE id = " + id; 
		Connection con = null;
		
		try {	
			con = (Connection) cpl.getConnection();
			Statement st= (Statement) con.createStatement();	
		    ResultSet rs = st.executeQuery(query);
				
		    if(rs.first()){
		    	cust = new Customer(rs.getLong("id"), rs.getString("cust_name"),
		    			rs.getString("password"), null );
		    	return cust;
		    }
		else
			throw new CustomerDoesNotExist();			
	    }catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(cpl!=null)
				cpl.returnConnection(con);
		}
	}
	
	@Override
	public Customer getCustomer(String name) throws CustomerDoesNotExist, ConnectionException, DatabaseException {
		
		Customer cust= null;
		String query="SELECT * FROM customer WHERE cust_name = '" + name + "'"; 
		Connection con = null;
		
		try {	
			con = (Connection) cpl.getConnection();
			Statement st= (Statement) con.createStatement();	
		    ResultSet rs = st.executeQuery(query);
				
		    if(rs.first()){
		    	cust = new Customer(rs.getLong("id"), rs.getString("cust_name"),
		    			rs.getString("password"), null );
		    	return cust;
		    }
		else
			throw new CustomerDoesNotExist();			
	    }catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(cpl!=null)
				cpl.returnConnection(con);
		}
	}
	

	@Override
	public ArrayList<Customer> getAllCustomers() throws ConnectionException, DatabaseException{
	
	    ArrayList<Customer> allCustomers = new ArrayList<Customer>();
		String query = "SELECT * FROM customer";	    
		Connection con = null;		
		try {	
			con = (Connection) cpl.getConnection();
			Statement st= (Statement) con.createStatement();
			ResultSet rs = st.executeQuery(query);
		
			while (rs.next()) {
				long id = rs.getLong("id");
				String custName = rs.getString("cust_name");
				String password = rs.getString("password");
				
				allCustomers.add(new Customer(id, custName, password, null)); 
			}
		
		return allCustomers;
			
		}	catch (SQLException e) {
				throw new DatabaseException();

		}	finally {
				if(cpl!=null)
					cpl.returnConnection(con);
		}		
}

		
	public ArrayList<Coupon> getCoupons(long id) throws CustomerDoesNotExist, ConnectionException, DatabaseException {
		String query = "SELECT coupon.* FROM customer_coupon "
			      	 + "INNER JOIN coupon ON customer_coupon.coupon_id = coupon.id "
			      	 + "WHERE customer_coupon.cust_id=" + id;
	
	ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();
	Connection con = null;		
		try {	
			con = (Connection) cpl.getConnection();
			Statement st= (Statement) con.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
			allCoupons.add(new Coupon(rs.getLong("coupon.id"), rs.getString("coupon.title"),
						  new java.util.Date(rs.getDate("coupon.start_date").getTime()), new java.util.Date(rs.getDate("coupon.end_date").getTime()),
						  rs.getInt("coupon.amount"), rs.getString("coupon.message"),
						  rs.getDouble("coupon.price"), rs.getString("coupon.image"),  CouponType.valueOf(rs.getString("coupon.type"))) );
			}
			
			return allCoupons;
			
			
		}  catch(SQLException e){
			throw new DatabaseException();
		}  finally {
			if(cpl!=null)
				cpl.returnConnection(con);
		}	 
	}



	@Override
	public boolean login(String custName, String password) throws ConnectionException, DatabaseException{
		Connection con = null;
		try {
			con = (Connection) cpl.getConnection();
			Statement st = (Statement) con.createStatement();	
			ResultSet rs = st.executeQuery("SELECT * FROM customer WHERE cust_name='" + custName + "' AND password='" + password + "'");
			if(rs.first()){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new DatabaseException();
		} finally {
			if(cpl!=null)
				cpl.returnConnection(con);
		}
		
	}

}
