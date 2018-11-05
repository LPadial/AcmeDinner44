package controllers.diner;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.DinerService;
import services.EventService;

import controllers.AbstractController;
import domain.Diner;

@Controller
@RequestMapping("/diner/event")
public class DinerEventController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private DinerService dinerService;
		
		@Autowired
		private EventService eventService;
		
		@Autowired
		private LoginService loginService;
		
		// Constructors -----------------------------------------------------------
		
		public DinerEventController() {
			super();
		}

		// Events organized by a diner ----------------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView events(@RequestParam(required = true) final int q) {
			ModelAndView result;
			result = new ModelAndView("event/list");
			result.addObject("a", 0);
			result.addObject("events", dinerService.findEventsOfDiner(q));	
			
			return result;
		}
		
		//Events organized by logged diner
		@RequestMapping(value = "/organizedList", method = RequestMethod.GET)
		public ModelAndView eventsOrganized() {
			System.out.println("Entro organized List");
			ModelAndView result;
			result = new ModelAndView("event/list");
			
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());			
			
			result.addObject("a", 0);
			result.addObject("events", dinerService.findEventsOfDiner(d.getId()));	
			
			return result;
		}
		
		//Events organized by logged diner
		@RequestMapping(value = "/registeredList", method = RequestMethod.GET)
		public ModelAndView eventsRegistered() {
			ModelAndView result;
			result = new ModelAndView("event/list");
					
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());			
					
			result.addObject("a", 0);
			result.addObject("events", d.getEvents());	
					
			return result;
		}
		

		// Creation ---------------------------------------------------------------

		/*@RequestMapping(value="/create",method=RequestMethod.GET)
		public ModelAndView create(){
			ModelAndView res;

			res = new ModelAndView("diner/create");
			res.addObject("diner",dinerService.create());

			return res;
		}*/
		
		// Save ---------------------------------------------------------------
		/*@RequestMapping(value="/save",method=RequestMethod.POST,params = "save")
		public ModelAndView saveCreate(@Valid Diner diner, BindingResult binding){
			ModelAndView res;

			if(binding.hasErrors()){
				res = createModelAndView(diner,null);
			}else{
				try{
					dinerService.save(diner);
					res = new ModelAndView("redirect:/welcome/index.do");

				}catch(Throwable e){
					res = createModelAndView(diner, "diner.commit.error");
				}
			}
			return res;

		}*/


		/*@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Diner diner) {
			ModelAndView result;

			dinerService.delete(diner);
			result = new ModelAndView("redirect:list.do");

			return result;
		}*/
		
		// Ancillary methods ------------------------------------------------------
		
		protected ModelAndView createModelAndView(Diner diner,String message){
			ModelAndView res;
			res = new ModelAndView("diner/create");
			res.addObject("diner", diner);
			res.addObject("message",message);
			return res;
		}


}
