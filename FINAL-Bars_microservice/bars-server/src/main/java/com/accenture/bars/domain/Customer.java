package com.accenture.bars.domain;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Customer Entity
 *
 */
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long customerId;
	private String firstName;
	private String lastName;
	private String address;
	private String status;
	private Timestamp dateCreated;
	private String lastEdited;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval=true)
    @JsonManagedReference
	private Set<Account> accounts;

	public Customer() {
	}

	public Customer(String firstName, String lastName, String address,
			String status, Timestamp dateCreated, String lastEdited,
			Set<Account> accounts) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.status = status;
		this.dateCreated = dateCreated;
		this.lastEdited = lastEdited;
		this.accounts = accounts;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(String lastEdited) {
		this.lastEdited = lastEdited;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

}
