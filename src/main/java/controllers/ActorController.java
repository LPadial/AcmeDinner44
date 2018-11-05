package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.Diner;
import domain.Sponsor;
import domain.Supermarket;
import security.LoginService;
import services.AdministratorService;
import services.DinerService;
import services.SponsorService;
import services.SupermarketService;


@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController{
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private DinerService dinerService;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private SupermarketService supermarketService;
	
	public ActorController(){
		super();
	}
	
	@RequestMapping(value="/edit", method= RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		Actor actor;
		
		actor = loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		
		result = new ModelAndView("actor/edit");
		result.addObject("person", actor);
		result.addObject("administrator", actor);
		result.addObject("diner", actor);
		result.addObject("sponsor", actor);
		result.addObject("supermarket", actor);
		
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Actor actor){
		ModelAndView result;
		
		result=createEditModelAndView(actor,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Actor actor,String message){
		ModelAndView result;
		
		result = new ModelAndView("actor/edit");
		result.addObject("person", actor);
		result.addObject("administrator", actor);
		result.addObject("diner", actor);
		result.addObject("sponsor", actor);
		result.addObject("supermarket", actor);
		
		return result;
	}
	
	@RequestMapping(value = "/save-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid Administrator administrator, BindingResult binding) {
		ModelAndView result;
		
		for(FieldError e: binding.getFieldErrors()){
			System.out.println(e.getDefaultMessage());
		}
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(administrator);
		} else {
			try {
				administratorService.save(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(administrator, "actor.commit.error"); } }

		return result; 
	}
	
	@RequestMapping(value="/save-diner", method = RequestMethod.POST, params = "save")
	public ModelAndView saveDiner(@Valid Diner diner, BindingResult binding){
		ModelAndView result;
		
		for(FieldError e: binding.getFieldErrors()){
			System.out.println(e.getDefaultMessage());
		}
		
		if(binding.hasErrors()){
			result = createEditModelAndView(diner);
		}else{
			try{
				dinerService.save(diner);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result = createEditModelAndView(diner, "actor.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-supermarket", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSupermarket(@Valid Supermarket supermarket, BindingResult binding){
		ModelAndView result;
		
		for(FieldError e: binding.getFieldErrors()){
			System.out.println(e.getDefaultMessage());
		}
		
		if(binding.hasErrors()){
			result = createEditModelAndView(supermarket);
		}else{
			try{
				supermarketService.save(supermarket);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result = createEditModelAndView(supermarket, "actor.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-sponsor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsor(@Valid Sponsor sponsor, BindingResult binding){
		ModelAndView result;
		
		for(FieldError e: binding.getFieldErrors()){
			System.out.println(e.getDefaultMessage());
		}
		
		if(binding.hasErrors()){
			result = createEditModelAndView(sponsor);
		}else{
			try{
				sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result = createEditModelAndView(sponsor, "actor.commit.error");
			}
		}
		return result;
	}
}
