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
import com.marianowinar.model.Person;
import com.marianowinar.model.forms.Profmaterial;
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.service.application.MaterialService;

@Controller
@RequestMapping(value = "/material")
public class MaterialController implements Controllerss<Material>{
	
	private Takeid takeid;
	
	public MaterialController() {this.takeid = new Takeid();}
	
	@Autowired
	private MaterialService matServ;
	
	/*
	 * Respuestas a lo largo de sus operaciones
	 */
	@GetMapping("/responseMaterial")
	public String getResponse(ModelMap mp) {
		List<Takeid> list = new ArrayList<>();
		list.add(this.takeid);
		mp.put("takeids", list);
        return "/material/respMaterial";
	}

	@Override
	@GetMapping("/materialControlPanel")
	public String getControlPanel(ModelMap mp) {
		mp.put("materials", matOrdenar());
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
		model.addAttribute("takeid", new Takeid());
		mp.put("materials", matOrdenar());
		return "/material/takeChangeMaterial";
	}

	@Override
	@GetMapping("/updateMaterial")
	public String getUpdate(Model model, ModelMap mp) {
		Material material = matServ.searchingMaterial(this.takeid.getNameMaterial());
		List<Material> list = new ArrayList<>();
		list.add(material);
		mp.put("materials", list);
		model.addAttribute("material", new Material());
		return "/material/updateMaterial";
	}

	@Override
	@GetMapping("/takeIdDeleteMaterial")
	public String getIdDelete(Model model, ModelMap mp) {
		model.addAttribute("takeid", new Takeid());
		mp.put("materials", matOrdenar());
		return "/material/takeDeleteMaterial";
	}

	@Override
	public String getIdAddMaterial(Model model, ModelMap mp) {
		// No la utiliza
		return null;
	}

	@Override
	public String getIdDeleteMaterial(Model model, ModelMap mp) {
		// No la utiliza
		return null;
	}
	
	@Override
	@GetMapping("/takeNameListMaterialProfessor")
	public String getIdNameListProfMat(Model model, ModelMap mp) {
		model.addAttribute("takeid", new Takeid());
		mp.put("materials", matOrdenar());
		return "/material/takeNameListMaterialProfessor";
	}
	
	@Override
	@GetMapping("/matListProf")
	public String getListProfMat(Model model, ModelMap mp) {
		Material material = matServ.searchMaterialName(this.takeid.getNameMaterial());
		mp.put("persons", profOrdenarMat(material));
		
		List<Material> list = new ArrayList<>();
		list.add(material);		
		mp.put("materials", list);		
		return "/material/matListProf";
	}

	/*
	 * CREATE MATERIAL
	 */
	@Override
	@PostMapping(value = "/registerMaterial")
	public String postRegister(@ModelAttribute Material entity, BindingResult result) {
        String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:/material/createMaterial";
		}else {			
			if(matServ.createMaterial(entity)) {
	        	this.takeid.setText("Los cambios en la cuenta del Profesor fueron correctos!!");
				destiny = "redirect:/material/responseMaterial";
	        }else {
	        	this.takeid.setText("Datos erróneos vuelva a intentarlo.");
	        	destiny = "redirect:/material/responseMaterial";
	        }
		}
		return destiny;
	}

	/*
	 * TOMA DE LA LISTA EL ID DE LA MATERIA A MODIFICAR
	 */
	@Override
	@PostMapping(value = "/sendIdChangeMaterial")
	public String postTakeChangeProfile(@ModelAttribute Takeid entity, BindingResult result, ModelMap mp) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/material/materialControlPanel";
	    }else{
	        this.takeid.setNameMaterial(entity.getNameMaterial());
	        destiny = "redirect:/material/updateMaterial";
	    }
	    return destiny;
	}

	/*
	 * CAMBIO DEL OBJETO MATERIA
	 */
	@Override
	@PostMapping(value = "/changeMaterial")
	public String postChangeProfile(@ModelAttribute Material entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()){		        
	        destiny = "redirect:/material/materialControlPanel";
	    }else{	    	
	    	if(matServ.changeMaterial(entity)) {
	        	this.takeid.setText("Los cambios en la Materia fueron correctos!!");
				destiny = "redirect:/material/responseMaterial";
	        }else {
	        	this.takeid.setText("Datos erróneos vuelva a intentarlo.");
	        	destiny = "redirect:/material/responseMaterial";
	        }
	    }
	    return destiny;
	}

	@Override
	@PostMapping(value = "/sendIdDeleteMaterial")
	public String postDeleteProfile(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/material/takeIdDeleteMaterial";
	    }else{
	    	if(matServ.deleteMaterial(entity)) {
	        	this.takeid.setText("Ha sido eliminada correctamente la Materia!!");
	        	destiny = "redirect:/material/responseMaterial";
	        }else {
	        	this.takeid.setText("Datos erróneos vuelva a intentarlo.");
	        	destiny = "redirect:/material/responseMaterial";
	        }
	    }
	    return destiny;
	}

	@Override
	public String postAddMaterial(Profmaterial entity, BindingResult result) {
		// No la utiliza
		return null;
	}

	@Override
	public String postDeleteMaterial(Profmaterial entity, BindingResult result) {
		// No la utiliza
		return null;
	}

	/*
	 * Por un objeto Material lista los Profesores asignados
	 */
	@Override
	@PostMapping(value = "/sendNameMaterialListProfessor")
	public String postIdNameListProfMat(Takeid entity, BindingResult result) {
		String destiny = "";
	    if(result.hasErrors()){		        
	        destiny = "redirect:/material/takeNameListMaterialProfessor";
	    }else{
	    	this.takeid.setNameMaterial(entity.getNameMaterial());
	        destiny = "redirect:/material/matListProf";
	    }
	    return destiny;
	}

	/*
	 * SETERS Y GETERS
	 */
	public Takeid getTakeid() {
		return takeid;
	}

	public void setTakeid(Takeid takeid) {
		this.takeid = takeid;
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
	

	private Object profOrdenarMat(Material material) {
		List<Person> list = material.getListPerson();
		Collections.sort(list, new Comparator<Person>() {
			   public int compare(Person obj1, Person obj2) {
				   return obj1.getName().compareTo(obj2.getName());
			   }
			});
		return list;
	}
}
