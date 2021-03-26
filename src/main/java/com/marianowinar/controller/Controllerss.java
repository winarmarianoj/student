package com.marianowinar.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.marianowinar.model.forms.Profmaterial;
import com.marianowinar.model.forms.Takeid;

public interface Controllerss<T> {
	
	/*
	 * GET FUNCTIONS
	 */
	public String getControlPanel(ModelMap mp);
	public String getRegister(Model model);
	public String getIdChange(Model model, ModelMap mp);
	public String getUpdate(Model model);
	public String getIdDelete(Model model, ModelMap mp);
	public String getIdAddMaterial(Model model, ModelMap mp);
	public String getIdDeleteMaterial(Model model, ModelMap mp);
	public String getIdNameListProfMat(Model model, ModelMap mp);
	public String getListProfMat(Model model, ModelMap mp);
	
	/*
	 * POST FUNCTIONS
	 */
	public String postRegister(T entity, BindingResult result);
	public String postTakeChangeProfile(T entity, BindingResult result, ModelMap mp);
	public String postChangeProfile(T entity, BindingResult result);
	public String postDeleteProfile(T entity, BindingResult result);
	public String postAddMaterial(Profmaterial entity, BindingResult result);
	public String postDeleteMaterial(Profmaterial entity, BindingResult result);
	public String postIdNameListProfMat(Takeid entity, BindingResult result);

}
