package com.accenture.bars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accenture.bars.domain.Billing;

public interface BillingRepository extends JpaRepository<Billing, Long>{

	@Query("Select b, c.status, a "
			+ "from Billing b, Account a, Customer c "
			+ "where b.account.accountId = a.accountId "
			+ "and a.customer.customerId = c.customerId")
	List<Object[]> findBillings();
	
}
