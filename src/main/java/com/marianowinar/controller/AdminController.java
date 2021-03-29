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
import com.marianowinar.model.forms.Register;
import com.marianowinar.model.forms.ShareMaterial;
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.service.application.MaterialService;
import com.marianowinar.service.application.PersonService;
import com.marianowinar.service.application.UserService;
import com.marianowinar.service.factory.FactoryEntities;
import com.marianowinar.service.helper.ListPerson;

@Controller
@RequestMapping(value = "/admins")
public class AdminController implements Controllers{
	
	@Autowired
	private PersonService perServ;	
	@Autowired
	private UserService userServ;	
	@Autowired
	private MaterialService matServ;
		
	private Takeid takeid;
	private ListPerson list;
	private ShareMaterial share;
	private FactoryEntities factory;
	
	public AdminController() {
		this.takeid = new Takeid();
		this.list = new ListPerson();
		this.share = new ShareMaterial();
		this.factory = FactoryEntities.getInstance();
	}	
	
	/*
	 * GET FUNCTIONS
	 */
	
	/*
	 * Respuesta solo en la creación de la cuentas nuevas
	 */
	@GetMapping("/responseCreate")
	public String getResponseCreate(ModelMap mp) {
		List<Takeid> list = new ArrayList<>();
		list.add(this.takeid);
		mp.put("takeids", list);
        return "/admin/responseAdminCreate";
	}
	
	/*
	 * Respuestas a lo largo de sus operaciones
	 */
	@GetMapping("/response")
	public String getResponse(ModelMap mp) {
		List<Takeid> list = new ArrayList<>();
		list.add(this.takeid);
		mp.put("takeids", list);
        return "/admin/responseAdmin";
	}
	
	@Override
	@GetMapping("/registerAdmin")
	public String getRegister(Model model){
		model.addAttribute("register", new Register());
        return "/admin/registerAdmin";
    }
	
	@Override
	@GetMapping("/loginForm")
	public String getLogin(Model model){
		model.addAttribute("register", new Register());
        return "/admin/loginAdmin";
    }	
	
	@Override
	@GetMapping("/profileAdmin")
	public String getProfile(Model model, ModelMap mp){	
		model.addAttribute("takeid", new Takeid());
		Person perInstance = perServ.searchPersonDni(this.takeid.getText());
		List<Person> list = new ArrayList<>();
		list.add(perInstance);
		mp.put("persons", list);	
		return "/admin/profileAdmin";
    }
	
	@Override
	@GetMapping("/updateChangeAdmin")
	public String getUpdate(Model model, ModelMap mp) {
		Person perInstance = perServ.searchPersonDni(this.takeid.getText());
		List<Person> list = new ArrayList<>();
		list.add(perInstance);
		mp.put("persons", list);		
		model.addAttribute("register", new Register());
        return "/admin/updateAdmin";
    }
	
	@Override
	@GetMapping("/deleteAdmin")
	public String getDelete(Model model){
		model.addAttribute("takeid", new Takeid());
        return "/admin/deletesAdmin";
    }
	
	@Override
	@GetMapping("/outAdmin")
	public String getLogout(Model model){
		model.addAttribute("takeid", new Takeid());
        return "/admin/logoutAdmin";
    }
	
	@GetMapping("/ListStudent")
	public String getListStudent(ModelMap mp) {
		mp.put("persons", studentOrdenar());
		return "/admin/ListOrderStudent";
	}
	
	@GetMapping("/share")
	public String getShare(ModelMap mp) {
		mp.put("sharematerials", matOrdenarShare());
		return "/admin/shareMaterial";
	}
	

