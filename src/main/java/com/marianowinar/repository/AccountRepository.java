package com.marianowinar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marianowinar.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{	

}
