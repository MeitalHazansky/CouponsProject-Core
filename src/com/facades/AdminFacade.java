package com.facades;

import java.util.ArrayList;
import java.util.Collection;

import com.DBDAO.CompanyDBDAO;
import com.DBDAO.CustomerDBDAO;
import com.MainCoupons.Company;
import com.MainCoupons.Customer;
import com.MainCoupons.UserType;
import com.exceptions.AdminIsNotLoggedIn;
import com.exceptions.CompanyAlreadyExists;
import com.exceptions.CompanyDoesntExist;
import com.exceptions.ConnectionException;
import com.exceptions.CustomerAlreadyExists;
import com.exceptions.CustomerDoesNotExist;
import com.exceptions.DatabaseException;
import com.interfaces.CompanyDAO;
import com.interfaces.CustomerDAO;

public class AdminFacade implements CouponClientFacade {

	private CompanyDAO companyDAO;
	private	CustomerDAO customerDAO;
	private boolean adminLoggedIn;
	
	public AdminFacade() {
		
		this.companyDAO = new CompanyDBDAO();
		this.customerDAO = new CustomerDBDAO();
		this.adminLoggedIn = false;
		
	}
	
	@Override
	public CouponClientFacade login(String name, String password, UserType type) {
		if(name.equals("admin") && password.equals("1234")) {
			this.adminLoggedIn = true;
			return this;
		}
		return null;
	}

	public void createCompany(Company company) throws CompanyAlreadyExists, ConnectionException, DatabaseException, AdminIsNotLoggedIn {
		if(adminLoggedIn)
			companyDAO.createCompany(company);
		else
			throw new AdminIsNotLoggedIn();
	}
	
	public void removeCompany(Company company) throws CompanyDoesntExist, ConnectionException, DatabaseException, AdminIsNotLoggedIn {
		if(adminLoggedIn)	
			companyDAO.removeCompany(company);
		else
			throw new AdminIsNotLoggedIn();
	}
	
	public void removeCompany(long id) throws CompanyDoesntExist, ConnectionException, DatabaseException, AdminIsNotLoggedIn {
		if(adminLoggedIn)
			companyDAO.removeCompany(id);
		else
			throw new AdminIsNotLoggedIn();
	}
	
	public void updateCompany(Company company) throws CompanyDoesntExist, ConnectionException, DatabaseException, AdminIsNotLoggedIn {
		if(adminLoggedIn)
			companyDAO.updateCompany(company);
		else
			throw new AdminIsNotLoggedIn();
	}
	
	public Company getCompany(long id) throws CompanyDoesntExist, ConnectionException, DatabaseException, AdminIsNotLoggedIn {
		if(adminLoggedIn)
			return companyDAO.getCompany(id);
		throw new AdminIsNotLoggedIn();
	}
	
	public ArrayList<Company> getAllCompanies() throws ConnectionException, DatabaseException, AdminIsNotLoggedIn{
		if(adminLoggedIn)
			return companyDAO.getAllCompanies();
		throw new AdminIsNotLoggedIn();
	}
	

	public void createCustomer(Customer customer) throws AdminIsNotLoggedIn, CustomerAlreadyExists, ConnectionException, DatabaseException{
		if(adminLoggedIn)
			customerDAO.createCustomer(customer);
		else
			throw new AdminIsNotLoggedIn();
	}
	

	public void updateCustomer(Customer customer) throws AdminIsNotLoggedIn, CustomerDoesNotExist, ConnectionException, DatabaseException{
		if(adminLoggedIn)
			customerDAO.updateCustomer(customer);
		else
			throw new AdminIsNotLoggedIn();
	}
	

	public void removeCustomer(Customer customer) throws AdminIsNotLoggedIn, CustomerDoesNotExist, ConnectionException, DatabaseException{
		if(adminLoggedIn)
			customerDAO.removeCustomer(customer);
		else
			throw new AdminIsNotLoggedIn();
	}

	public void removeCustomer(long id) throws AdminIsNotLoggedIn, CustomerDoesNotExist, ConnectionException, DatabaseException{
		if(adminLoggedIn)
			customerDAO.removeCustomer(id);
		else
			throw new AdminIsNotLoggedIn();
	}
	

	public Customer getCustomerByID(long id) throws AdminIsNotLoggedIn, CustomerDoesNotExist, ConnectionException, DatabaseException{
		if(adminLoggedIn)
			return customerDAO.getCustomer(id);
		throw new AdminIsNotLoggedIn();
	}

	public Customer getCustomerByName(String customerName) throws AdminIsNotLoggedIn, CustomerDoesNotExist, ConnectionException, DatabaseException{
		if(adminLoggedIn)
			return customerDAO.getCustomer(customerName);
		throw new AdminIsNotLoggedIn();
	}
	

	public Collection<Customer> getAllCustomers() throws AdminIsNotLoggedIn, ConnectionException, DatabaseException{
		if(adminLoggedIn)
			return customerDAO.getAllCustomers();
		throw new AdminIsNotLoggedIn();
	}
	
}
