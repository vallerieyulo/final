package com.accenture.bars.domain;

import java.sql.Date;

/**
 * Record - class that contains the elements of each table in bars_db : billing,
 * customer, account Billing table : billing_cycle, start_date, end_date, amount
 * Account table : account_name Customer : last_name, first_name
 *
 */
public class Record {

	private int billingCycle;
	private Date startDate;
	private Date endDate;
	private String customerLastName;
	private String customerFirstName;
	private double amount;

	public Record() {}

	public Record(int billingCycle, Date startDate, Date endDate,
			String customerLastName, String customerFirstName, double amount) {
		this.billingCycle = billingCycle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.customerLastName = customerLastName;
		this.customerFirstName = customerFirstName;
		this.amount = amount;
	}

	public int getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(int billingCycle) {
		this.billingCycle = billingCycle;
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

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
