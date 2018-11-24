package controllers.administrator;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;

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
	
	//Update the scores of the diners ------------------------------------------------------------
	@RequestMapping("/updateScore")
	public ModelAndView updateScore() {
		ModelAndView result;
		administratorService.updateScores();
		result = new ModelAndView("redirect:/welcome/index.do");
				
		return result;			
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
		result.addObject("avgEventsOrganised", administratorService.avgNumberOfEventsOrganisedByDiners());
		result.addObject("minEventsOrganised",administratorService.minNumberOfEventsOrganisedByDiners());
		result.addObject("maxEventsOrganised",administratorService.maxNumberOfEventsOrganisedByDiners());
		
		//Diners who have more events
		result.addObject("dinersMoreEvents",administratorService.dinersWhoHaveMoreEvents());
		
		//Ratio of events that are over
		result.addObject("ratioOfEventsOver",administratorService.ratioOfEventsOver());
		
		//Avg, min, max number of dishes per soiree
		result.addObject("avgMinMaxNumberOfDishesPerSoiree", administratorService.avgMinMaxNumberOfDishesPerSoiree());
		
		//Ratio of diners who have at least one professional section
		result.addObject("ratioPS",administratorService.ratioOfDinersWhoHaveAtLeastOneProfessionalSection());
		
		//Ratio of diners who have at least one social section
		result.addObject("ratioSS",administratorService.ratioOfDinersWhoHaveAtLeastOneSocialSection());
		
		//Top-3 best-selling items
		result.addObject("bestSelledItems",administratorService.bestSelledItems());
		
		//Top-3 best-buying diners
		result.addObject("bestBuyingDiners",administratorService.bestBuyingDiners());
		
		//Top-3 best-selling supermarkets
		result.addObject("bestSellingSupermarkets",administratorService.bestSellingSupermarkets());

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
