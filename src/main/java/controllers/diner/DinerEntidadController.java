package controllers.diner;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DinerService;

import controllers.AbstractController;
import domain.Entidad;
@Controller
@RequestMapping("/diner/entidad")
public class DinerEntidadController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private DinerService dinerService;

	// Constructors -----------------------------------------------------------

	public DinerEntidadController() {
		super();
	}

	// Diner's entidades ----------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView entidades(@RequestParam(required = true) final int q) {
		ModelAndView result;
		result = new ModelAndView("entidad/list");
		result.addObject("entidades", dinerService.findEntidadesOfDiner(q));
		result.addObject("requestURI","/diner/entidad/list.do");
		ArrayList<Entidad> areFinalMode = new ArrayList<Entidad>();
		
		for(Entidad e: dinerService.findEntidadesOfDiner(q)){
			if(e.getIsSaveFinalMode() == true){
				areFinalMode.add(e);
			}
		}
		result.addObject("entidadesFinalMode", areFinalMode);

		return result;
	}

}
