package com.marianowinar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marianowinar.model.Users;

public interface AccountRepository extends JpaRepository<Users, Long>{

}
