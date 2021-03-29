package com.marianowinar.controller;


import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.marianowinar.model.forms.Register;
import com.marianowinar.model.forms.Takeid;

@Controller
public interface Controllers{
	
	/*
	 * GET FUNCTIONS
	 */
	public String getRegister(Model model);
	public String getLogin(Model model);
	public String getProfile(Model model, ModelMap mp) throws ServletException, IOException;
	public String getUpdate(Model model, ModelMap mp) throws ServletException, IOException;
	public String getDelete(Model model);
	public String getLogout(Model model);
	
	/*
	 * POST FUNCTIONS
	 */
	public String postRegister(@ModelAttribute Register entity, BindingResult result);
	public String postLogin(@ModelAttribute Register entity, BindingResult result) throws ServletException, IOException;
	public String postProfile(@ModelAttribute Takeid entity, BindingResult result, ModelMap mp);
	public String postChangeProfile(@ModelAttribute Register entity, BindingResult result, ModelMap mp);
	public String postDeleteProfile(@ModelAttribute Takeid entity, BindingResult result);
	public String postLogoutProfile(@ModelAttribute Takeid entity, BindingResult result);
	
}
