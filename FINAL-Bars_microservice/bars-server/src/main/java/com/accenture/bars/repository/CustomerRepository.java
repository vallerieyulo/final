package com.accenture.bars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.bars.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	List<Customer> findAllByStatus(String status);
	
	
	
}
