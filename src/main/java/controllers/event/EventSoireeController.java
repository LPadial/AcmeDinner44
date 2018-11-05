package controllers.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import domain.Event;
import domain.Soiree;

@Controller
@RequestMapping("/event/soiree")
public class EventSoireeController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private SoireeService soireeService;

	@Autowired
	private EventService eventService;

	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------

	public EventSoireeController() {
		super();
	}

	// This event's soirees ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView soirees(@RequestParam(required = true) final int q) {
		ModelAndView result;
		result = new ModelAndView("soiree/list");
		
		Event e = eventService.findOne(q);
		
		result.addObject("soirees", e.getSoirees());

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

	// Creation ---------------------------------------------------------------

	/*
	 * @RequestMapping(value="/create",method=RequestMethod.GET) public
	 * ModelAndView create(){ ModelAndView res;
	 * 
	 * res = new ModelAndView("diner/create");
	 * res.addObject("diner",dinerService.create());
	 * 
	 * return res; }
	 */

	// Save ---------------------------------------------------------------
	/*
	 * @RequestMapping(value="/save",method=RequestMethod.POST,params = "save")
	 * public ModelAndView saveCreate(@Valid Diner diner, BindingResult
	 * binding){ ModelAndView res;
	 * 
	 * if(binding.hasErrors()){ res = createModelAndView(diner,null); }else{
	 * try{ dinerService.save(diner); res = new
	 * ModelAndView("redirect:/welcome/index.do");
	 * 
	 * }catch(Throwable e){ res = createModelAndView(diner,
	 * "diner.commit.error"); } } return res;
	 * 
	 * }
	 */

	/*
	 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params =
	 * "delete") public ModelAndView delete(Diner diner) { ModelAndView result;
	 * 
	 * dinerService.delete(diner); result = new
	 * ModelAndView("redirect:list.do");
	 * 
	 * return result; }
	 */

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createModelAndView(Diner diner, String message) {
		ModelAndView res;
		res = new ModelAndView("diner/create");
		res.addObject("diner", diner);
		res.addObject("message", message);
		return res;
	}

}
