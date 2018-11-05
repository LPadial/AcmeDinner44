package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Diner;
import domain.Dish;
import domain.Event;
import domain.Soiree;

import security.LoginService;
import services.EventService;

@Controller
@RequestMapping("/event")
public class EventController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EventService eventService;

	@Autowired
	private LoginService loginService;


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

		if (LoginService.hasRole("DINER")) {
			Diner actor = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			List<Event> eventsApplyActor = new ArrayList<Event>();
			for (Event event : actor.getEvents()) {
				eventsApplyActor.add(event);
			}
			result.addObject("eventsApplyActor", eventsApplyActor);
		}

		result.addObject("events", eventService.findAll());

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(required = false) String q) {
		ModelAndView result;
		result = new ModelAndView("event/list");
		
		result.addObject("a", 0);
		result.addObject("events", eventService.findEventsByKeyWord(q));
		
		
		return result;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) int q) {
		ModelAndView result;
		result = new ModelAndView("event/view");
		
		Event e = eventService.findOne(q);
		
		Collection<Dish> dishesView = new ArrayList<Dish>();
		
		result.addObject("evento", e);
		result.addObject("organizer",e.getOrganizer());
		result.addObject("soirees", e.getSoirees());
		
		for (Soiree s: e.getSoirees()){
			dishesView.addAll(s.getDishes());			
		}
		result.addObject("dishes", dishesView);
		
		
		return result;
	}

	@RequestMapping(value = "/event/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(eventService.create(), null);

		return result;
	}

	@RequestMapping("/event/edit")
	public ModelAndView edit(@RequestParam Event q) {
		ModelAndView result;
		Diner a = (Diner) loginService.findActorByUsername(LoginService
				.getPrincipal().getId());

		if (a != null) {
			if (a.getEvents().contains(q)) {
				result = createNewModelAndView(q, null);
			} else {
				result = list();
			}
		} else {
			return list();
		}

		return result;
	}

	@RequestMapping(value = "/diner/save-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreateEdit(@Valid Event event,
			BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(event, null);
		} else {
			try {
				eventService.save(event);
				result = new ModelAndView("redirect:/event/diner/mylist.do");

			} catch (Throwable th) {
				result = createNewModelAndView(event, "course.commit.error");
			}
		}
		return result;
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

	/*@RequestMapping("/event/delete")
	public ModelAndView delete(@RequestParam Event q) {
		ModelAndView result;

		Diner a = (Diner) loginService.findActorByUsername(LoginService
				.getPrincipal().getId());

		if (a != null) {
			if (a.getEvents().contains(q)) {
				eventService.delete(q);
				result = new ModelAndView("redirect:/event/diner/mylist.do");
			} else {
				result = list();
			}
		} else {
			return list();
		}

		return result;
	}*/

}
