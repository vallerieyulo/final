package com.accenture.loginserver.loginserver.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AccountRepository interface that contains custom operations
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByUsername(String username);

}