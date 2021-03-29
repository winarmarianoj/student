package com.marianowinar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;

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
import com.marianowinar.model.forms.Takeid;
import com.marianowinar.service.application.MaterialService;
import com.marianowinar.service.application.PersonService;
import com.marianowinar.service.application.UserService;
import com.marianowinar.service.helper.ListPerson;

@Controller
@RequestMapping(value = "/student")
public class StudentController implements Controllers{
	
	@Autowired
	private PersonService perServ;
	@Autowired
	private UserService userServ;
	@Autowired
	private MaterialService matServ;
	
	private Takeid takeid;
	private ListPerson list;
	
	public StudentController() {
		this.takeid = new Takeid();
	}
	
	/*
	 * Respuesta solo en la creación de la cuentas nuevas
	 */
	@GetMapping("/responseCreate")
	public String getResponseCreate(ModelMap mp) {
		List<Takeid> list = new ArrayList<>();
		list.add(this.takeid);
		mp.put("takeids", list);
        return "/student/responseStudentCreate";
	}
	
	/*
	 * Respuestas a lo largo de sus operaciones
	 */
	@GetMapping("/response")
	public String getResponse(ModelMap mp) {
		List<Takeid> list = new ArrayList<>();
		list.add(this.takeid);
		mp.put("takeids", list);
        return "/student/responseStudent";
	}
	
	/*
	 * Respuestas a lo largo de sus operaciones
	 */
	@GetMapping("/responseInscription")
	public String getInscription(ModelMap mp) {
		List<Takeid> list = new ArrayList<>();
		list.add(this.takeid);
		mp.put("takeids", list);
        return "/student/responseSubscription";
	}

	@Override
	@GetMapping("/registerStudent")
	public String getRegister(Model model) {
		model.addAttribute("register", new Register());
        return "/student/registerStudents";
	}

	@Override
	@GetMapping("/loginForm")
	public String getLogin(Model model) {
		model.addAttribute("register", new Register());
		return "/student/loginStudent";
	}

	@Override
	@GetMapping("/profileStudent")
	public String getProfile(Model model, ModelMap mp) throws ServletException, IOException {	
		model.addAttribute("takeid", new Takeid());
		Person perInstance = perServ.searchPersonDni(this.takeid.getText());
		List<Person> list = new ArrayList<>();
		list.add(perInstance);
		mp.put("persons", list);
        return "/student/profStudent";
	}

	@Override
	@GetMapping("/updateStudent")
	public String getUpdate(Model model, ModelMap mp) throws ServletException, IOException {
		Person perInstance = perServ.searchPersonDni(this.takeid.getText());
		List<Person> list = new ArrayList<>();
		list.add(perInstance);
		mp.put("persons", list);		
		model.addAttribute("register", new Register());
		return "/student/updateChangeStudent";
	}

	@Override
	@GetMapping("/deleteStudent")
	public String getDelete(Model model) {
		model.addAttribute("takeid", new Takeid());
        return "/student/deleStudents";
	}

	@Override
	@GetMapping("/logoutStudent")
	public String getLogout(Model model) {
		model.addAttribute("takeid", new Takeid());
        return "/student/outStudent";
	}
	
	@GetMapping("/inscription")
	public String getInscription(Model model, ModelMap mp) {
		model.addAttribute("takeid", new Takeid());
		mp.put("materials", matOrdenar());
        return "/student/inscriptionStudent";
	}
	
	@GetMapping("/listMaterialInscripted")
	public String getInscripcionStudentMat(Model model, ModelMap mp)  {		
		Person student = perServ.searchPersonDni(this.takeid.getText());
		List<Person> list = new ArrayList<>();
		list.add(student);
		mp.put("students", list);
		mp.put("materials", matOrdenarStudent(student));
		return "/student/listMaterialStudentInscripted";
	}

	@GetMapping("/UnsubscribedMaterial")
	public String getUnsubscribedMaterial(Model model, ModelMap mp) {
		model.addAttribute("takeid", new Takeid());
		mp.put("materials", matOrdenar());
        return "/student/deleteMatStudent";
	}
	
    /*
     * STUDENT REGISTER
     */
	@Override
	@PostMapping(value = "/student")
	public String postRegister(@ModelAttribute Register entity, BindingResult result) {
        String destiny = "";
		
		if(result.hasErrors()) {
			destiny= "redirect:/student/registerStudent";
		}else {
			if(perServ.createStudent(entity)) {
				this.takeid.setText("La Cuenta se ha creado satisfactoriamente. Puede loguearse!!! Bienvenido al Sitio");
				destiny = "redirect:/student/responseCreate";
			}else {
				this.takeid.setText("Ha cargado datos erróneos, vuelva a intentarlo.");
				destiny = "redirect:/student/responseCreate";
			}	
		}
		return destiny;
		
		
	}

