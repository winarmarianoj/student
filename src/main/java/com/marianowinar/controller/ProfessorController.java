package com.marianowinar.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.marianowinar.model.Material;
import com.marianowinar.model.Professor;
import com.marianowinar.model.forms.Profmaterial;
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.service.application.MaterialService;
import com.marianowinar.service.application.ProfessorService;

@Controller
@RequestMapping(value = "/professor")
public class ProfessorController implements Controllerss<Professor>{
		
	private Takeid takeid;
	
	public ProfessorController() {this.takeid = new Takeid();}
	
	@Autowired
	private ProfessorService profServ;	
	
	@Autowired
	private MaterialService matServ;
	
	
	/*
	 * Respuestas a lo largo de sus operaciones
	 */
	@GetMapping("/responseProfessor")
	public String getResponse(ModelMap mp) {
		List<Takeid> list = new ArrayList<>();
		list.add(this.takeid);
		mp.put("takeids", list);
        return "/professor/respProfessor";
	}
		
	@Override
	@GetMapping("/professorControlPanel")
	public String getControlPanel(ModelMap mp) {
		mp.put("professors", profOrdenar());
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
		model.addAttribute("takeid", new Takeid());
		mp.put("professors", profOrdenar());
		return "/professor/takeChangeProfessor";
	}

	@Override
	@GetMapping("/updateProfessor")
	public String getUpdate(Model model, ModelMap mp) {
		model.addAttribute("professor", new Professor());
		Professor professor = profServ.searchingProfessor(this.takeid);	
		List<Professor> list = new ArrayList<>();
		list.add(professor);
        mp.put("professors", list);
		return "/professor/updateProfessor";
	}
	
	@Override
	@GetMapping("/takeIdDeleteProfessor")
	public String getIdDelete(Model model, ModelMap mp) {
		model.addAttribute("takeid", new Takeid());
		mp.put("professors", profOrdenar());
		return "/professor/takeDeleteProfessor";
	}

	
	@Override
	@GetMapping("/takeIdAddMaterialProfessor")
	public String getIdAddMaterial(Model model, ModelMap mp) {
		model.addAttribute("profmaterial", new Profmaterial());
		mp.put("materials", matOrdenar());
		mp.put("professors", profOrdenar());
		return "/professor/takeIdAddMaterialProfessor";
	}
	
	@Override
	@GetMapping("/takeIdDeleteMaterialProfessor")
	public String getIdDeleteMaterial(Model model, ModelMap mp) {
		model.addAttribute("profmaterial", new Profmaterial());
		mp.put("materials", matOrdenar());
		mp.put("professors", profOrdenar());
		return "/professor/takeIdDeleteMaterialProfessor";
	}
	
	@Override
	@GetMapping("/takeIdProfessorListMat")
	public String getIdNameListProfMat(Model model, ModelMap mp) {
		model.addAttribute("takeid", new Takeid());
		mp.put("professors", profOrdenar());
		return "/professor/takeIdProfessorListMaterial";
	}
	
	@Override
	@GetMapping("/listProfessorMaterial")
	public String getListProfMat(Model model, ModelMap mp) {
		Professor profesor = profServ.searchProfessorId(this.takeid.getNum());
		mp.put("materialss", matOrdenarProf(profesor));		
		List<Professor> list = new ArrayList<>();
		list.add(profesor);
		mp.put("professorss", list);
		return "/professor/profListMat";
	}

