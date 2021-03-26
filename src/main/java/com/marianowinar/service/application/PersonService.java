package com.marianowinar.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marianowinar.model.Account;
import com.marianowinar.model.Material;
import com.marianowinar.model.Person;
import com.marianowinar.model.enums.PersonType;
import com.marianowinar.model.forms.Forgot;
import com.marianowinar.model.forms.Register;
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.repository.PersonRepository;
import com.marianowinar.service.exception.account.InvalidPasswordAccountException;
import com.marianowinar.service.exception.person.InvalidMailException;
import com.marianowinar.service.exception.person.InvalidNamesPersonException;
import com.marianowinar.service.factory.FactoryEntities;
import com.marianowinar.service.helper.ListPerson;
import com.marianowinar.service.logger.Errors;
import com.marianowinar.service.util.PasswordEncryptor;
import com.marianowinar.service.validator.ValidPass;
import com.marianowinar.service.validator.ValidPersonTypes;

@Service
public class PersonService implements Services<Person>{
	
	@Autowired
	private PersonRepository perRepo;
	
	@Autowired
	private AccountService accServ;
	
	@Autowired
	private MaterialService matServ;
	
	private PasswordEncryptor encryptor;
	private ValidPass validPass;
	private Errors errors;	
	private ValidPersonTypes validPer;
	private Person per;
	private FactoryEntities factory;	
	private ListPerson listPerson;

	public PersonService() {
		this.validPer = ValidPersonTypes.getInstance();
		this.errors = Errors.getInstance();
		this.encryptor = PasswordEncryptor.getInstance();
		this.validPass  = ValidPass.getInstance();
		this.factory = FactoryEntities.getInstance();
		this.per = new Person();
		this.listPerson = new ListPerson();
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
	 * Recibe el objeto forgot y luego procesa con update
	 * devuelve el resultado al controller booleano
	 */	
	public boolean changePasswordPerson(Forgot entity) {
		boolean res = false;
		Person aux = searchPerson(entity);
		
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
	 * Forgot
	 */
	public Person searchPerson(Account entity) {		
		/*
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getAccount().getUserId() == entity.getUserId()){
				this.per = ele;
			}
		}
		*/
		
		for(Person ele : listPerson.getListPerson()) {
			if(ele.getAccount().getDni().equals(entity.getDni())) {
				this.per = ele;
			}
		}
		return this.per;
	}	
	
	/*
	 * Busca si la persona existe o no y devuelve un booleano
	 * Forgot
	 */
	public Person searchPerson(Forgot entity) {
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getAccount().getLegajo().equals(entity.getLegajo())) {
				this.per = ele;
			}
		}
		return this.per;
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
		Account acc = factory.createAccount(entity);
		
		Person per = factory.createPerson(entity);
		per.setType(PersonType.ADMIN);
		
		if(!accServ.searchAccount(acc)) {accServ.create(acc);}		
		if(!searchPerson(per)) {per.setAccount(acc);}		
		
		listPerson.addPerson(per);
		return create(per);
	}
	
	/*
	 * Crea la Cuenta tipo de person Student
	 */
	public boolean createStudent(Register entity) {
		entity.setPassword(encryptor.generateSecurePassword(entity.getPassword()));
		Account acc = factory.createAccount(entity);		
		
		Person per = factory.createPerson(entity);
		per.setType(PersonType.STUDENT);
		
		if(!accServ.searchAccount(acc)) {accServ.create(acc);}		
		if(!searchPerson(per)) {per.setAccount(acc);}		
		
		listPerson.addPerson(per);
		return create(per);
	}

	/*
	 * Busca un objeto Persona con un objeto Register
	 * devuelve objeto Persona
	 */
	public Person searchPerson(Register entity) {		
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getAccount().getDni().equals(entity.getDni()) || ele.getAccount().getLegajo().equals(entity.getLegajo())) {
				this.per = ele;
			}
		}
		return this.per;
	}		

	/*
	 * Delete Person y Account
	 */
	public boolean deleteProfile(Account entity) {		
		Person per = searchPerson(entity);
		return delete(per.getPersonId());
	}

	/*
	 * Devuelve objeto Admin activo
	 */
	public Person searchPersonAdmin() {
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getType().equals(PersonType.ADMIN) &&
					ele.getAccount().isActive()){
				this.per = ele;
			}
		}
		return this.per;
	}

	/*
	 * Devuelve el Estudiante Activo o logueado 
	 */
	public Person searchPersonStudent(String text) {
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getType().equals(PersonType.STUDENT) &&
					ele.getAccount().isActive() &&
					ele.getAccount().getDni().equals(text)){
				this.per = ele;
			}
		}
		return this.per;
	}	

	/*
	 * Inscripciones de Estudiantes a Materias
	 */
	public boolean inscription(Takeid entity) {
		boolean res = false;
		Person student = searchPersonStudent(entity.getText());
		Material material = matServ.searchMaterialName(entity.getNameMaterial());
		int capacity = Integer.parseInt(material.getCapacity());
		int subs = Integer.parseInt(material.getSubscribed());
		
		if(capacity > subs && !notInscripted(student,material)) {
			student.addMaterial(material);
			update(student);
			subs++;
			material.setSubscribed(String.valueOf(subs));
			matServ.update(material);
			res = true;
		}
		return res;
	}

	private boolean notInscripted(Person student, Material material) {		
		return student.searchExistMaterial(material.getName());
	}

}
