package com.marianowinar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marianowinar.model.Material;
import com.marianowinar.model.forms.Profmaterial;
import com.marianowinar.service.application.MaterialService;
import com.marianowinar.service.application.PersonService;
import com.marianowinar.service.application.ProfessorService;

@Controller
@RequestMapping(value = "/material")
public class MaterialController implements Controllerss<Material>{
	
	@Autowired
	private MaterialService matServ;
	@Autowired
	private PersonService perServ;
	@Autowired
	private ProfessorService profServ;

	@Override
	@GetMapping("/materialControlPanel")
	public String getControlPanel(ModelMap mp) {
		mp.put("materials", matServ.viewAll());
		return "/material/materialControlPanel";
	}

	@Override
	@GetMapping("/createMaterial")
	public String getRegister(Model model) {
		model.addAttribute("material", new Material());
		return "/material/materialControlPanel";
	}

	@Override
	@GetMapping("/takeIdChangeMaterial")
	public String getIdChange(Model model, ModelMap mp) {
		model.addAttribute("material", new Material());
		
		return null;
	}

	@Override
	public String getUpdate(Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdDelete(Model model, ModelMap mp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDelete(Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdAddMaterial(Model model, ModelMap mp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdDeleteMaterial(Model model, ModelMap mp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postRegister(Material entity, BindingResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postTakeChangeProfile(Material entity, BindingResult result, ModelMap mp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postChangeProfile(Material entity, BindingResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postDeleteProfile(Material entity, BindingResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postAddMaterial(Profmaterial entity, BindingResult result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postDeleteMaterial(Profmaterial entity, BindingResult result) {
		// TODO Auto-generated method stub
		return null;
	}

}