	/*
	 * CREATE PROFESSOR
	 */
	@Override
	@PostMapping(value = "/registerProfessor")
	public String postRegister(@ModelAttribute Professor entity, BindingResult result) {
		String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:/professor/createProfessor";
		}else {
			if(!profServ.searchProfessor(entity)) {
				if(profServ.create(entity)) {
					//destiny = "redirect:/professor/professorControlPanel";
					this.takeid.setText("Su cuenta ha sido creada correctamente.!!");
					destiny = "redirect:/professor/responseProfessor";
				}else {
					//destiny = "redirect:/professor/createProfessor";
					this.takeid.setText("Hubo un error en la carga de su cuenta o datos erróneos");
					destiny = "redirect:/professor/responseProfessor";
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
	public String postTakeChangeProfile(@ModelAttribute Takeid entity, BindingResult result, ModelMap mp) {	
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/professor/professorControlPanel";
	    }else{
	    	this.takeid.setNum(entity.getNum());	        
	        destiny = "redirect:/professor/updateProfessor";
	    }
	    return destiny;
	}

	/*
	 * CAMBIA LOS DATOS DE UN OBJETO PROFESSOR
	 */
	@Override
	@PostMapping(value = "/changeProfessor")
	public String postChangeProfile(@ModelAttribute Professor entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()){		        
	        destiny = "redirect:/professor/professorControlPanel";
	    }else{
	        if(profServ.changeProfessor(entity)) {
	        	//destiny = "redirect:/professor/professorControlPanel";
	        	this.takeid.setText("Los cambios en la cuenta del Profesor fueron correctos!!");
	        	destiny = "redirect:/professor/responseProfessor";
	        }else {
	        	//destiny = "redirect:/professor/takeChangeProfessor";
	        	this.takeid.setText("Datos erróneos vuelva a intentarlo.");
	        	destiny = "redirect:/professor/responseProfessor";
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
	public String postDeleteProfile(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/professor/takeIdDeleteProfessor";
	    }else{
	    	if(profServ.delete(entity.getNum())) {
	        	//destiny = "redirect:/professor/professorControlPanel";
	        	this.takeid.setText("La cuenta del Profesor fue eliminada correctamente!!");
	        	destiny = "redirect:/professor/responseProfessor";
	        }else {
	        	//destiny = "redirect:/professor/takeChangeProfessor";
	        	this.takeid.setText("Datos erróneos vuelva a intentarlo.");
	        	destiny = "redirect:/professor/responseProfessor";
	        }
	    }
	    return destiny;
	}
	
	
	/*
	 * AGREGA UNA MATERIA A LA LISTA DE UN PROFESSOR
	 */
	@Override
	@PostMapping(value = "/addMaterialProfessor")
	public String postAddMaterial(@ModelAttribute Profmaterial entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/professor/takeIdAddMaterialProfessor";
	    }else{
	        if(profServ.addMatProf(entity)) {
	        	//destiny = "redirect:/professor/professorControlPanel";
	        	this.takeid.setText("La Materia fue Agregada a la lista del Profesor correctamente!!");
	        	destiny = "redirect:/professor/responseProfessor";
	        }else {
	        	//destiny = "redirect:/professor/takeIdAddMaterialProfessor";
	        	this.takeid.setText("Datos erróneos vuelva a intentarlo.");
	        	destiny = "redirect:/professor/responseProfessor";
	        }	        
	    }
	    return destiny;
	}
	
	/*
	 * ELIMINA UNA MATERIA DE LA LISTA DE UN PROFESSOR
	 */
	@Override
	@PostMapping(value = "/deleteMaterialProfessor")
	public String postDeleteMaterial(@ModelAttribute Profmaterial entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/professor/takeIdAddMaterialProfessor";
	    }else{
	    	
	    	if(profServ.deleteMatProf(entity)) {
	        	//destiny = "redirect:/professor/professorControlPanel";
	        	this.takeid.setText("La Materia fue Eliminada de la lista del Profesor correctamente!!");
	        	destiny = "redirect:/professor/responseProfessor";
	        }else {
	        	//destiny = "redirect:/professor/takeIdDeleteMaterialProfessor";
	        	this.takeid.setText("Datos erróneos vuelva a intentarlo.");
	        	destiny = "redirect:/professor/responseProfessor";
	        }
	    }
	    return destiny;
	}	
	
	/*
	 * Toma el ID del Profesor para listar las Materias que tiene asignadas
	 */
	@Override
	@PostMapping(value = "/idProfessorListMaterial")
	public String postIdNameListProfMat(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/professor/takeIdProfessorListMaterial";
	    }else{
	        this.takeid.setNum(entity.getNum());;
	        destiny = "redirect:/professor/listProfessorMaterial";
	    }
	    return destiny;
	}
	
	

	/*
	 * SETERS Y GETERS
	 */
	public Takeid getNum() {
		return takeid;
	}

	public void setNum(Takeid num) {
		this.takeid = num;
	}
	

	private List<Material> matOrdenar(){
		List<Material> list = matServ.viewAll();
		Collections.sort(list, new Comparator<Material>() {
			   public int compare(Material obj1, Material obj2) {
				   return obj1.getName().compareTo(obj2.getName());
			   }
			});
		return list;
	}
	
	private List<Professor> profOrdenar(){
		List<Professor> list = profServ.viewAll();
		Collections.sort(list, new Comparator<Professor>() {
			   public int compare(Professor obj1, Professor obj2) {
				   return obj1.getName().compareTo(obj2.getName());
			   }
			});
		return list;
	}	
	
	private Object matOrdenarProf(Professor profesor) {
		List<Material> list = profesor.getListMaterial();
		Collections.sort(list, new Comparator<Material>() {
			   public int compare(Material obj1, Material obj2) {
				   return obj1.getName().compareTo(obj2.getName());
			   }
			});
		return list;
	}
}
