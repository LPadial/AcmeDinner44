package controllers.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.EventService;
import services.SoireeService;

import controllers.AbstractController;
import domain.Diner;
import domain.Event;
import domain.Soiree;

@Controller
@RequestMapping("/event/diner")
public class EventDinerController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private SoireeService soireeService;

	@Autowired
	private EventService eventService;
	
	@Autowired
	private LoginService loginService;


	// Constructors -----------------------------------------------------------

	public EventDinerController() {
		super();
	}

	// Registered diners in event ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView diners(@RequestParam(required = true) final int q) {
		ModelAndView result;
		result = new ModelAndView("diner/list");		
		result.addObject("a",0);
		result.addObject("diners", eventService.findRegisteredDinerInEvents(q));

		return result;
	}
	
	// This soiree's dishes ----------------------------------------------------------------

	@RequestMapping(value = "/dish/list", method = RequestMethod.GET)
	public ModelAndView dishes(@RequestParam(required = true) final int q) {
		ModelAndView result;
		result = new ModelAndView("dish/list");
				
		Soiree s = soireeService.findOne(q);
		result.addObject("dishes", s.getDishes());

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
			}/*else{
				result.addObject("a", 0);
			}*/
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

	protected ModelAndView createModelAndView(Diner diner, String message) {
		ModelAndView res;
		res = new ModelAndView("diner/create");
		res.addObject("diner", diner);
		res.addObject("message", message);
		return res;
	}

}
