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
import com.marianowinar.model.Professor;
import com.marianowinar.model.forms.Profmaterial;
import com.marianowinar.service.application.MaterialService;
import com.marianowinar.service.application.ProfessorService;

@Controller
@RequestMapping(value = "/professor")
public class ProfessorController implements Controllerss<Professor>{
	
	@Autowired
	private ProfessorService profServ;	
	
	@Autowired
	private MaterialService matServ;
		
	@Override
	@GetMapping("/professorControlPanel")
	public String getControlPanel(ModelMap mp) {
		mp.put("professors", profServ.viewAll());
		return "/professor/professorControlPanel";
	}

	@Override
	@GetMapping("/createProfessor")
	public String getRegister(Model model) {
		model.addAttribute("professor", new Professor());
		return "/professor/registerProfessor";
	}
	
	@Override
	@GetMapping("/takeIdChangeProfessor")
	public String getIdChange(Model model, ModelMap mp) {
		model.addAttribute("professor", new Professor());
		mp.put("professors", profServ.viewAll());
		return "/professor/takeChangeProfessor";
	}

	@Override
	@GetMapping("/updateProfessor")
	public String getUpdate(Model model) {
		model.addAttribute("professor", new Professor());
		return "/professor/updateProfessor";
	}
	
	@Override
	@GetMapping("/takeIdDeleteProfessor")
	public String getIdDelete(Model model, ModelMap mp) {
		model.addAttribute("professor", new Professor());
		mp.put("professors", profServ.viewAll());
		return "/professor/takeDeleteProfessor";
	}
	

	@Override
	@GetMapping("/deleteProfessor")
	public String getDelete(Model model) {
		model.addAttribute("professor", new Professor());
		return "/professor/deleteProfessor";
	}	
	
	@Override
	@GetMapping("/takeIdAddMaterialProfessor")
	public String getIdAddMaterial(Model model, ModelMap mp) {
		model.addAttribute("professor", new Professor());
		mp.put("materials", matServ.viewAll());
		return "/professor/takeIdAddMaterialProfessor";
	}
	
	@Override
	@GetMapping("/takeIdDeleteMaterialProfessor")
	public String getIdDeleteMaterial(Model model, ModelMap mp) {
		model.addAttribute("professor", new Professor());
		mp.put("materials", matServ.viewAll());
		return "/professor/takeIdDeleteMaterialProfessor";
	}
	
	/*
	 * CREATE PROFESSOR
	 */
	@Override
	@PostMapping(value = "/registerProfessor")
	public String postRegister(Professor entity, BindingResult result) {
		String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:/professor/createProfessor";
		}else {
			if(!profServ.searchProfessor(entity)) {
				if(profServ.create(entity)) {
					destiny = "redirect:/professor/professorControlPanel";
				}else {
					destiny = "redirect:/professor/createProfessor";
				}
				
			}else {
				destiny = "redirect:/professor/createProfessor";
			}
		}
		return destiny;
	}
	
	/*
	 * DE LA LISTA ELIGE EL ID DEL PROFESOR A MODIFICAR
	 * VA A LA VISTA DE UPDATE PROFESSOR
	 */
	@Override
	@PostMapping(value = "/sendIdChangeProfessor")
	public String postTakeChangeProfile(Professor entity, BindingResult result, ModelMap mp) {	
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/professor/professorControlPanel";
	    }else{
	        Professor professor = profServ.searchingProfessor(entity);		        
	        mp.put("professor", professor);
	        destiny = "redirect:/professor/updateProfessor";
	    }
	    return destiny;
	}

	/*
	 * CAMBIA LOS DATOS DE UN OBJETO PROFESSOR
	 */
	@Override
	@PostMapping(value = "/changeProfessor")
	public String postChangeProfile(Professor entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()){		        
	        destiny = "redirect:/professor/professorControlPanel";
	    }else{
	        if(profServ.changeProfessor(entity)) {
	        	destiny = "redirect:/professor/professorControlPanel";
	        }else {
	        	destiny = "redirect:/professor/takeChangeProfessor";
	        }
	    }
	    return destiny;
	}
		
	/*
	 * DE LA LISTA ELIGE EL ID DEL PROFESOR A ELIMINAR
	 * VA A LA VISTA DE DELETE PROFESSOR
	 */
	@Override
	@PostMapping(value = "/sendIdDeleteProfessor")
	public String postDeleteProfile(Professor entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/professor/takeIdDeleteProfessor";
	    }else{
	        profServ.delete(entity.getPersonId());
	        destiny = "redirect:/professor/professorControlPanel";
	    }
	    return destiny;
	}
	
	
	/*
	 * AGREGA UNA MATERIA A LA LISTA DE UN PROFESSOR
	 */
	@Override
	@PostMapping(value = "/addMaterialProfessor")
	public String postAddMaterial(Profmaterial entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/professor/takeIdAddMaterialProfessor";
	    }else{
	        Material mat = matServ.searchingMaterial(entity);
	        Professor prof = profServ.searchingProfessor(entity);
	        prof.addMateial(mat);
	        if(profServ.update(prof)) {
	        	destiny = "redirect:/professor/professorControlPanel";
	        }else {
	        	destiny = "redirect:/professor/takeIdAddMaterialProfessor";
	        }
	    }
	    return destiny;
	}
	
	/*
	 * ELIMINA UNA MATERIA DE LA LISTA DE UN PROFESSOR
	 */
	@Override
	@PostMapping(value = "/deleteMaterialProfessor")
	public String postDeleteMaterial(Profmaterial entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/professor/takeIdAddMaterialProfessor";
	    }else{
	        Material mat = matServ.searchingMaterial(entity);
	        Professor prof = profServ.searchingProfessor(entity);
	        if(prof.removeMaterial(entity.getMaterialId())) {
	        	if(profServ.update(prof)) {
		        	destiny = "redirect:/professor/professorControlPanel";
		        }else {
		        	destiny = "redirect:/professor/takeIdAddMaterialProfessor";
		        }
	        }
	    }
	    return destiny;
	}
}
