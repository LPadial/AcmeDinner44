package controllers.diner;


import java.util.ArrayList;
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
import services.SoireeService;

import controllers.AbstractController;
import domain.Diner;
import domain.Dish;
import domain.Event;
import domain.Soiree;

@Controller
@RequestMapping("/diner/event")
public class DinerEventController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private DinerService dinerService;
	
	@Autowired
	private EventService eventService;
	
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
		ArrayList<Integer> canCreateSoiree = new ArrayList<Integer>();
		ArrayList<Integer> eventCanRegistered = new ArrayList<Integer>();
		ArrayList<Event> dateIsBeforeCurrent = new ArrayList<Event>();
		result = new ModelAndView("event/list");
		result.addObject("a", 0);
		result.addObject("events", dinerService.findEventsOfDiner(q));	
		result.addObject("requestURI","/diner/event/list.do");
		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService
					.getPrincipal().getId());
			result.addObject("myRegisteredEvents", d.getEvents());

			for (Event e : dinerService.findEventsOfDiner(q)) {
				Collection<Diner> organizers = soireeService
						.organizerOfSoireesOfEvent(e.getId());
				if (!organizers.contains(d)) {
					canCreateSoiree.add(e.getId());
				}

				if (eventService.findRegisteredDinerInEvents(e.getId()).size() < 4) {
					eventCanRegistered.add(e.getId());
				}
				if (eventService.isOver(e)) {
					dateIsBeforeCurrent.add(e);
				}
			}
			result.addObject("dateIsBeforeCurrent", dateIsBeforeCurrent);
			result.addObject("eventCanRegistered", eventCanRegistered);
			result.addObject("canCreateSoiree", canCreateSoiree);
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
		result.addObject("requestURI","/diner/event/organizedList.do");
			return result;
		}
		
		//Events organized by logged diner
		@RequestMapping(value = "/registeredList", method = RequestMethod.GET)
		public ModelAndView eventsRegistered() {
			ModelAndView result;
			ArrayList<Integer> canCreateSoiree = new ArrayList<Integer>();
			ArrayList<Event> dateIsBeforeCurrent = new ArrayList<Event>();
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
					if (eventService.isOver(e)) {
						dateIsBeforeCurrent.add(e);
					}
				}
				result.addObject("myRegisteredEvents", d.getEvents());		
				result.addObject("dateIsBeforeCurrent", dateIsBeforeCurrent);
			}			
			result.addObject("canCreateSoiree", canCreateSoiree);
			result.addObject("requestURI","/diner/event/registeredList.do");
			return result;
		}
		
		//View -------------------------------------------------------------------------------------------
		@RequestMapping(value = "/view", method = RequestMethod.GET)
		public ModelAndView view(@RequestParam(required = true) int q) {
			ModelAndView result;
			result = new ModelAndView("event/view");

			Event e = eventService.findOne(q);

			Collection<Dish> dishesView = new ArrayList<Dish>();

			result.addObject("evento", e);
			result.addObject("organizer", e.getOrganizer());
			result.addObject("soirees", e.getSoirees());

			for (Soiree s : e.getSoirees()) {
				dishesView.addAll(s.getDishes());
			}
			result.addObject("dishes", dishesView);

			return result;
		}
		
		//Create -----------------------------------------------------------------------------------------
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;

			result = createNewModelAndView(eventService.create(), null);

			return result;
		}
		
		//Edit -----------------------------------------------------------------------------------------
		@RequestMapping("/edit")
		public ModelAndView edit(@RequestParam Event q) {
			ModelAndView result;
			Diner d = (Diner) loginService.findActorByUsername(LoginService
					.getPrincipal().getId());

			if (d != null) {
				if (q.getOrganizer() == d) {
					result = createNewModelAndView(q, null);
				} else {
					result = new ModelAndView("redirect:/misc/403.do");
				}
			} else {
				return new ModelAndView("redirect:/welcome/index.do");
			}

			return result;
		}
		
		//Save -----------------------------------------------------------------------------------------
		@RequestMapping(value = "/save-create", method = RequestMethod.POST, params = "save")
		public ModelAndView saveCreateEdit(@Valid Event event, BindingResult binding) {
			ModelAndView result;
			if (binding.hasErrors()) {
				result = createNewModelAndView(event, null);
			} else {
				try {
					eventService.save(event);
					result = new ModelAndView(
							"redirect:/diner/event/organizedList.do");

				} catch (Throwable th) {
					result = createNewModelAndView(event, "event.commit.error");
				}
			}
			return result;
		}
		
		//Delete -----------------------------------------------------------------------------------------
		@RequestMapping("/delete")
		public ModelAndView delete(@RequestParam Event q) {
			ModelAndView result;

			Diner d = (Diner) loginService.findActorByUsername(LoginService
					.getPrincipal().getId());

			if (d != null) {
				if (q.getOrganizer() == d) {
					eventService.delete(q);
					result = new ModelAndView(
							"redirect:/diner/event/organizedList.do");
				} else {
					result = new ModelAndView("redirect:/misc/403.do");
				}
			} else {
				return new ModelAndView("redirect:/welcome/index.do");
			}

			return result;
		}
		
		// Diner is registering to a event----------------------------------------------------------------

		@RequestMapping(value = "/register", method = RequestMethod.GET)
		public ModelAndView register(@RequestParam(required = true) final int q) {
			ModelAndView result = new ModelAndView("redirect:/misc/403.do");
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			Event event = eventService.findOne(q);
			if(!d.getEvents().contains(event)){
				if(eventService.findRegisteredDinerInEvents(q).size()<4){
					eventService.registerToEvent(q);
					result = new ModelAndView("redirect:/diner/event/registeredList.do");
				}
			}			
			return result;			
		}
		
		// Diner is unregistering to a event----------------------------------------------------------------

		@RequestMapping(value = "/unregister", method = RequestMethod.GET)
		public ModelAndView unregister(@RequestParam(required = true) final int q) {
			ModelAndView result = new ModelAndView("redirect:/misc/403.do");
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			if(d.getEvents().contains(eventService.findOne(q))){
				eventService.unregisterToEvent(q);
				result = new ModelAndView("redirect:/diner/event/registeredList.do");
			}

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
		
		protected ModelAndView createNewModelAndView(Event event, String message) {
			ModelAndView result;

			if (event.getId() == 0) {
				result = new ModelAndView("event/create");
			} else {
				result = new ModelAndView("event/edit");
			}
			result.addObject("event", event);
			result.addObject("message", message);
			return result;
		}


}
