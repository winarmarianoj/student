package com.marianowinar.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marianowinar.model.Material;
import com.marianowinar.model.Person;
import com.marianowinar.model.Users;
import com.marianowinar.model.forms.Forgot;
import com.marianowinar.model.forms.Register;
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.repository.PersonRepository;
import com.marianowinar.service.exception.account.InvalidPasswordAccountException;
import com.marianowinar.service.exception.person.InvalidMailException;
import com.marianowinar.service.exception.person.InvalidNamesPersonException;
import com.marianowinar.service.factory.FactoryEntities;
import com.marianowinar.service.logger.Errors;
import com.marianowinar.service.util.PasswordEncryptor;
import com.marianowinar.service.validator.ValidPass;
import com.marianowinar.service.validator.ValidPersonTypes;

@Service
public class PersonService implements Services<Person>{
	
	@Autowired
	private PersonRepository perRepo;
	
	@Autowired
	private UserService accServ;
	
	@Autowired
	private MaterialService matServ;
	
	private PasswordEncryptor encryptor;
	private ValidPass validPass;
	private Errors errors;	
	private ValidPersonTypes validPer;
	private Person per;
	private FactoryEntities factory;	

	public PersonService() {
		this.validPer = ValidPersonTypes.getInstance();
		this.errors = Errors.getInstance();
		this.encryptor = PasswordEncryptor.getInstance();
		this.validPass  = ValidPass.getInstance();
		this.factory = FactoryEntities.getInstance();
		this.per = new Person();
	}

	@Override
	public boolean create(Person entity) {
		boolean res = false;
		try {
			if(validPer.validPerson(entity)) {
				perRepo.save(entity);				
				res = true;
			}
		}catch(InvalidNamesPersonException | InvalidMailException e) {
			errors.logError(e.getMessage());
		}
		return res;
	}

	@Override
	public boolean update(Person entity) {
		return create(entity);
	}

	@Override
	public boolean delete(Long id) {
		Person aux = take(id);
		perRepo.delete(aux);
		return searchPerson(aux);
	}

	@Override
	public List<Person> viewAll() {
		return perRepo.findAll();
	}

	@Override
	public Person take(Long id) {
		return perRepo.getOne(id);
	}

	/*
	 * GETTERS TIPO DE OBJETOS
	 */
	public ValidPersonTypes getVper() {
		return validPer;
	}

	public Errors getErrors() {
		return errors;
	}

	public Person getPer() {
		return per;
	}	
	
	
	/*
	 * METHODS AND FUNCTIONS
	 */
	
		
	/*
	 * LOGIN
	 */
	public boolean login(Register entity) {
		boolean res = false;
		this.per = searchPersonDni(entity.getDni());
		if(!this.per.getAccount().isEnabled()) {
			this.per.getAccount().setEnabled(true);
			if(accServ.update(per.getAccount())) {
				update(this.per);
				res = true;
			}
		}
		return res;
	}	
	
	/*
	 * Recibe el objeto forgot y luego procesa con update
	 * devuelve el resultado al controller booleano
	 */	
	public boolean changePasswordPerson(Forgot entity) {
		boolean res = false;
		Person aux = searchPersonDni(entity.getDni());
		
		try {
			if(validPass.validatePass(entity.getPassword())) {
				aux.getAccount().setPassword(encryptor.generateSecurePassword(entity.getPassword()));
				res = update(aux); 
			}
		}catch(InvalidPasswordAccountException e) {
			errors.logError(e.getMessage());						
		}		
		return res;
	}	
	
	
	
