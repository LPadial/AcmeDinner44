package controllers.diner;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.PersonalSectionService;

import controllers.AbstractController;
import domain.Diner;
import domain.PersonalSection;

@Controller
@RequestMapping("/diner/personalSection")
public class DinerPersonalSectionController extends AbstractController{
	
	// Services ---------------------------------------------------------------
	@Autowired
	private PersonalSectionService personalSectionService;
	
	@Autowired
	private LoginService loginService;
	
	// Constructors -----------------------------------------------------------			
	public DinerPersonalSectionController() {
		super();
	}

	//Edit ---------------------------------------------------------------------	
	@RequestMapping(value="/edit", method= RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		
		Diner d = (Diner)loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		
		result = new ModelAndView("personalSection/edit");
		result.addObject("personalsection", d.getBusinessCard().getPersonalSection());
		
		return result;
	}
	
	
	@RequestMapping(value = "/save-edit", method = RequestMethod.POST, params = "save")
	public ModelAndView savePersonalSection(@Valid PersonalSection personalSection, BindingResult binding) {
		ModelAndView result;
		
		for(FieldError e: binding.getFieldErrors()){
			System.out.println(e.getDefaultMessage());
		}
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(personalSection);
		} else {
			try {
				personalSectionService.save(personalSection);
				result = new ModelAndView("redirect:/diner/businessCard/myView.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(personalSection, "personalsection.commit.error"); } }

		return result; 
	}

			
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(PersonalSection personalSection){
		ModelAndView result;
		
		result=createEditModelAndView(personalSection,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(PersonalSection personalSection,String message){
		ModelAndView result;
		
		result = new ModelAndView("personalSection/edit");
		result.addObject("personalsection", personalSection);
		
		return result;
	}




}
