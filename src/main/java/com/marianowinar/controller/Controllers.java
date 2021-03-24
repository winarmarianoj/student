package com.marianowinar.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.marianowinar.model.Account;
import com.marianowinar.model.Person;
import com.marianowinar.model.forms.Register;

@Controller
public interface Controllers{
	
	/*
	 * GET FUNCTIONS
	 */
	public String getRegister(Model model);
	public String getLogin(Model model);
	public String getProfile(@ModelAttribute Person person,Model model, HttpSession session, ModelMap mp);
	public String getUpdate(Model model);
	public String getDelete(Model model);
	public String getLogout(Model model);
	
	/*
	 * POST FUNCTIONS
	 */
	public String postRegister(@ModelAttribute Register entity, BindingResult result); 
	public String postLogin(@ModelAttribute Account entity, BindingResult result, HttpSession session); 
	public String postProfile(@ModelAttribute Account entity, BindingResult result, ModelMap mp);
	public String postChangeProfile(@ModelAttribute Register entity, BindingResult result, ModelMap mp, HttpSession session); 
	public String postDeleteProfile(@ModelAttribute Account entity, BindingResult result, HttpSession session); 
	public String postLogoutProfile(@ModelAttribute Account entity, BindingResult result, HttpSession session); 
	
}
