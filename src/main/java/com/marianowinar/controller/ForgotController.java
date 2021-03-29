package com.marianowinar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marianowinar.model.forms.Forgot;
import com.marianowinar.service.application.PersonService;
import com.marianowinar.service.exception.account.InvalidPasswordAccountException;

@Controller
@RequestMapping(value = "/forgot")
public class ForgotController {

	@Autowired
	private PersonService perServ;
	
	
	@GetMapping("/forgot")
	public String forgot(Model model){
		model.addAttribute("forgot", new Forgot());
        return "/account/forgot";
    }
	
	@PostMapping(value = "/changeForgot")
	public String changeForgot(@ModelAttribute Forgot entity, BindingResult result) throws InvalidPasswordAccountException {
		String destiny = "";		
		if(result.hasErrors()) {
			destiny= "redirect:forgot/forgot";
		}else {
			if(perServ.changePasswordPerson(entity)) {
				
				destiny = "redirect:index"; 
			}else {
				
				destiny = "redirect:forgot/forgot";
			}
		}
		return destiny;
	}	
}
