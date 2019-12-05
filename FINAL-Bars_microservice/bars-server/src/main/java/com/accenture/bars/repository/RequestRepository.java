package com.accenture.bars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.accenture.bars.domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{

	@Query("Select b.billingCycle, b.startDate, b.endDate, c.lastName, c.firstName, b.amount "
			+ "from Billing b, Account a, Customer c, Request r "
			+ "where b.account.accountId = a.accountId "
			+ "and a.customer.customerId = c.customerId and b.billingCycle = r.billingCycle and b.startDate = r.startDate "
			+ "and b.endDate = r.endDate")
	List<Object[]> findRecords();

}
