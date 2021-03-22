package com.marianowinar.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marianowinar.model.Professor;
import com.marianowinar.model.enums.PersonType;
import com.marianowinar.model.forms.Profmaterial;
import com.marianowinar.repository.ProfessorRepository;
import com.marianowinar.service.exception.person.InvalidMailException;
import com.marianowinar.service.exception.person.InvalidNamesPersonException;
import com.marianowinar.service.logger.Errors;
import com.marianowinar.service.validator.ValidPersonTypes;

@Service
public class ProfessorService implements Services<Professor>{
	
	@Autowired
	private ProfessorRepository profRepo;
		
	private Errors errors;	
	private ValidPersonTypes validPer;

	public ProfessorService() {
		this.validPer = ValidPersonTypes.getInstance();
		this.errors = Errors.getInstance();
	}

	@Override
	public boolean create(Professor entity) {
		boolean res = false;
		try {
			if(validPer.validProfessor(entity)) {
				entity.setActive(true);
				entity.setType(PersonType.PROFESSOR);
				profRepo.save(entity);
				res = true;
			}
		}catch(InvalidNamesPersonException | InvalidMailException e) {
			errors.logError(e.getMessage());
		}
		return res;
	}

	@Override
	public boolean update(Professor entity) {
		boolean res = false;
		if(create(entity)) res = true;
		
		return res;
	}

	@Override
	public boolean delete(Long id) {
		boolean res = false;
		Professor aux = take(id);
		profRepo.delete(aux);
		
		if(!searchProfessor(aux)) res = true;
		
		return res;
	}

	@Override
	public List<Professor> viewAll() {
		return profRepo.findAll();
	}

	@Override
	public Professor take(Long id) {
		return profRepo.getOne(id);
	}

	/*
	 * GETTERS OBJETCS
	 */

	public Errors getErrors() {
		return errors;
	}

	public ValidPersonTypes getValidPer() {
		return validPer;
	}

	/*
	 * METHODS AND FUCNTIONS	
	 */
	
	/*
	 * Busca si la persona existe o no y devuelve un booleano
	 */
	public boolean searchProfessor(Professor entity) {
		boolean res = false;
		List<Professor> listProf = viewAll();
		for(Professor ele : listProf) {
			if(ele == entity) {
				res = true;
			}
		}
		return res;
	}

	/*
	 * Busca y devuelve un objeto Professor si existe
	 */
	public Professor searchingProfessor(Professor entity) {
		Professor aux = null;
		List<Professor> listProf = viewAll();
		for(Professor ele : listProf) {
			if(ele.getPersonId() == entity.getPersonId()) {
				aux = ele;
			}
		}
		return aux;
	}

	/*
	 * Cambiar los datos de un objeto Professor
	 */
	public boolean changeProfessor(Professor entity) {
		boolean res = false;
		Professor aux = searchingProfessor(entity);
		if(update(aux)) {
			res = true;
		}
		return res;
	}

	/*
	 * Busca objeto Professor con el id para agregar una
	 * materia
	 */
	public Professor searchingProfessor(Profmaterial entity) {
		Professor aux = null;
		List<Professor> listProf = viewAll();
		for(Professor ele : listProf) {
			if(ele.getPersonId() == entity.getPersonId()) {
				aux = ele;
			}
		}
		return aux;
	}

}
