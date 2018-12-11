package controllers.administrator;


import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdministratorService;
import services.DinerService;
import services.EntidadService;

import controllers.AbstractController;
import domain.Administrator;
import domain.Entidad;

@Controller
@RequestMapping("/administrator/entidad")
public class AdministratorEntidadController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EntidadService entidadService;
	
	@Autowired
	private AdministratorService adminService;
	
	@Autowired
	private DinerService dinerService;
	
	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------

	public AdministratorEntidadController() {
		super();
	}

	// All entidades ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView entidades() {
		ModelAndView result;
		result = new ModelAndView("entidad/list");
		Administrator a = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result.addObject("entidades", adminService.findEntidadesOfAdministrator(a.getId()));
		result.addObject("requestURI", "/administrator/entidad/list.do");
		
		ArrayList<Entidad> areFinalMode = new ArrayList<Entidad>();
		
		for(Entidad e: adminService.findEntidadesOfAdministrator(a.getId())){
			if(e.getIsSaveFinalMode() == true){
				areFinalMode.add(e);
			}
		}
		result.addObject("entidadesFinalMode", areFinalMode);

		return result;
	}
	
	//View entidad ----------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) int q) {
		ModelAndView result;
		result = new ModelAndView("entidad/view");
			
		Entidad entidad = entidadService.findOne(q);			
		result.addObject("entidad", entidad);
			
		return result;
	}
	
	//Cambiar a estado final --------------------------------------------------------------------
	@RequestMapping("/finalMode")
	public ModelAndView finalMode(@RequestParam Entidad q) {
		ModelAndView result;
		Administrator a = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (a != null) {
			if (q.getAdmin() == a) {
				entidadService.isSaveFinalMode(q);
				result =  new ModelAndView("redirect:/administrator/entidad/list.do");
			} else {
				result = new ModelAndView("redirect:/misc/403.do");
			}
		} else {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	
	//Create entidad -----------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) final int q) {
		ModelAndView result;

		result = createNewModelAndView(entidadService.create(dinerService.findOne(q)), null);

		return result;
	}
	
	//Editar entidad
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Entidad q) {
		ModelAndView result;
		Administrator a = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (a != null) {
			if (q.getAdmin() == a) {
				result = createNewModelAndView(q, null);
			} else {
				result = new ModelAndView("redirect:/misc/403.do");
			}
		} else {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	// Save ---------------------------------------------------------------

	@RequestMapping(value = "/save-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Entidad entidad, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = createNewModelAndView(entidad, null);
		} else {
			try {
				entidadService.save(entidad);
				res = new ModelAndView("redirect:/administrator/entidad/list.do");
			} catch (Throwable e) {
				res = createNewModelAndView(entidad, "entidad.commit.error");
			}
		}
		return res;
	}

	// Delete
	// ----------------------------------------------------------------------------------
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Entidad q) {
		ModelAndView result;

		Administrator a = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (a != null) {
			if (q.getAdmin() == a && q.getIsSaveFinalMode() == false) {
				entidadService.delete(q);
				result = new ModelAndView("redirect:/adminitrator/entidad/list.do");
			} else {
				result = new ModelAndView("redirect:/misc/403.do");
			}
		} else {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createNewModelAndView(Entidad entidad, String message) {
		ModelAndView result;
		if (entidad.getId() == 0) {
			result = new ModelAndView("entidad/create");
		} else {
			result = new ModelAndView("entidad/edit");
		}

		result.addObject("entidad", entidad);
		result.addObject("message", message);
		return result;
	}

}
