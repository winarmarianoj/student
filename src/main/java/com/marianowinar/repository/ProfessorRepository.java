package com.marianowinar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marianowinar.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>{

}
