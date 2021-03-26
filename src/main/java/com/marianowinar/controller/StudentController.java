package com.marianowinar.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.marianowinar.model.Material;
import com.marianowinar.model.Person;
import com.marianowinar.model.Professor;
import com.marianowinar.model.forms.Register;
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.service.application.AccountService;
import com.marianowinar.service.application.MaterialService;
import com.marianowinar.service.application.PersonService;
import com.marianowinar.service.factory.FactoryEntities;

@Controller
@RequestMapping(value = "/student")
public class StudentController implements Controllers{
	
	@Autowired
	private PersonService perServ;
	@Autowired
	private AccountService accServ;
	@Autowired
	private MaterialService matServ;
	
	private FactoryEntities factory;
	private Takeid dniNum;
	
	public StudentController() {
		this.factory = FactoryEntities.getInstance();
		this.dniNum = new Takeid();
	}

	@Override
	@GetMapping("/registerStudent")
	public String getRegister(Model model) {
		model.addAttribute("register", new Register());
        return "/student/registerStudent";
	}

	@Override
	@GetMapping("/loginForm")
	public String getLogin(Model model) {
		model.addAttribute("account", new Account());
		return "/student/loginStudent";
	}

	@Override
	@GetMapping("/profileStudent")
	public String getProfile(Model model, ModelMap mp) {
		model.addAttribute("takeid", new Takeid());	
		Person perInstance = perServ.searchPersonStudent(this.dniNum.getText());
		List<Person> list = new ArrayList<>();
		list.add(perInstance);
		mp.put("persons", list);		
        return "/student/profileStudent";
	}

	@Override
	@GetMapping("/updateStudent")
	public String getUpdate(Model model, ModelMap mp) {
		model.addAttribute("register", new Register());
        Person perInstance = perServ.searchPersonStudent(this.dniNum.getText());
		List<Person> list = new ArrayList<>();
		list.add(perInstance);
		mp.put("persons", list);
        return "/student/updateStudent";
	}

	@Override
	@GetMapping("/deleteStudent")
	public String getDelete(Model model) {
		model.addAttribute("account", new Account());
        return "/student/deleteStudent";
	}

	@Override
	@GetMapping("/logoutStudent")
	public String getLogout(Model model) {
		model.addAttribute("account", new Account());
        return "/student/logoutStudent";
	}
	
	@GetMapping("/inscription")
	public String getInscription(Model model, ModelMap mp) {
		model.addAttribute("takeid", new Takeid());
		mp.put("materials", matServ.viewAll());
        return "/student/inscriptionStudent";
	}
	
	@GetMapping("/listMaterialInscripted")
	public String getInscripcionStudentMat(Model model, ModelMap mp) {
		Person student = perServ.searchPersonStudent(this.dniNum.getText());
		List<Material> listMat = student.getListMaterial();
		mp.put("materials", listMat);
		
		List<Person> list = new ArrayList<>();
		list.add(student);
		mp.put("students", list);
		return "/student/listMaterialStudentInscripted";
	}
	
    /*
     * STUDENT REGISTER
     */
	@Override
	@PostMapping(value = "/student")
	public String postRegister(@ModelAttribute Register entity, BindingResult result) {
        String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:/student/registerStudent";
		}else {
			if(perServ.createStudent(entity)) {
				destiny = "redirect:/student/loginForm";
			}else {			
				destiny = "redirect:/student/registerStudent";
			}	
		}
		return destiny;
	}

	/*
	 * STUDENT LOGIN
	 */
	@Override
	@PostMapping(value = "/login")
	public String postLogin(@ModelAttribute Account entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/student/loginForm";
		}else{			
			accServ.login(entity);
			Account acc = accServ.searchingAccount(entity);			
			Person student = perServ.searchPerson(acc);			
			this.dniNum.setText(entity.getDni());
			destiny = "redirect:/student/profileStudent";					
		}
		return destiny;
	}

	/*
	 * STUDENT PROFILE
	 */
	@Override
	@PostMapping(value = "/profile")
	public String postProfile(@ModelAttribute Takeid entity, BindingResult result, ModelMap mp) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/student/profileStudent";
		}else{			
			this.dniNum.setText(entity.getText());
			destiny = "redirect:/student/updateStudent";		
		}
		return destiny;
	}

	/*
	 * STUDENT CHANGE
	 */
	@Override
	@PostMapping(value = "/changeProfile")
	public String postChangeProfile(@ModelAttribute Register entity, BindingResult result, ModelMap mp) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/student/updateStudent";
		}else{
						
			Person student = perServ.searchPerson(entity);			
			Account acc = factory.changeAccount(student,entity);
			
			student = factory.changePerson(student, entity);
			student.setAccount(acc);
									
			if(accServ.update(student.getAccount())) {
				if(perServ.update(student)) {
					destiny = "redirect:/student/profileStudent";
				}
			}else {				
				destiny= "redirect:/student/updateStudent";
			}		
		}		
		return destiny;
	}

	/*
	 * STUDENT DELETE
	 */
	@Override
	@PostMapping(value = "/deleteProfile")
	public String postDeleteProfile(@ModelAttribute Account entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny = "redirect:/student/profileStudent";
		}else{
			if(perServ.deleteProfile(entity)) {
				destiny = "redirect:/";
			}else {
				destiny = "redirect:/student/profileStudent";
			}
		}		
		return destiny;
	}

	/*
	 * STUDENT LOGOUT
	 */
	@Override
	@PostMapping(value = "/logoutProfile")
	public String postLogoutProfile(@ModelAttribute Account entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny = "redirect:/student/profileStudent";
		}else{						
			accServ.logout(entity);
			destiny = "redirect:/";			
		}		
		return destiny;
	}
	
	/*
	 * STUDENT MATERIAL INSCRIPTION 
	 */
	@PostMapping(value = "/inscription")
	public String postInscription(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny = "redirect:/student/profileStudent";
		}else{	
			
			if(perServ.inscription(entity)) {
				this.dniNum.setText(entity.getText());
				destiny = "redirect:/student/listMaterialInscripted";
			}else {
				destiny = "redirect:/student/profileStudent";
			}						
		}		
		return destiny;
	}
		

}
