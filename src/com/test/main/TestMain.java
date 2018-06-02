package com.test.main;

public class TestMain {
/*
	private static CouponSystem system;
	
	public static void main(String[] args) {
		system = CouponSystem.getInstance();
		System.out.println("Welcome to the coupon system.");
		System.out.println("Here is the system showcase.");
		System.out.println("Now going through the Admin Facade Procedures: ");
		//adminWorkBench();
		System.out.println("Now going through the Company Facade Procedures: ");
		//companyWorkBench();
		System.out.println("Now going through the Customer Facade Procedures: ");
		customerWorkBench();
		System.out.println("Shutting down system.");
		try {
			system.shutDown();
		} catch (ConnectionException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void adminWorkBench() {
		AdminFacade admin = new AdminFacade();
		System.out.println("Attempting to access Admin Facade methods without doing login");
		try {
			admin.createCompany(null);
		} catch (CompanyAlreadyExists | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
	
		System.out.println("Log in admin facade with bad information.");
		try {
			admin = (AdminFacade) system.login("admin", "12345", UserType.ADMIN);
			System.out.println("Here is admin facade after logging in with bad info:  " + admin);
		} catch (ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Log in admin facade with good information.");
		try {
			admin = (AdminFacade) system.login("admin", "1234", UserType.ADMIN);
			System.out.println("Here is admin facade after logging in with good info: " + admin);
		} catch (ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Attempting to create new company: ");
		System.out.println("Here is information that is going to be created: ");
		Company company = new Company(1l ,"TestCompany", "1234", "testCompany@gmail.com", null);
		System.out.println(company);
		try {
			admin.createCompany(company);
			System.out.println("Company has been created sucessfully.");
		} catch (CompanyAlreadyExists | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Attempting to create same company again: ");
		try {
			admin.createCompany(company);
			System.out.println("Company has been created sucessfully.");
		} catch (CompanyAlreadyExists | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now attempting to remove existing company: ");
		System.out.println("Here is the ID of company that is going to be removed: " + company.getID());
		try {
			admin.removeCompany(company);
			System.out.println("Company has been removed sucessfully.");
		} catch (CompanyDoesntExist | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now attempting to remove same company again: ");
		try {
			admin.removeCompany(company);
			System.out.println("Company has been removed sucessfully.");
		} catch (CompanyDoesntExist | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now attempting to create same company again: ");
		try {
			admin.createCompany(company);
			System.out.println("Company has been created sucessfully.");
		} catch (CompanyAlreadyExists | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now attempting to update same company info: ");
		System.out.println("We are going to change password to this thing - AnfAKDFHAI#(#&$@()");
		company.setPassword("AnfAKDFHAI#(#&$@()");
		try {
			admin.updateCompany(company);
			System.out.println("Company information has been updated sucessfully");
		} catch (CompanyDoesntExist | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now attempting to print the information about company we have updated: ");
		
		try {
			System.out.println(admin.getCompany(company.getID()));
		} catch (CompanyDoesntExist | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to create a number of companies and print their information: ");
		System.out.println("Here are the companies that are going to be added to the database.");
		Company[] companies = new Company[3];
		companies[0] = new Company(3l ,"FastCompany", "123424", "fastCompany@gmail.com", null);
		companies[1] = new Company(457647l ,"Burgers", "1sgs454", "start@gmail.com", null);
		companies[2] = new Company(2356l ,"Kings", "1bdt36][b", "test@gmail.com", null);
		System.out.println(companies[0]);
		System.out.println(companies[1]);
		System.out.println(companies[2]);
		try {
			admin.createCompany(companies[0]);
			System.out.println("Company has been created sucessfully. " + companies[0].getID());
		} catch (CompanyAlreadyExists | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		try {
			admin.createCompany(companies[1]);
			System.out.println("Company has been created sucessfully. " + companies[1].getID());
		} catch (CompanyAlreadyExists | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		try {
			admin.createCompany(companies[1]);
			System.out.println("Company has been created sucessfully. " + companies[2].getID());
		} catch (CompanyAlreadyExists | ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Here is company information taken from the database: ");
		try {
			ArrayList<Company> databaseCompanies = admin.getAllCompanies();
			for (int i = 0; i < databaseCompanies.size(); i++) {
				System.out.println(databaseCompanies.get(i));
			}
		} catch (ConnectionException | DatabaseException | AdminIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to check the customer part of Admin Facade: ");
		System.out.println("Now we are going to create customer: ");
		Customer customer = new Customer(1234l, "TestCustomer", "1234", null);
		System.out.println("Here is information that is going to be inserted :" + customer);
		try {
			admin.createCustomer(customer);
			System.out.println("Customer has been created sucessfully.");
		} catch (AdminIsNotLoggedIn | CustomerAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now we are going to create same customer again: ");
		try {
			admin.createCustomer(customer);
			System.out.println("Customer has been created sucessfully.");
		} catch (AdminIsNotLoggedIn | CustomerAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to remove the customer: ");
		 try {
			admin.removeCustomer(customer);
			System.out.println("Customer has been removed sucessfully.");
		} catch (AdminIsNotLoggedIn | CustomerDoesNotExist | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		 System.out.println("Now we are going to remove same again customer: ");
		 try {
			admin.removeCustomer(customer);
			System.out.println("Customer has been removed sucessfully.");
		} catch (AdminIsNotLoggedIn | CustomerDoesNotExist | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to add same customer again: ");
		try {
			admin.createCustomer(customer);
			System.out.println("Customer has been created sucessfully.");
		} catch (AdminIsNotLoggedIn | CustomerAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now attempting to update same customer info: ");
		System.out.println("We are going to change password to this thing - GKJNPO#_I%{}");
		customer.setPassword("GKJNPO#_I%{}");
		try {
			admin.updateCustomer(customer);
		} catch (AdminIsNotLoggedIn | CustomerDoesNotExist | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now attempting to print the information about customer we have updated: ");
		
		try {
			System.out.println(admin.getCustomerByID(customer.getId()));
		} catch (AdminIsNotLoggedIn | CustomerDoesNotExist | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to create a number of customers and print their information: ");
		System.out.println("Here are the customers that are going to be added to the database.");
		Customer[] customers = new Customer[3];
		customers[0] = new Customer(141414l,"HELLUNLEASHED", "ADASD", null);
		customers[1] = new Customer(7856l,"testSconde", "fjfju", null);
		customers[2] = new Customer(1789l,"jibrish", "tryry5", null);
		System.out.println(customers[0]);
		System.out.println(customers[1]);
		System.out.println(customers[2]);
		try {
			admin.createCustomer(customers[0]);
		} catch (AdminIsNotLoggedIn | CustomerAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		try {
			admin.createCustomer(customers[1]);
		} catch (AdminIsNotLoggedIn | CustomerAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		try {
			admin.createCustomer(customers[2]);
		} catch (AdminIsNotLoggedIn | CustomerAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Here is customers information taken from the database: ");		
		ArrayList<Customer> customersDatabase;
		try {
			customersDatabase = (ArrayList<Customer>) admin.getAllCustomers();			
			for (int i = 0; i < customersDatabase.size(); i++) {
				System.out.println(customersDatabase.get(i));
		}
		} catch (AdminIsNotLoggedIn | ConnectionException | DatabaseException e) {
				System.out.println(e.getMessage());
		}
		System.out.println("Now we are leaving admin workbench and going back to check company workbench.");
		System.out.println("*************************************************************************************************************");
	
	}

	public static void companyWorkBench() {
		CompanyFacade company = new CompanyFacade();
		System.out.println("Attempting to access Company Facade methods without doing login");
		try {
			company.createCoupon(null);
		} catch (CompanyIsNotLoggedIn | CouponAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Log in company facade with bad information.");
		try {
			company = (CompanyFacade) system.login("afodgahg", "fg45ggg43", UserType.COMPANY);
			System.out.println("Here is company facade after logging in with bad info:  " + company);
		} catch (ConnectionException | DatabaseException e) {			
			System.out.println(e.getMessage());
		}
		System.out.println("Log in company facade with good information.");
		try {
			company = (CompanyFacade) system.login("TestCompany", "AnfAKDFHAI#(#&$@()", UserType.COMPANY);
			System.out.println("Here is company facade after logging in with good info:  " + company);
		} catch (ConnectionException | DatabaseException e) {			
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to create new coupon.");
		Coupon coupon = new Coupon(12345l, "TestCoupon", new Date(1518088814000l), new Date(1519557614000l), 20, "Test message", 23.32, "Test image", CouponType.FOOD);
		System.out.println("Here is information about coupon that is going to be created: " + coupon);
		try {
			company.createCoupon(coupon);
			System.out.println("Coupon has been created sucessfully.");
		} catch (CompanyIsNotLoggedIn | CouponAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now we are going to attempt to create same coupon again.");
		try {
			company.createCoupon(coupon);
			System.out.println("Coupon has been created sucessfully.");
		} catch (CompanyIsNotLoggedIn | CouponAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to attempt and remove same coupon from the database: ");
		try {
			company.removeCoupon(coupon);
			System.out.println("Coupon has been removed sucessfully.");
		} catch (CompanyIsNotLoggedIn | CouponDoesntExist | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now we are going to attempt and remove same coupon again : ");
		try {
			company.removeCoupon(coupon);
			System.out.println("Coupon has been removed sucessfully.");
		} catch (CompanyIsNotLoggedIn | CouponDoesntExist | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to attempt to create same coupon again.");
		try {
			company.createCoupon(coupon);
			System.out.println("Coupon has been created sucessfully.");
		} catch (CompanyIsNotLoggedIn | CouponAlreadyExists | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now we are going to update coupon.");
		System.out.println("We are going to set his end date to now and price to 50.40");
		coupon.setEndDate(new Date(System.currentTimeMillis()));
		coupon.setPrice(50.40);
		try {
			company.updateCoupon(coupon);
			System.out.println("Coupon has been updated sucessfully.");
		} catch (CompanyIsNotLoggedIn | CouponDoesntExist | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to get that coupon from the database.");
		try {
			System.out.println(company.getCouponByID(coupon.getID()));			
		} catch (CompanyIsNotLoggedIn | ConnectionException | DatabaseException | CouponDoesntExist e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to create 5 more coupons to that company. ");
		Coupon[] coupons = new Coupon[5];
		coupons[0] = new Coupon(467245l, "GoodCoupon", new Date(1516879214000l), new Date(1523704814000l), 12, "Test message for good coupon", 345.32, "Best image", CouponType.RESTURANS);
		coupons[1] = new Coupon(9678573l, "FastCoupon", new Date(1518261614000l), new Date(1519644014000l), 43, "Test message for faser coupon", 213.54, "Gres", CouponType.ELECTRICITY);
		coupons[2] = new Coupon(432425l, "FrightCoupon", new Date(1517484014000l), new Date(1521026414000l), 15, "Fastest Coupon", 12.54, "Right", CouponType.HEALTH);
		coupons[3] = new Coupon(14512l, "SpecialCoupon", new Date(1515928814000l), new Date(1526210414000l), 0, "Lovely Coupon", 75.24, "Left", CouponType.TRAVELLING);
		coupons[4] = new Coupon(1231414l, "SuperCoupon", new Date(1518520814000l), new Date(1524050414000l), 12, "Brightest Coupon", 986.23, "Upper", CouponType.CAMPING);
		System.out.println("Here is coupons information.");
		for (int i = 0; i < coupons.length; i++) {
			 System.out.println(coupons[i]);
			 try {
				company.createCoupon(coupons[i]);
			} catch (CompanyIsNotLoggedIn | CouponAlreadyExists | ConnectionException | DatabaseException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Now we are going to try and retieve all company coupons from the database: ");
		try {
			ArrayList<Coupon> couponsDataBase = (ArrayList<Coupon>) company.getAllCoupons();
			for (int i = 0; i < couponsDataBase.size(); i++) {
				System.out.println(couponsDataBase.get(i));
			}
		} catch (CompanyIsNotLoggedIn | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to retrive all company coupons of FOOD type.");
		try {
			ArrayList<Coupon> couponsDataBase = (ArrayList<Coupon>) company.getCouponByType(CouponType.FOOD);
			for (int i = 0; i < couponsDataBase.size(); i++) {
				System.out.println(couponsDataBase.get(i));
			}
		} catch (CompanyIsNotLoggedIn | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to retrive all company coupons up to specific price 230.54.");
		try {
			ArrayList<Coupon> couponsDataBase = (ArrayList<Coupon>) company.getCouponsUpToPrice(230.54);
			for (int i = 0; i < couponsDataBase.size(); i++) {
				System.out.println(couponsDataBase.get(i));
			}
		} catch (CompanyIsNotLoggedIn | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to retrive all company coupons up to specific date 2018-04-14.");
		try {
			ArrayList<Coupon> couponsDataBase = (ArrayList<Coupon>) company.getCouponsUntilDate(new Date(1523704814000l));
			for (int i = 0; i < couponsDataBase.size(); i++) {
				System.out.println(couponsDataBase.get(i));
			}
		} catch (CompanyIsNotLoggedIn | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to view all company information: ");
		try {
			System.out.println(company.getCompanyInfo());
		} catch (CompanyIsNotLoggedIn e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Now we are returning to main menu.");
	}
	
	public static void customerWorkBench() {
		CustomerFacade customer = new CustomerFacade();
		System.out.println("Attempting to access customer facade without doing the log in process.");
		try {
			customer.getAllPurchasedCoupons();
		} catch (CustomerIsNotLoggedIn | ConnectionException | DatabaseException | CustomerDoesNotExist e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Attempting to login into the customer Facade.");
		try {
			customer = (CustomerFacade) system.login("TestCustomer", "GKJNPO#_I%{}", UserType.CUSTOMER);
		} catch (ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to attempt to purchase one of the coupons");
		Coupon coupon =  new Coupon(432425l, "FrightCoupon", new Date(1517484014000l), new Date(1521026414000l), 15, "Fastest Coupon", 12.54, "Right", CouponType.HEALTH);
		try {
			customer.purchaseCoupon(coupon);
			System.out.println("Coupon has been purchased sucessfully.");
		} catch (CustomerIsNotLoggedIn | CouponDoesntExist | CouponOutOfStock | CouponOutOfDate
				| CustomerAlreadyBroughtCoupon | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now we are going to try and purhcase same coupon again: ");
		try {
			customer.purchaseCoupon(coupon);
			System.out.println("Coupon has been purchased sucessfully.");
		} catch (CustomerIsNotLoggedIn | CouponDoesntExist | CouponOutOfStock | CouponOutOfDate
				| CustomerAlreadyBroughtCoupon | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now we will try to purchase coupon that doesnt exist");
		coupon =  new Coupon(5474l, "FrightCoupon", new Date(1517484014000l), new Date(1521026414000l), 15, "Fastest Coupon", 12.54, "Right", CouponType.HEALTH);
		try {
			customer.purchaseCoupon(coupon);
			System.out.println("Coupon has been purchased sucessfully.");
		} catch (CustomerIsNotLoggedIn | CouponDoesntExist | CouponOutOfStock | CouponOutOfDate
				| CustomerAlreadyBroughtCoupon | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Now we will try to purchase coupon that is out of stock.");
		coupon = new Coupon(14512l, "SpecialCoupon", new Date(1515928814000l), new Date(1526210414000l), 0, "Lovely Coupon", 75.24, "Left", CouponType.TRAVELLING);
		try {
			customer.purchaseCoupon(coupon);
			System.out.println("Coupon has been purchased sucessfully.");
		} catch (CustomerIsNotLoggedIn | CouponDoesntExist | CouponOutOfStock | CouponOutOfDate
				| CustomerAlreadyBroughtCoupon | ConnectionException | DatabaseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to purchae 3 more coupons to that company. ");
		Coupon[] coupons = new Coupon[3];
		coupons[0] = new Coupon(467245l, "GoodCoupon", new Date(1516879214000l), new Date(1523704814000l), 12, "Test message for good coupon", 345.32, "Best image", CouponType.RESTURANS);
		coupons[1] = new Coupon(9678573l, "FastCoupon", new Date(1518261614000l), new Date(1519644014000l), 43, "Test message for faser coupon", 213.54, "Gres", CouponType.ELECTRICITY);
		coupons[2] = new Coupon(1231414l, "SuperCoupon", new Date(1518520814000l), new Date(1524050414000l), 12, "Brightest Coupon", 986.23, "Upper", CouponType.CAMPING);
		System.out.println("Here is coupons information.");
		for (int i = 0; i < coupons.length; i++) {
			 System.out.println(coupons[i]);			 
				try {
					customer.purchaseCoupon(coupons[i]);
				} catch (CustomerIsNotLoggedIn | CouponDoesntExist | CouponOutOfStock | CouponOutOfDate
						| CustomerAlreadyBroughtCoupon | ConnectionException | DatabaseException e) {
					System.out.println(e.getMessage());
				}
			
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to get all purchased coupons.");
		try {
			ArrayList<Coupon> couponsDataBase = (ArrayList<Coupon>) customer.getAllPurchasedCoupons();
			for (int i = 0; i < couponsDataBase.size(); i++) {
				System.out.println(couponsDataBase.get(i));
			}
		} catch (CustomerIsNotLoggedIn | ConnectionException | DatabaseException | CustomerDoesNotExist e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to get all purchased coupons by type CAMPING.");
		try {
			ArrayList<Coupon> couponsDataBase = (ArrayList<Coupon>) customer.getAllPurchasedCouponsByType(CouponType.CAMPING);
			for (int i = 0; i < couponsDataBase.size(); i++) {
				System.out.println(couponsDataBase.get(i));
			}
		} catch (CustomerIsNotLoggedIn | ConnectionException | DatabaseException | CustomerDoesNotExist e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to get all purchased coupons by price 213.54.");
		try {
			ArrayList<Coupon> couponsDataBase = (ArrayList<Coupon>) customer.getAllPurchasedCouponsByPrice(213.54);
			for (int i = 0; i < couponsDataBase.size(); i++) {
				System.out.println(couponsDataBase.get(i));
			}
		} catch (CustomerIsNotLoggedIn | ConnectionException | DatabaseException | CustomerDoesNotExist e) {
			System.out.println(e.getMessage());
		}
		System.out.println("*************************************************************************************************************");
		System.out.println("Now we are going to get all purchased coupons up to price 230");
		try {
			ArrayList<Coupon> couponsDataBase = (ArrayList<Coupon>) customer.getAllPurchasedCouponUpToPrice(230);
			for (int i = 0; i < couponsDataBase.size(); i++) {
				System.out.println(couponsDataBase.get(i));
			}
		} catch (CustomerIsNotLoggedIn | ConnectionException | DatabaseException | CustomerDoesNotExist e) {
			System.out.println(e.getMessage());
		}
	}
*/
}
