package controllers.diner;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.SoireeService;
import controllers.AbstractController;
import domain.Diner;
import domain.Soiree;

@Controller
@RequestMapping("/diner/soiree")
public class DinerSoireeController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private SoireeService soireeService;
		
	@Autowired
	private LoginService loginService;
		
	// Constructors -----------------------------------------------------------
		
	public DinerSoireeController() {
		super();
	}
		
	//Soirees organized by logged diner
	@RequestMapping(value = "/organizedList", method = RequestMethod.GET)
	public ModelAndView soireesOrganized() {
		ModelAndView result;
		ArrayList<Soiree> canCreateDish = new ArrayList<Soiree>();
		result = new ModelAndView("soiree/list");
		
		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			Collection<Soiree> soirees = soireeService.soireesOfDiner(d.getId());
			result.addObject("soirees", soirees);
			
			for(Soiree s: soirees){
				if(s.getDishes().size()<4){
					canCreateDish.add(s);
				}
			}
			result.addObject("canCreateDish", canCreateDish);
			result.addObject("soireesOfDiner", soirees);			
		}
			return result;
		}
		
		// Ancillary methods ------------------------------------------------------


}
