package com.marianowinar.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marianowinar.model.Account;
import com.marianowinar.model.Person;
import com.marianowinar.model.enums.PersonType;
import com.marianowinar.model.forms.Forgot;
import com.marianowinar.model.forms.Register;
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
	private AccountService accServ;
	
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
			//errors.logError(e.getMessage());
			System.out.println(e.getMessage());
		}
		return res;
	}

	@Override
	public boolean update(Person entity) {
		boolean res = false;
		if(create(entity)) res = true;
		
		return res;
	}

	@Override
	public boolean delete(Long id) {
		boolean res = false;
		Person aux = take(id);
		perRepo.delete(aux);
		
		if(!searchPerson(aux)) res = true;
		
		return res;
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
			if(validPass.validatePass(aux.getAccount().getPassword())) {
				String pass = encryptor.generateSecurePassword(aux.getAccount().getPassword());
				aux.getAccount().setPassword(pass);
			}
			
			if(update(aux)) {res = true;}
			
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
		Person aux = null;
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getAccount().getDni().equals(entity.getDni()) && ele.getAccount().getLegajo().equals(entity.getLegajo())) {
				aux = ele;
			}
		}
		return aux;
	}	
	
	/*
	 * Busca si la persona existe o no y devuelve un booleano
	 * Forgot
	 */
	public Person searchPerson(Forgot entity) {
		Person aux = null;
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getAccount().getDni().equals(entity.getDni()) && ele.getAccount().getLegajo().equals(entity.getLegajo())) {
				aux = ele;
			}
		}
		return aux;
	}
	
	/*
	 * Busca si la persona existe o no y devuelve un booleano
	 */
	private boolean searchPerson(Person entity) {
		boolean res = false;
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele == entity) {
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
		boolean res = false;
		
		Account acc = factory.createAccount(entity);
		Person per = factory.createPerson(entity);
		per.setType(PersonType.ADMIN);
		
		if(!accServ.searchAccount(acc)) {
			accServ.create(acc);			
		}
		
		if(!searchPerson(per)) {
			per.setAccount(acc);
			
			if(create(per)) {res = true;}
		}		
		
		return res;
	}
	
	/*
	 * Crea la Cuenta tipo de person Student
	 */
	public boolean createStudent(Register entity) {
		boolean res = false;
		
		Account acc = factory.createAccount(entity);
		Person per = factory.createPerson(entity);
		per.setType(PersonType.STUDENT);
		
		if(!accServ.searchAccount(acc)) {
			accServ.create(acc);			
		}
		
		if(!searchPerson(per)) {
			per.setAccount(acc);
			
			if(create(per)) {res = true;}
		}		
				
		return res;
	}

	/*
	 * Busca un objeto Persona con un objeto Register
	 * devuelve objeto Persona
	 */
	public Person searchPerson(Register entity) {
		Person aux = null;
		List<Person> listPerson = viewAll();
		for(Person ele : listPerson) {
			if(ele.getAccount().getDni().equals(entity.getDni()) || ele.getAccount().getLegajo().equals(entity.getLegajo())) {
				aux = ele;
			}
		}
		return aux;
	}	
	
	/*
	 * Login de la cuenta
	 */
	public Person login(Account entity) {
		Person person = searchPerson(entity);
		person.getAccount().setActive(true);
		person.getAccount().setPassword(encryptor.generateSecurePassword(person.getAccount().getPassword()));
		if(accServ.update(person.getAccount())) {
			update(person);
		}
		return person;
	}

	/*
	 * Logout 
	 */
	public void logout(Account acc) {
		Person per = searchPerson(acc);
		per.getAccount().setActive(false);
		update(per);
	}

	/*
	 * Delete Person y Account
	 */
	public boolean deleteProfile(Account entity) {		
		Person per = searchPerson(entity);
		return delete(per.getPersonId());
	}

}
