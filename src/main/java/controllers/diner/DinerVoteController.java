package controllers.diner;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.DishService;
import services.DishTypeService;
import services.SoireeService;
import services.VoteService;
import controllers.AbstractController;
import domain.Diner;
import domain.Soiree;
import domain.Vote;


@Controller
@RequestMapping("/diner/vote")
public class DinerVoteController extends AbstractController {

	// Services ---------------------------------------------------------------
	
	@Autowired
	private SoireeService soireeService;
	
	@Autowired
	private VoteService voteService;
	
	@Autowired
	private LoginService loginService;


	// Constructors -----------------------------------------------------------
	public DinerVoteController() {
		super();
	}

	// Actions
	//Create ---------------------------------------------------------------
	
	//Cast a vote
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView vote(@RequestParam(required = true) int q) {
		ModelAndView result;
		
		result = new ModelAndView("redirect:/misc/403.do");
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		if(voteService.dinerHasVoteInSoiree(d.getId(), q)==0){
			result = new ModelAndView("vote/create");
				
			Soiree s = soireeService.findOne(q);
				
			result.addObject("vote", voteService.create(s));	
		}
			
		return result;
	}
	
	// Save ---------------------------------------------------------------
	@RequestMapping(value="/save-create",method=RequestMethod.POST,params = "save")
	public ModelAndView saveCreate(@Valid Vote vote, BindingResult binding){ 
		ModelAndView res;
		if(binding.hasErrors()){ 
			res = createNewModelAndView(vote,null);
		}else{
			try{
				
				voteService.save(vote); 
				res = new ModelAndView("redirect:/diner/event/registeredList.do");
			}catch(Throwable e){ 
				res = createNewModelAndView(vote,"vote.commit.error");
			} 
		} 
		return res;
	}
	
	
	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createNewModelAndView(Vote vote, String message) {
		ModelAndView result;

	
		result = new ModelAndView("vote/create");
		
		result.addObject("vote", vote);
		result.addObject("message", message);
		return result;
	}
}