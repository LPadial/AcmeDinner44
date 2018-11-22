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
import security.UserAccount;
import services.ActorService;
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
	private ActorService actorService;
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
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView result;
		Actor actor;
		
		actor = loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		
		result=createEditModelAndView(actor,null);		
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Actor actor,String message){
		ModelAndView result;
		
		result = new ModelAndView("actor/edit");

		if(LoginService.hasRole("ADMINISTRATOR")){
			result.addObject("administrator",actor);
		}
		if(LoginService.hasRole("DINER")){
			result.addObject("diner",actor);
		}
		if(LoginService.hasRole("SUPERMARKET")){
			result.addObject("supermarket",actor);
		}
		if(LoginService.hasRole("SPONSOR")){
			result.addObject("sponsor",actor);
		}
		result.addObject("message",message);
		
		return result;
	}
	
	@RequestMapping(value = "/save-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid Administrator person, BindingResult binding) {
		ModelAndView result;
		
		for(FieldError e: binding.getFieldErrors()){
			System.out.println(e + ": " + e.getDefaultMessage());
		}
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(person,"administrator.commit.error");
		} else {
			try {
				administratorService.save(person);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(person, "administrator.commit.error"); } }

		return result; 
	}
	
	@RequestMapping(value="/save-diner", method = RequestMethod.POST, params = "save")
	public ModelAndView saveDiner(@Valid Diner person, BindingResult binding){
		UserAccount ua = actorService.findUserAccount(person);
		System.out.println("Password: " + ua.getPassword());
		System.out.println("Username: " + ua.getUsername());
		ModelAndView result;
		
			for(FieldError e: binding.getFieldErrors()){
				System.out.println("entra en imprimir");
				System.out.println(e + ": " + e.getDefaultMessage());
			}
			
			if(binding.hasErrors() || binding.hasFieldErrors()){
				System.out.println("tiene errores el binding");
				result = createEditModelAndView(person,"diner.commit.error");
			}else{
				try{
					System.out.println("guarda");
					dinerService.save(person);
					result = new ModelAndView("redirect:/welcome/index.do");
				}catch(Throwable e){
					System.out.println("no guarda");
					result = createEditModelAndView(person, "diner.commit.error");
				}
			}
		
		return result;
	}
	
	@RequestMapping(value="/save-supermarket", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSupermarket(@Valid Supermarket person, BindingResult binding){
		ModelAndView result;
		
		for(FieldError e: binding.getFieldErrors()){
			System.out.println(e.getDefaultMessage());
		}
		
		if(binding.hasErrors()){
			result = createEditModelAndView(person, "supermarket.commit.error");
		}else{
			try{
				supermarketService.save(person);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result = createEditModelAndView(person, "supermarket.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-sponsor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSponsor(@Valid Sponsor person, BindingResult binding){
		ModelAndView result;
		
		for(FieldError e: binding.getFieldErrors()){
			System.out.println(e.getDefaultMessage());
		}
		
		if(binding.hasErrors()){
			result = createEditModelAndView(person, "sponsor.commit.error");
		}else{
			try{
				sponsorService.save(person);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result = createEditModelAndView(person, "sponsor.commit.error");
			}
		}
		return result;
	}
}
