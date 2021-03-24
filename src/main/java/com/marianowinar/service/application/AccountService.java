package com.marianowinar.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marianowinar.model.Account;
import com.marianowinar.model.forms.Register;
import com.marianowinar.repository.AccountRepository;
import com.marianowinar.service.exception.account.InvalidPasswordAccountException;
import com.marianowinar.service.exception.account.NullAccountException;
import com.marianowinar.service.logger.Errors;
import com.marianowinar.service.util.PasswordEncryptor;
import com.marianowinar.service.validator.ValidAccount;
import com.marianowinar.service.validator.ValidPass;

@Service
public class AccountService implements Services<Account>{
	
	@Autowired	
	private AccountRepository accRepo;	
	
	private ValidAccount vacc;
	private Errors errors;
	private PasswordEncryptor encryptor;
	private Account acc;
	private ValidPass validatePass;

	public AccountService() {
		this.vacc = ValidAccount.getInstance();
		this.errors = Errors.getInstance();
		this.encryptor = PasswordEncryptor.getInstance();
		this.validatePass = ValidPass.getInstance();
	}

	@Override
	public boolean create(Account entity) {
		boolean res = false;		
		String passEncrypted = "";		
				
		try {
			if(validatePass.validatePass(entity.getPassword())) {
				passEncrypted = encryptor.generateSecurePassword(entity.getPassword());
				entity.setPassword(passEncrypted);
			}
			if(!searchAccount(entity)) {
				if(vacc.validCreateAccount(entity)) {
					entity.setActive(false);
					accRepo.save(entity);
					res = true;
				}
			}
		}catch(NullAccountException | InvalidPasswordAccountException e) {
			errors.logError(e.getMessage());
		}
		return res;
	}

	@Override
	public boolean update(Account entity) {
		boolean res = true;
		accRepo.save(entity);
		return res;
	}

	@Override
	public boolean delete(Long id){
		boolean res = false;
		Account acc = take(id);
		accRepo.deleteById(id);
		
		if(!searchAccount(acc)) res = true;
		
		return res;
	}

	@Override
	public List<Account> viewAll() {		
		return accRepo.findAll();
	}

	@Override
	public Account take(Long id) {
		return accRepo.getOne(id);
	}	

	/*
	 * GETTERS OBJECTS
	 */
	public ValidAccount getVacc() {
		return vacc;
	}

	public Errors getErrors() {
		return errors;
	}

	public PasswordEncryptor getEncryptor() {
		return encryptor;
	}	
	
	public Account getAcc() {
		return acc;
	}
	
	/*
	 * METHODS AND FUNCTIONS
	 */
	
	/*
	 * Busca si la cuenta ya existe en la BD o no
	 */
	public boolean searchAccount(Account acc) {
		boolean res = false;
		List<Account> listAcc = viewAll();
		for(Account ele : listAcc) {
			if(ele.getDni().equals(acc.getDni()) && ele.getLegajo().equals(acc.getLegajo())) {
				res = true;
				break;
			}
		}
		return res;
	}		
	

	/*
	 * Busca una cuenta en la BD
	 * Devuelve la cuenta
	 */
	public Account searchingAccount(Account acc) {
		Account res = null;
		List<Account> listAcc = viewAll();
		for(Account ele : listAcc) {
			if(ele.getDni().equals(acc.getDni()) && ele.getLegajo().equals(acc.getLegajo())) {
				res = ele;
				break;
			}
		}
		return res;
	}	
	
	
	
}