	/*
	 * Busca si la persona existe o no y devuelve un booleano
	 */
	private boolean searchPerson(Person entity) {
		boolean res = false;
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getName().equals(entity.getName()) && ele.getSurname().equals(entity.getSurname())) {
				res = true;
				break;
			}
		}
		return res;
	}
	
	/*
	 * Crea la Cuenta tipo de persona Admin
	 */
	public boolean createAdmin(Register entity) {
		entity.setPassword(encryptor.generateSecurePassword(entity.getPassword()));
		Users acc = factory.createAccount(entity);
		
		Person per = factory.createPerson(entity);
		per.setType("ADMIN");
		
		if(!accServ.searchAccount(acc)) {accServ.create(acc);}		
		if(!searchPerson(per)) {per.setAccount(acc);}		
		
		return create(per);
	}
	
	/*
	 * Crea la Cuenta tipo de person Student
	 */
	public boolean createStudent(Register entity) {
		entity.setPassword(encryptor.generateSecurePassword(entity.getPassword()));
		Users acc = factory.createAccount(entity);
		
		Person per = factory.createPerson(entity);
		per.setType("STUDENT");
		
		if(!accServ.searchAccount(acc)) {accServ.create(acc);}		
		if(!searchPerson(per)) {per.setAccount(acc);}		
		
		return create(per);
	}

	/*
	 * Busca un objeto Persona con un objeto Register
	 * devuelve objeto Persona
	 */
	public Person searchPersonDni(String text) {		
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getDni().equals(text)){
				this.per = ele;
			}
		}
		return this.per;
	}		

	/*
	 * Delete Person y Account
	 */
	public boolean deleteProfile(Takeid entity) {
		boolean res = false;
		Person per = searchPersonDni(entity.getText());
		if(per.getAccount().getUsername().equals(entity.getNameMaterial())) {
			if(delete(per.getPersonId())) {
				res = true;
			}
		}
		return res;
	}
	

	/*
	 * Inscripciones de Estudiantes a Materias
	 */
	public boolean inscription(Takeid entity) {
		boolean res = false;
		Person student = searchPersonDni(entity.getText());
		Material material = matServ.searchMaterialName(entity.getNameMaterial());
		int capacity = Integer.parseInt(material.getCapacity());
		int subs = Integer.parseInt(material.getSubscribed());
		
		if(capacity > subs && !notInscripted(student,material)) {
			if(notSameHour(material)){
				student.addMaterial(material);
				update(student);
				subs++;
				material.setSubscribed(String.valueOf(subs));
				matServ.update(material);
				res = true;
			}
		}
		return res;
	}

	/*
	 * Eliminar una Materia de la lista del Estudiante
	 */
	public boolean unsubscribed(Takeid entity) {
		boolean res = false;
		Person student = searchPersonDni(entity.getText());
		Material material = matServ.searchMaterialName(entity.getNameMaterial());
		int capacity = Integer.parseInt(material.getCapacity());
		int subs = Integer.parseInt(material.getSubscribed());
		
		if(student.removeMaterialName(material.getName())) {
			update(student);
			subs--;
			material.setSubscribed(String.valueOf(subs));
			matServ.update(material);
			res = true;
		}
		return res;
	}
	

	/*
	 * Busca y Cambia Person y Users Admin
	 */
	public boolean changeProfileAdmin(Register entity) {
		boolean res = false;
		Person per = searchPersonDni(entity.getDni());
		per = changePerson(per, entity);
		accServ.create(per.getAccount());
		if(create(per)) res = true;
		
		return res;
	}

	/*
	 * Busca y Cambia Persons y Users Student
	 */
	public boolean changeProfileStudent(Register entity) {
		boolean res = false;
		Person per = searchPersonDni(entity.getDni());
		per = changePerson(per, entity);
		accServ.create(per.getAccount());
		if(create(per)) res = true;
		
		return res;
	}
	
	/*
	 * Setea los cambios en Person y User
	 */
	private Person changePerson(Person per, Register entity) {
		per.setDni(entity.getDni());
		per.setName(entity.getName());
		per.setSurname(entity.getSurname());
		per.setEmail(entity.getEmail());
		per.setPhone(entity.getPhone());
		per.getAccount().setEnabled(true);
		per.getAccount().setUsername(entity.getLegajo());
		per.getAccount().setPassword(entity.getPassword());
		return per;
	}

	/*
	 * Que no este inscrito en esa materia
	 */
	private boolean notInscripted(Person student, Material material) {		
		return student.searchExistMaterial(material);
	}
	
	/*
	 * Que la materia nueva no sea del mismo horario que otra
	 */
	private boolean notSameHour(Material material) {
		boolean res = true;
		for(Material ele : matServ.viewAll()) {
			if(ele.getHour().equals(material.getHour())) {
				res = false;
			}
		}		
		return res;
	}

}
