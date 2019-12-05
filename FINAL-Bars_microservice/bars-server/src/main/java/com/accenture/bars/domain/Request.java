package com.accenture.bars.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Request class
 *
 */

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int requestID;
	private int billingCycle;
	private Date startDate;
	private Date endDate;

	public Request() {
	}

	public Request(int requestID, int billingCycle, Date startDate, Date endDate) {
		super();
		this.requestID = requestID;
		this.billingCycle = billingCycle;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
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

}
