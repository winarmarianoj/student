package com.marianowinar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.marianowinar.model.Material;
import com.marianowinar.model.forms.Profmaterial;
import com.marianowinar.service.application.MaterialService;
import com.marianowinar.service.application.ProfessorService;

@Controller
@RequestMapping(value = "/material")
public class MaterialController implements Controllerss<Material>{
	
	@Autowired
	private MaterialService matServ;
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
		return "/material/registerMaterial";
	}

	@Override
	@GetMapping("/takeIdChangeMaterial")
	public String getIdChange(Model model, ModelMap mp) {
		model.addAttribute("material", new Material());
		mp.put("materials", matServ.viewAll());
		return "/material/takeChangeMaterial";
	}

	@Override
	@GetMapping("/updateMaterial")
	public String getUpdate(Model model) {
		model.addAttribute("material", new Material());
		return "/material/updateMaterial";
	}

	@Override
	@GetMapping("/takeIdDeleteMaterial")
	public String getIdDelete(Model model, ModelMap mp) {
		model.addAttribute("material", new Material());
		mp.put("materials", matServ.viewAll());
		return "/material/takeDeleteMaterial";
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
	
	

	/*
	 * CREATE MATERIAL
	 */
	@Override
	@PostMapping(value = "/registerMaterial")
	public String postRegister(Material entity, BindingResult result) {
        String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:/material/createMaterial";
		}else {
			
			if(!matServ.searchMaterial(entity)) {
				if(matServ.create(entity)) {
					destiny = "redirect:/material/materialControlPanel";
				}else {
					destiny = "redirect:/material/createMaterial";
				}
				
			}else {
				destiny = "redirect:/professor/createProfessor";
			}
		}
		return destiny;
	}

	/*
	 * TOMA DE LA LISTA EL ID DE LA MATERIA A MODIFICAR
	 */
	@Override
	@PostMapping(value = "/sendIdChangeMaterial")
	public String postTakeChangeProfile(Material entity, BindingResult result, ModelMap mp) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/material/materialControlPanel";
	    }else{
	        Material material = matServ.searchNameMaterial(entity);		        
	        mp.put("material", material);
	        destiny = "redirect:/material/updateMaterial";
	    }
	    return destiny;
	}

	/*
	 * CAMBIO DEL OBJETO MATERIA
	 */
	@Override
	@PostMapping(value = "/changeMaterial")
	public String postChangeProfile(Material entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()){		        
	        destiny = "redirect:/material/materialControlPanel";
	    }else{
	        if(matServ.changeMaterial(entity)) {
	        	destiny = "redirect:/material/materialControlPanel";
	        }else {
	        	destiny = "redirect:/material/takeChangeMaterial";
	        }
	    }
	    return destiny;
	}

	@Override
	@PostMapping(value = "/sendIdDeleteMaterial")
	public String postDeleteProfile(Material entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/material/takeIdDeleteMaterial";
	    }else{
	    	Material material = matServ.searchNameMaterial(entity);	
	        matServ.delete(material.getId());
	        destiny = "redirect:/material/materialControlPanel";
	    }
	    return destiny;
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
