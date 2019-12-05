package com.accenture.bars.domain;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Billing Entity
 *
 */
@Entity
public class Billing {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long billingId;
	private int billingCycle;
	private String billingMonth;
	private double amount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
    @JsonBackReference
	private Account account;

	private Date startDate;
	private Date endDate;
	private String lastEdited;

	public Billing() {
	}

	public Billing(int billingCycle, String billingMonth, double amount,
			Account account, Date startDate, Date endDate, String lastEdited) {
		this.billingCycle = billingCycle;
		this.billingMonth = billingMonth;
		this.amount = amount;
		this.account = account;
		this.startDate = startDate;
		this.endDate = endDate;
		this.lastEdited = lastEdited;
	}

	public long getBillingId() {
		return billingId;
	}

	public void setBillingId(long billingId) {
		this.billingId = billingId;
	}

	public int getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(int billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getBillingMonth() {
		return billingMonth;
	}

	public void setBillingMonth(String billingMonth) {
		this.billingMonth = billingMonth;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

	public String getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(String lastEdited) {
		this.lastEdited = lastEdited;
	}

}