	/*
	 * ADMIN REGISTER
	 */
	@Override
	@PostMapping(value = "/admin")
	public String postRegister(@ModelAttribute Register entity, BindingResult result) {
		String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:/admins/registerAdmin";
		}else {
			if(perServ.createAdmin(entity)) {
				this.takeid.setText("La Cuenta se ha creado satisfactoriamente. Puede loguearse!!! Bienvenido al Sitio");
				destiny = "redirect:/admins/responseCreate";
			}else {			
				this.takeid.setText("Ha cargado datos erróneos, vuelva a intentarlo.");
				destiny = "redirect:/admins/responseCreate";
			}	
		}
		return destiny;
	}	
	
	/*
	 * LOGIN ADMIN
	 */
	@Override
	@PostMapping(value = "/login")
	public String postLogin(@ModelAttribute Register entity, BindingResult result){
		String destiny = "";
		if(result.hasErrors()) {
			this.takeid.setText("La Operación causó errores, Por favor vuelva a intentarlo. Gracias");
			destiny= "redirect:/admins/responseCreate";
		}else{	
			if(perServ.login(entity)) {
				this.takeid.setText(entity.getDni());
				destiny= "redirect:/admins/profileAdmin";
			}else {
				this.takeid.setText("Ha ingresado datos erróneos, vuelva a intentarlo.");
				destiny= "redirect:/admins/responseCreate";
			}					
		}
		return destiny;
	}		
	
	/*
	 * ADMIN PROFILE NO SE USA POR AHORA
	 */
	@Override
	@PostMapping(value = "/profile")	
	public String postProfile(@ModelAttribute Takeid entity, BindingResult result, ModelMap mp) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/admins/profileAdmin";
		}else{
			this.takeid.setText(entity.getText());
			destiny = "redirect:/admins/updateChangeAdmin";		
		}
		return destiny;
	}		
	
	/*
	 * ADMIN CHANGE 
	 */
	@Override
	@PostMapping(value = "/changeProfile")
	public String postChangeProfile(@ModelAttribute Register entity, BindingResult result, ModelMap mp) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/admins/updateAdmin";
		}else{			
			if(perServ.changeProfileAdmin(entity)) {
				this.takeid.setText("Los Cambios en su cuenta fueron correctos.");
				destiny= "redirect:/admins/response";
			}else {		
				this.takeid.setText("Incorrectos los Cambios realizados o la información ingresada es incorrecta. Vuelva a intentarlo.");
				destiny= "redirect:/admins/response";
			}		
		}		
		return destiny;
	}		
	
	/*
	 * DELETE PROFILE ADMIN
	 */
	@Override
	@PostMapping(value = "/deleteProfile")
	public String postDeleteProfile(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/admins/profileAdmin";
		}else{
			if(perServ.deleteProfile(entity)) {
				list.removePerName(entity.getText());
				this.takeid.setText("La Cuenta fue eliminada con éxito.");
				destiny= "redirect:/admins/response";				
			}else {
				this.takeid.setText("No pudo ser eliminada la cuenta, intente nuevamente.");
				destiny= "redirect:/admins/response";
			}
		}		
		return destiny;
	}		
	
	/*
	 * LOGOUT PROFILE ADMIN
	 */
	@Override
	@PostMapping(value = "/logoutProfile")
	public String postLogoutProfile(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/admins/profileAdmin";
		}else{						
			if(userServ.logout(entity)) {				
				destiny = "redirect:/";
			}else {
				this.takeid.setText("Error al intentar salir de la cuenta. Intente nuevamente.");
				destiny= "redirect:/admins/response";
			}			
		}		
		return destiny;		
	}
	
	private List<Person> studentOrdenar(){
		List<Person> listStudent = new ArrayList<>();
		for(Person ele : perServ.viewAll()) {
			if(ele.getType().equals("STUDENT")) {
				listStudent.add(ele);
			}
		}		
		Collections.sort(listStudent, new Comparator<Person>() {
			   public int compare(Person obj1, Person obj2) {
				   return obj1.getSurname().compareTo(obj2.getSurname());
			   }
			});
		return listStudent;
	}
	
	private List<ShareMaterial> matOrdenarShare() {
		List<ShareMaterial> shareList = new ArrayList<>();
		for(Material ele : matServ.viewAll()) {
			this.share = factory.createShare(ele);
			this.share.setShare(Integer.parseInt(ele.getCapacity()) - Integer.parseInt(ele.getSubscribed()));
			shareList.add(this.share);
		}
		Collections.sort(shareList, new Comparator<ShareMaterial>() {
			   public int compare(ShareMaterial obj1, ShareMaterial obj2) {
				   return obj1.getName().compareTo(obj2.getName());
			   }
			});
		return shareList;
	}
	   
}
