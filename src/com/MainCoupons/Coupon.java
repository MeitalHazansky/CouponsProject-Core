package com.MainCoupons;

import java.util.Date;

public class Coupon {

	private long id;
	private String title ;
	private Date startDate;
	private Date endDate ;
	private int amount ;
	private String messege ;
	private double price ;
	private String image ;
	private CouponType type;

	public Coupon(long id, String title, Date startDate, Date endDate, int amount, String messege, double price,
			String image, CouponType type) {
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.messege = messege;
		this.price = price;
		this.image = image;
		this.type = type;
	}

	public Coupon() {
		
	}
	
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", messege=" + messege + ", price=" + price + ", image=" + image + ", type="
				+ type + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMessege() {
		return messege;
	}

	public void setMessege(String messege) {
		this.messege = messege;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}
	

	
}
