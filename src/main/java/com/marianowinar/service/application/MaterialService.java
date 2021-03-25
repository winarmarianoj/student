package com.marianowinar.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marianowinar.model.Material;
import com.marianowinar.model.Person;
import com.marianowinar.model.Professor;
import com.marianowinar.model.forms.Profmaterial;
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.repository.MaterialRepository;
import com.marianowinar.service.exception.material.InvalidCapacityMaterialException;
import com.marianowinar.service.exception.material.InvalidNameMaterialException;
import com.marianowinar.service.exception.person.InvalidMailException;
import com.marianowinar.service.exception.person.InvalidNamesPersonException;
import com.marianowinar.service.logger.Errors;
import com.marianowinar.service.validator.ValidMaterial;

@Service
public class MaterialService implements Services<Material>{
	
	@Autowired
	private MaterialRepository matRepo;
	
	private ValidMaterial vmat;
	private Errors errors;
	private Material material;

	public MaterialService() {
		this.vmat = ValidMaterial.getInstance();
		this.errors = Errors.getInstance();
		this.material = new Material();
	}

	@Override
	public boolean create(Material entity) {
		boolean res = false;
		try {
			if(vmat.validNameMaterial(entity)) {
				matRepo.save(entity);
				res = true;
			}
		}catch(InvalidNameMaterialException | InvalidCapacityMaterialException  e) {
			errors.logError(e.getMessage());
		}
		return res;
	}

	@Override
	public boolean update(Material entity) {
		return create(entity);
	}

	@Override
	public boolean delete(Long id) {
		Material aux = take(id);
		matRepo.delete(aux);
		return searchMaterial(aux);
	}	

	@Override
	public List<Material> viewAll() {
		return matRepo.findAll();
	}

	@Override
	public Material take(Long id) {
		return matRepo.getOne(id);
	}

	/*
	 * GETTERS OBJECTS
	 */
	public ValidMaterial getVmat() {
		return vmat;
	}

	public Errors getErrors() {
		return errors;
	}
	
	/*
	 * METHODS AND FUNCTIONS
	 */

	/*
	 * Busca si una Materia existe o no en la BD
	 */
	public boolean searchMaterial(Material aux) {
		boolean res = false;
		List<Material> listMaterial = viewAll();
		for(Material ele : listMaterial) {
			if(ele.getName().equals(aux.getName())) {
				res = true;
			}
		}
		return res;
	}
	
	/*
	 * Busca y devuelve un objeto Material si existe
	 * para agregar la materia a la lista de un 
	 * objeto professor
	 */
	public Material searchingMaterial(Profmaterial entity) {
		List<Material> listMat = viewAll();
		for(Material ele : listMat) {
			if(ele.getName().equals(entity.getNameMaterial())) {
				this.material = ele;
			}
		}
		return this.material;
	}

	/*
	 * Busca y devuelve un objeto Material segun su nombre
	 */
	public Material searchNameMaterial(Material entity) {
		List<Material> listMat = viewAll();
		for(Material ele : listMat) {
			if(ele.getName().equals(entity.getName())) {
				this.material = ele;
			}
		}
		return this.material;
	}

	/*
	 * Busca el objeto Material e implementa los cambios
	 */
	public boolean changeMaterial(Material entity) {
		boolean res = false;
		List<Material> listMat = viewAll();
		for(Material ele : listMat) {
			if(ele.getId() == entity.getId()) {
				ele = entity;
				res = true;
			}
		}
		return res;
	}
	
	/*
	 * Busca un objeto Material para luego listar los
	 * profesores o estudiantes que tenga asignado
	 */
	public Material searchNameMaterial(Takeid entity) {
		List<Material> listMat = viewAll();
		for(Material ele : listMat) {
			if(ele.getName().equals(entity.getText())) {
				this.material = ele;
			}
		}
		return this.material;
	}
}
