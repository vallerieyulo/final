package com.accenture.bars.domain;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Account Entity
 *
 */
@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long accountId;
	private String accountName;
	private Timestamp dateCreated;
	private String isActive;
	private String lastEdited;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
    @JsonBackReference
	private Customer customer;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval=true)
    @JsonManagedReference
	private Set<Billing> billings;

	public Account() {
	}

	public Account(String accountName, Timestamp dateCreated, String isActive,
			String lastEdited, Customer customer, Set<Billing> billings) {
		this.accountName = accountName;
		this.dateCreated = dateCreated;
		this.isActive = isActive;
		this.lastEdited = lastEdited;
		this.customer = customer;
		this.billings = billings;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(String lastEdited) {
		this.lastEdited = lastEdited;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Billing> getBillings() {
		return billings;
	}

	public void setBillings(Set<Billing> billings) {
		this.billings = billings;
	}

}
