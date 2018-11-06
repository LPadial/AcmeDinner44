package controllers.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import services.SoireeService;

import controllers.AbstractController;
import domain.Diner;
import domain.Soiree;

@Controller
@RequestMapping("/event/diner")
public class EventDinerController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private SoireeService soireeService;

	@Autowired
	private EventService eventService;


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

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createModelAndView(Diner diner, String message) {
		ModelAndView res;
		res = new ModelAndView("diner/create");
		res.addObject("diner", diner);
		res.addObject("message", message);
		return res;
	}

}
