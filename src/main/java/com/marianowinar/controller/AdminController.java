package com.marianowinar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marianowinar.model.Account;
import com.marianowinar.model.Person;
import com.marianowinar.model.forms.Register;
import com.marianowinar.model.forms.Instance;
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.service.application.AccountService;
import com.marianowinar.service.application.PersonService;
import com.marianowinar.service.factory.FactoryEntities;

@Controller
@RequestMapping(value = "/admins")
public class AdminController implements Controllers{
	
	@Autowired
	private PersonService perServ;	
	@Autowired
	private AccountService accServ;	
		
	private FactoryEntities factory;
	private Takeid dniNum;
	private Instance instance;
	
	public AdminController() {
		this.factory  = FactoryEntities.getInstance(); 
		this.dniNum = new Takeid(); 
		this.instance = new Instance(); 
	}
	
	
	/*
	 * GET FUNCTIONS
	 */
	
	@Override
	@GetMapping("/registerAdmin")
	public String getRegister(Model model){
		model.addAttribute("register", new Register());
        return "/admin/registerAdmin";
    }
	
	@Override
	@GetMapping("/loginForm")
	public String getLogin(Model model){
		model.addAttribute("account", new Account());
        return "/admin/loginAdmin";
    }	
	
	@Override
	@GetMapping("/profileAdmin")
	public String getProfile(Model model, ModelMap mp){
		model.addAttribute("takeid", new Takeid());		
		Person perInstance = perServ.searchPersonAdmin();	
		
		List<Person> list = new ArrayList<>();
		list.add(perInstance);
		mp.put("persons", list);		
        return "/admin/profileAdmin";
    }
	
	@Override
	@GetMapping("/updateAdmin")
	public String getUpdate(Model model, ModelMap mp){
        Person perInstance = perServ.searchPersonAdmin();	
		
		List<Person> list = new ArrayList<>();
		list.add(perInstance);
		mp.put("persons", list);
		
		model.addAttribute("register", new Register());
        return "/admin/updateAdmin";
    }
	
	@Override
	@GetMapping("/deleteAdmin")
	public String getDelete(Model model){
		model.addAttribute("account", new Account());
        return "/admin/deleteAdmin";
    }
	
	@Override
	@GetMapping("/logoutAdmin")
	public String getLogout(Model model){
		model.addAttribute("account", new Account());
        return "/admin/logoutAdmin";
    }
	
	
	/*
	 * REGISTER ADMIN
	 */
	@Override
	@PostMapping(value = "/admin")
	public String postRegister(@ModelAttribute Register entity, BindingResult result) {
		String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:/admins/registerAdmin";
		}else {
			if(perServ.createAdmin(entity)) {
				destiny = "redirect:/admins/loginForm";
			}else {			
				destiny = "redirect:/admins/registerAdmin";
			}	
		}
		return destiny;
	}	
	
	/*
	 * LOGIN ADMIN
	 */
	@Override
	@PostMapping(value = "/login")
	public String postLogin(@ModelAttribute Account entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/admins/loginForm";
		}else{			
			accServ.login(entity);
			Account acc = accServ.searchingAccount(entity);			
			Person per = perServ.searchPerson(acc);			
			destiny = "redirect:/admins/profileAdmin";					
		}
		return destiny;
	}		
	
	/*
	 * PROFILE ADMIN
	 */
	@Override
	@PostMapping(value = "/profile")	
	public String postProfile(@ModelAttribute Takeid entity, BindingResult result, ModelMap mp) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/admins/profileAdmin";
		}else{
			this.dniNum.setText(entity.getText());
			destiny = "redirect:/admins/updateAdmin";		
		}
		return destiny;
	}		
	
	/*
	 * CHANGE PROFILE ADMIN
	 */
	@Override
	@PostMapping(value = "/changeProfile")
	public String postChangeProfile(@ModelAttribute Register entity, BindingResult result, ModelMap mp) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/admins/updateAdmin";
		}else{
						
			Person per = perServ.searchPerson(entity);
			
			Account acc = factory.changeAccount(per,entity);
			per.setAccount(acc);
			per = factory.changePerson(per, entity);
									
			if(accServ.update(per.getAccount())) {
				if(perServ.update(per)) {
					destiny = "redirect:/admins/profileAdmin";
				}
			}else {				
				destiny = "redirect:/admins/updateAdmin";
			}		
		}		
		return destiny;
	}		
	
	/*
	 * DELETE PROFILE ADMIN
	 */
	@Override
	@PostMapping(value = "/deleteProfile")
	public String postDeleteProfile(@ModelAttribute Account entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/admins/profileAdmin";
		}else{
			if(perServ.deleteProfile(entity)) {
				destiny = "redirect:/";
			}else {
				destiny= "redirect:/admins/profileAdmin";
			}
		}		
		return destiny;
	}		
	
	/*
	 * LOGOUT PROFILE ADMIN
	 */
	@Override
	@PostMapping(value = "/logoutProfile")
	public String postLogoutProfile(@ModelAttribute Account entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/admins/profileAdmin";
		}else{						
			accServ.logout(entity);
			destiny = "redirect:/";			
		}		
		return destiny;
	}	

}
