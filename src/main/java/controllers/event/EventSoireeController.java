package controllers.event;

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
import services.EventService;
import services.SoireeService;
import services.VoteService;

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
	private VoteService voteService;
	
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
		if(LoginService.hasRole("DINER")){
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			
			ArrayList<Soiree> soireesOfDiner = new ArrayList<Soiree>();
			ArrayList<Soiree> canCreateDish = new ArrayList<Soiree>();
			Boolean isRegisteredInEvent = false;
			ArrayList<Soiree> dinerCanCastAVote = new ArrayList<Soiree>();
			
			
			
			for(Soiree s: e.getSoirees()){
				if(s.getOrganizer()==d){
					soireesOfDiner.add(s);
				}			
				if(s.getDishes().size()<4){
					canCreateDish.add(s);
				}
				if(eventService.isOver(e) && voteService.dinerHasVoteInSoiree(d.getId(), s.getId())<1){
					dinerCanCastAVote.add(s);
				}
			}		
	
			if(d.getEvents().contains(e)){
				isRegisteredInEvent = true;			
			}
			result.addObject("dinerCanCastAVote",dinerCanCastAVote);
			result.addObject("isRegisteredInEvent",isRegisteredInEvent);
			result.addObject("canCreateDish",canCreateDish);
			result.addObject("soireesOfDiner", soireesOfDiner);
		}
		result.addObject("soirees", e.getSoirees());

		return result;
	}
	
	// This soiree's dishes ----------------------------------------------------------------

		@RequestMapping(value = "/dish/list", method = RequestMethod.GET)
		public ModelAndView dishes(@RequestParam(required = true) final int q) {
			ModelAndView result;
			result = new ModelAndView("dish/list");
				
			Soiree s = soireeService.findOne(q);
			result.addObject("dishes", soireeService.dishesOfSoiree(s.getId()));

			return result;
		}

	// Creation ---------------------------------------------------------------
		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) final int q) {
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Collection<Diner> dinersCreators = soireeService.organizerOfSoireesOfEvent(q);
		
		if(!dinersCreators.contains(d)){
			result = createNewModelAndView(soireeService.create(eventService.findOne(q)), null);
		}

		return result;
	}
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value="/save-create",method=RequestMethod.POST,params = "save")
	public ModelAndView saveCreate(@Valid Soiree soiree, BindingResult binding){ 
		ModelAndView res;
		
		if(binding.hasErrors()){ 
			res = createNewModelAndView(soiree,null); 
		}else{
			try{ 
				soireeService.save(soiree); 
				res = new ModelAndView("redirect:/diner/soiree/organizedList.do");
			}catch(Throwable e){ 
				res = createNewModelAndView(soiree,"soiree.commit.error"); 
			} 
		} 
		return res;
	 }

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

	protected ModelAndView createNewModelAndView(Soiree soiree, String message) {
		ModelAndView result;
		result = new ModelAndView("soiree/create");
		
		result.addObject("soiree", soiree);
		result.addObject("message", message);
		return result;
	}

}
