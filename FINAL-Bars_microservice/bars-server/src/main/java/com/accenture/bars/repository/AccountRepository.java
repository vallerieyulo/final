package com.accenture.bars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.bars.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	List<Account> findAllByIsActive(String isActive);
	
	@Query("Select a, c from Account a, Customer c " + "where a.customer.customerId = c.customerId")
	List<Object[]> findAccounts();
	
	@Transactional
	@Modifying
	@Query("Update Account a set a.isActive = 'N' where a.customer.customerId = :id")
	void updateIsActive(@Param("id") long id);
	
}

