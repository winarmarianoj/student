package com.marianowinar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marianowinar.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
