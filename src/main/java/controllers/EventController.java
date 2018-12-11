package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Diner;
import domain.Event;
import domain.Soiree;

import security.LoginService;
import services.EventService;
import services.SoireeService;
import services.VoteService;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EventService eventService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private SoireeService soireeService;
	
	@Autowired
	private VoteService voteService;

	// Constructors -----------------------------------------------------------
	public EventController() {
		super();
	}

	// Actions

	@RequestMapping(value = "/listNoRegister", method = RequestMethod.GET)
	public ModelAndView listNoRegister() {
		ModelAndView result;

		result = new ModelAndView("event/list");

		result.addObject("events", eventService.findAll());
		result.addObject("a", 0);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("event/list");
		ArrayList<Integer> canCreateSoiree = new ArrayList<Integer>();
		ArrayList<Event> eventCanRegistered = new ArrayList<Event>();

		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService
					.getPrincipal().getId());
			result.addObject("myRegisteredEvents", d.getEvents());
			for (Event e : d.getEvents()) {
				if (eventService.findRegisteredDinerInEvents(e.getId()).size() < 4) {
					eventCanRegistered.add(e);
				}
			}

			for (Event e : d.getEvents()) {
				Collection<Diner> organizers = soireeService
						.organizerOfSoireesOfEvent(e.getId());
				if (!organizers.contains(d)) {
					canCreateSoiree.add(e.getId());
				}
			}

			result.addObject("eventCanRegistered", eventCanRegistered);
			result.addObject("canCreateSoiree", canCreateSoiree);
		}

		result.addObject("events", eventService.findAll());
		result.addObject("finder", 1);
		result.addObject("requestURI","/event/list.do");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(required = false) String q) {
		ModelAndView result;
		ArrayList<Integer> canCreateSoiree = new ArrayList<Integer>();
		ArrayList<Integer> eventCanRegistered = new ArrayList<Integer>();
		ArrayList<Event> dateIsBeforeCurrent = new ArrayList<Event>();
		result = new ModelAndView("event/list");

		result.addObject("a", 0);
		result.addObject("events", eventService.findEventsByKeyWord(q));
		result.addObject("requestURI","/event/search.do");
		result.addObject("finder", 1);

		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService
					.getPrincipal().getId());
			result.addObject("myRegisteredEvents", d.getEvents());

			for (Event e : eventService.findEventsByKeyWord(q)) {
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
	
	//The event's diners -----------------------------------------------------------------
	@RequestMapping(value = "/diner/list", method = RequestMethod.GET)
	public ModelAndView diners(@RequestParam(required = true) final int q) {
		ModelAndView result;
		result = new ModelAndView("diner/list");		
		result.addObject("a",0);
		result.addObject("diners", eventService.findRegisteredDinerInEvents(q));
		result.addObject("requestURI","/event/diner/list.do");

		return result;
	}

	// This event's soirees ----------------------------------------------------------------

	@RequestMapping(value = "/soiree/list", method = RequestMethod.GET)
	public ModelAndView soirees(@RequestParam(required = true) final int q) {

		ModelAndView result;
		result = new ModelAndView("soiree/list");
		Event e = eventService.findOne(q);
		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService
					.getPrincipal().getId());

			ArrayList<Soiree> soireesOfDiner = new ArrayList<Soiree>();
			ArrayList<Soiree> canCreateDish = new ArrayList<Soiree>();
			ArrayList<Soiree> pastSoirees = new ArrayList<Soiree>();
			Boolean isRegisteredInEvent = false;
			ArrayList<Soiree> dinerCanCastAVote = new ArrayList<Soiree>();

			for (Soiree s : e.getSoirees()) {
				if (s.getOrganizer() == d) {
					soireesOfDiner.add(s);
				}
				if (s.getDishes().size() < 4) {
					canCreateDish.add(s);
				}
				if (eventService.isOver(e)
						&& voteService.dinerHasVoteInSoiree(d.getId(),
								s.getId()) < 1) {
					dinerCanCastAVote.add(s);
				}
				if(s.getDate().before(Calendar.getInstance().getTime())){
					pastSoirees.add(s);
				}
			}

			if (d.getEvents().contains(e)) {
				isRegisteredInEvent = true;
			}
			result.addObject("dinerCanCastAVote", dinerCanCastAVote);
			result.addObject("isRegisteredInEvent", isRegisteredInEvent);
			result.addObject("canCreateDish", canCreateDish);
			result.addObject("soireesOfDiner", soireesOfDiner);
			result.addObject("pastSoirees", pastSoirees);
		}
		result.addObject("soirees", e.getSoirees());
		result.addObject("requestURI","/event/soiree/list.do");

		return result;
	}

	// This soiree's dishes
	// ----------------------------------------------------------------

	@RequestMapping(value = "/soiree/dish/list", method = RequestMethod.GET)
	public ModelAndView dishes(@RequestParam(required = true) final int q) {
		ModelAndView result;
		result = new ModelAndView("dish/list");

		Soiree s = soireeService.findOne(q);
		result.addObject("dishes", soireeService.dishesOfSoiree(s.getId()));
		result.addObject("soiree",q);
		result.addObject("requestURI","/event/soiree/dish/list.do");

		return result;
	}
}
