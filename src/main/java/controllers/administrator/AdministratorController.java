package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Administrator;
import domain.Diner;

import services.AdministratorService;
import services.DinerService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private AdministratorService administratorService;
	
	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;
		res = new ModelAndView("administrator/create");
		res.addObject("administrator",administratorService.create());

		return res;
	}
	
	// Save ---------------------------------------------------------------
	@RequestMapping(value="/save",method=RequestMethod.POST,params = "save")
	public ModelAndView saveCreate(@Valid Administrator administrator, BindingResult binding){
		ModelAndView res;

		if(binding.hasErrors()){
			res = createModelAndView(administrator,null);
		}else{
			try{
				administratorService.save(administrator);
				res = new ModelAndView("redirect:/welcome/index.do");

			}catch(Throwable e){
				res = createModelAndView(administrator, "administrator.commit.error");
			}
		}
		return res;

	}
	
	//DASHBOARD ----------------------------------------------------------------------------------
	
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");

		// Level C --------------------------------------------

		// Number of diners in the system
		result.addObject("numberOfDiners", administratorService.numDiners());

		// Avg, min, max score of diners
		result.addObject("avgMinMaxScore", administratorService.avgMinMaxScore());

		// Avg, min, max number of events organised by the diners

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createModelAndView(Administrator administrator,String message){
		ModelAndView res;
		res = new ModelAndView("administrator/create");
		res.addObject("administrator", administrator);
		res.addObject("message",message);
		return res;
	}


}