	/*
	 * STUDENT LOGIN
	 */
	@Override
	@PostMapping(value = "/login")
	public String postLogin(@ModelAttribute Register entity, BindingResult result) throws ServletException, IOException {
		String destiny = "";
		if(result.hasErrors()) {
			this.takeid.setText("La Operación causó errores, Por favor vuelva a intentarlo. Gracias");
		    destiny= "redirect:/student/responseCreate";
		}else{		
			if(perServ.login(entity)) {
				this.takeid.setText(entity.getDni());
				destiny = "redirect:/student/profileStudent";
			}else {
				this.takeid.setText("Ha cargado datos erróneos, vuelva a intentarlo.");
				destiny = "redirect:/student/response";
			}				
		}
		return destiny;
	}

	/*
	 * STUDENT PROFILE
	 */
	@Override
	@PostMapping(value = "/profile")
	public String postProfile(@ModelAttribute Takeid entity, BindingResult result, ModelMap mp) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/student/profileStudent";
		}else{			
			this.takeid.setText(entity.getText());
			destiny = "redirect:/student/updateStudent";		
		}
		return destiny;
	}

	/*
	 * STUDENT CHANGE
	 */
	@Override
	@PostMapping(value = "/changeProfile")
	public String postChangeProfile(@ModelAttribute Register entity, BindingResult result, ModelMap mp) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/student/updateStudent";
		}else{
			
			if(perServ.changeProfileStudent(entity)) {
				this.takeid.setText("Los Cambios en su cuenta fueron correctos.");
				destiny= "redirect:/student/response";
			}else {				
				this.takeid.setText("Incorrectos los Cambios realizados o la información ingresada es incorrecta. Vuelva a intentarlo.");
				destiny= "redirect:/student/response";
			}
		}		
		return destiny;
	}

	/*
	 * STUDENT DELETE
	 */
	@Override
	@PostMapping(value = "/deleteProfile")
	public String postDeleteProfile(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny = "redirect:/student/profileStudent";
		}else{
			if(perServ.deleteProfile(entity)) {
				list.removePerName(entity.getText());
				this.takeid.setText("La Cuenta fue eliminada con éxito.");
				destiny= "redirect:/student/response";
			}else {
				this.takeid.setText("No pudo ser eliminada la cuenta, intente nuevamente.");
				destiny= "redirect:/student/response";
			}
		}		
		return destiny;
	}

	/*
	 * STUDENT LOGOUT
	 */
	@Override
	@PostMapping(value = "/logoutProfile")
	public String postLogoutProfile(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny= "redirect:/student/profileStudent";
		}else{						
			if(userServ.logout(entity)) {
				destiny = "redirect:/";
			}else {
				this.takeid.setText("Error al intentar salir de la cuenta. Intente nuevamente.");
				destiny= "redirect:/student/response";
			}			
		}		
		return destiny;
	}
	
	/*
	 * STUDENT MATERIAL INSCRIPTION 
	 */
	@PostMapping(value = "/inscription")
	public String postInscription(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny = "redirect:/student/profileStudent";
		}else{				
			if(perServ.inscription(entity)) {
				this.takeid.setText("Su inscripción a la materia ha sido aceptada y se realizó correctamente!!");
				destiny= "redirect:/student/responseInscription";
			}else {
				this.takeid.setText("No se ha podido inscribir o los datos ingresados son erróneos.");
				destiny= "redirect:/student/responseInscription";
			}						
		}		
		return destiny;
	}
	
	/*
	 * STUDENT MATERIAL UNSUBSCRIBED
	 */
	@PostMapping(value = "/unsubscribed")
	public String postUnsubscribed(@ModelAttribute Takeid entity, BindingResult result) {
		String destiny = "";
		if(result.hasErrors()) {
			destiny = "redirect:/student/profileStudent";
		}else{				
			if(perServ.unsubscribed(entity)) {
				this.takeid.setText("Su desuscripción a la materia ha sido aceptada y se realizó correctamente!!");
				destiny= "redirect:/student/responseInscription";
			}else {
				this.takeid.setText("No se ha podido eliminar la materia de su lista o los datos son erróneos.");
				destiny= "redirect:/student/responseInscription";
			}						
		}		
		return destiny;
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
	
	private Object matOrdenarStudent(Person student) {
		List<Material> list = student.getListMaterial();
		Collections.sort(list, new Comparator<Material>() {
			   public int compare(Material obj1, Material obj2) {
				   return obj1.getName().compareTo(obj2.getName());
			   }
			});
		return list;
	}

}
