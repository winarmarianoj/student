package com.marianowinar.service.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marianowinar.model.Users;
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.repository.AccountRepository;
import com.marianowinar.service.exception.account.InvalidPasswordAccountException;
import com.marianowinar.service.exception.account.NullAccountException;
import com.marianowinar.service.logger.Errors;
import com.marianowinar.service.validator.ValidAccount;
import com.marianowinar.service.validator.ValidPass;

@Service
public class UserService implements Services<Users>{
	
	@Autowired
    private AccountRepository accRepo;
	
    private ValidAccount vacc = ValidAccount.getInstance();
	private Errors errors = Errors.getInstance();
	private ValidPass validatePass = ValidPass.getInstance();
	
	

	@Override
	public boolean create(Users entity) {
		boolean res = false;
		try {
			if(validatePass.validatePass(entity.getPassword())) {
				if(vacc.validCreateAccount(entity)) {
					accRepo.save(entity);
					res = true;
				}
			}
		}catch(InvalidPasswordAccountException | NullAccountException e) {
			errors.logError(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean update(Users entity) {
		boolean res = false;
		List<Users> listAcc = accRepo.findAll();
		for(Users ele : listAcc) {
			if(ele.getUsername().equals(entity.getUsername())) {
				ele = entity;
				accRepo.save(ele);
				res = true;
				break;
			}
		}
		return res;
	}

	@Override
	public boolean delete(Long id) {
		boolean res = true;
		accRepo.deleteById(id);
		List<Users> listAcc = accRepo.findAll();
		for(Users ele : listAcc) {
			if(ele.getId() == id) {
				res = false;
				break;
			}
		}
		return res;
	}

	@Override
	public List<Users> viewAll() {
		return accRepo.findAll();
	}

	@Override
	public Users take(Long id) {
		return accRepo.getOne(id);
	}
	
	/*
     * METHODS AND FUNCTION
     */

    /*
     * Busca Si existe o no una cuenta
     */
	public boolean searchAccount(Users acc) {
		boolean res = false;
		for(Users ele : viewAll()) {
			if(ele.getUsername().equals(acc.getUsername())) {
				res = true;
				break;
			}
		}
		return res;
	}	
		
	/*
	 * USERS LOGOUT
	 */
	public boolean logout(Takeid entity) {
		boolean res = false;
		for(Users ele : viewAll()) {
			if(ele.getUsername().equals(entity.getNameMaterial())) {
				ele.setEnabled(false);
				accRepo.save(ele);
				res = true;
				break;
			}
		}
		return res;		
	}

}
