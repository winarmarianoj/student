package com.marianowinar.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marianowinar.model.Account;
import com.marianowinar.model.forms.Register;
import com.marianowinar.service.application.PersonService;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
	
	@Autowired
	private PersonService perSer;
	
	@GetMapping("/loginForm")
	public String loginStudent(Model model){
		model.addAttribute("account", new Account());
        return "student/loginStudent";
    }
	
	@GetMapping("/registerStudent")
	public String registerStudent(Model model){
		model.addAttribute("register", new Register());
        return "student/registerStudent";
    }	
	
	@PostMapping(value = "/student")
	public String loginStudent(@ModelAttribute Register entity, BindingResult result, RedirectAttributes redirectAttrs) {
		String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:/register/registerStudent";
		}else {
			destiny = createEntitys(entity, redirectAttrs);
		}
		return destiny;
	}
	
	/*
	 * LOGIN STUDENT
	 */
	@PostMapping(value = "/login")
	public String loginStudent(@ModelAttribute Account entity,BindingResult result, RedirectAttributes redirectAttrs, HttpSession session) {
		String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:student/loginStudent";
		}else{
			//destiny = searching(entity, redirectAttrs,session);			
		}
				
		return destiny;
	}
	
	/*
	 * Funcion que Loguea en true la cuenta del usuario
	
	private String searching(Account entity, RedirectAttributes redirectAttrs, HttpSession session) {
		String destiny = "";		
		
		
		if(perServ.login(entity)) {
			Person per = perServ.searchPerson(entity);
			session.setAttribute("student", per);
			destiny = "redirect:student/menuStudent";			
			redirectAttrs
				.addFlashAttribute("mensage", "Account successfully login!")
				.addFlashAttribute("clase", "success");			
		}
		else {
			redirectAttrs
				.addFlashAttribute("mensage", "Account failed login!")
				.addFlashAttribute("clase", "failed");
			destiny = "redirect:index";
		}
		return destiny;
		
	}	
	 */
	
	/*
	 * Crea la entidad según su tipo y devuelve el destino según su resultado
	 */
	private String createEntitys(Register entity, RedirectAttributes redirectAttrs) {
		String destiny = "";
		
		if(perSer.createStudent(entity)) {
			redirectAttrs
				.addFlashAttribute("menssage", "Account and Person successfully created!")
				.addFlashAttribute("clase", "success");
			destiny = "redirect:/student/loginForm";
		}else {
			redirectAttrs
				.addFlashAttribute("menssage", "Account and Person failed created!")
				.addFlashAttribute("clase", "failed");
			destiny = "redirect:/register/registerStudent";
		}	
		
		return destiny;
	}		

}
