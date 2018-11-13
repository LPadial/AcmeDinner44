package controllers.diner;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.DinerService;
import services.SoireeService;

import controllers.AbstractController;
import domain.Diner;
import domain.Event;

@Controller
@RequestMapping("/diner/event")
public class DinerEventController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private DinerService dinerService;
	
	@Autowired
	private SoireeService soireeService;
		
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
		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			result.addObject("myRegisteredEvents", d.getEvents());			
		}
		
		return result;
	}
		
	//Events organized by logged diner
	@RequestMapping(value = "/organizedList", method = RequestMethod.GET)
	public ModelAndView eventsOrganized() {
		ModelAndView result;
		ArrayList<Integer> canCreateSoiree = new ArrayList<Integer>();
		result = new ModelAndView("event/list");
		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			result.addObject("a", 0);
			result.addObject("events", dinerService.findEventsOfDiner(d.getId()));
			for(Event e: dinerService.findEventsOfDiner(d.getId())){
				Collection<Diner> organizers = soireeService.organizerOfSoireesOfEvent(e.getId());
				if(!organizers.contains(d)){
					canCreateSoiree.add(e.getId());
				}
			}
			result.addObject("canCreateSoiree", canCreateSoiree);
			result.addObject("myRegisteredEvents", d.getEvents());			
		}
			return result;
		}
		
		//Events organized by logged diner
		@RequestMapping(value = "/registeredList", method = RequestMethod.GET)
		public ModelAndView eventsRegistered() {
			ModelAndView result;
			ArrayList<Integer> canCreateSoiree = new ArrayList<Integer>();
			result = new ModelAndView("event/list");
			
			if (LoginService.hasRole("DINER")) {
				Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());				
				result.addObject("a", 0);
				result.addObject("events", d.getEvents());
				for(Event e: d.getEvents()){
					Collection<Diner> organizers = soireeService.organizerOfSoireesOfEvent(e.getId());
					if(!organizers.contains(d)){
						canCreateSoiree.add(e.getId());
					}
				}
				result.addObject("myRegisteredEvents", d.getEvents());		
			}			
			result.addObject("canCreateSoiree", canCreateSoiree);		
			return result;
		}
		
		// Ancillary methods ------------------------------------------------------
		
		protected ModelAndView createModelAndView(Diner diner,String message){
			ModelAndView res;
			res = new ModelAndView("diner/create");
			res.addObject("diner", diner);
			res.addObject("message",message);
			return res;
		}


}
