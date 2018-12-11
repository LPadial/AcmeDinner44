package controllers.diner;

import java.util.ArrayList;
import java.util.Calendar;
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
import domain.Soiree;

@Controller
@RequestMapping("/diner/soiree")
public class DinerSoireeController extends AbstractController {

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

	public DinerSoireeController() {
		super();
	}
	
	//Actions ------------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) int q) {
		ModelAndView result;
		result = new ModelAndView("soiree/view");
		
		Soiree s = soireeService.findOne(q);
		
		result.addObject("soiree", s);
		result.addObject("organizer",s.getOrganizer());
		result.addObject("dishes", s.getDishes());		
		
		return result;
	}

	// Soirees organized by logged diner
	@RequestMapping(value = "/organizedList", method = RequestMethod.GET)
	public ModelAndView soireesOrganized() {
		ModelAndView result;
		ArrayList<Soiree> dinerCanCastAVote = new ArrayList<Soiree>();
		ArrayList<Soiree> pastSoirees = new ArrayList<Soiree>();
		result = new ModelAndView("soiree/list");

		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService
					.getPrincipal().getId());
			Collection<Soiree> soirees = soireeService
					.soireesOfDiner(d.getId());
			result.addObject("soirees", soirees);

			for (Soiree s : soirees) {
				if (eventService.isOver(s.getEvent())
						&& voteService.dinerHasVoteInSoiree(d.getId(),
								s.getId()) < 1) {
					dinerCanCastAVote.add(s);
				}
				if(s.getDate().before(Calendar.getInstance().getTime())){
					pastSoirees.add(s);
				}
			}
			result.addObject("dinerCanCastAVote", dinerCanCastAVote);
			result.addObject("pastSoirees", pastSoirees);
			result.addObject("isRegisteredInEvent", true);
			result.addObject("soireesOfDiner", soirees);
		}
		
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) final int q) {
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		Collection<Diner> dinersCreators = soireeService.organizerOfSoireesOfEvent(q);

		if (!dinersCreators.contains(d)) {
			result = createNewModelAndView(soireeService.create(eventService.findOne(q)), null);
		}

		return result;
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Soiree q) {
		ModelAndView result;
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());

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

	// Save ---------------------------------------------------------------

	@RequestMapping(value = "/save-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Soiree soiree, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = createNewModelAndView(soiree, null);
		} else {
			try {
				soireeService.save(soiree);
				res = new ModelAndView("redirect:/diner/soiree/organizedList.do");
			} catch (Throwable e) {
				res = createNewModelAndView(soiree, "soiree.commit.error");
			}
		}
		return res;
	}

	// Delete
	// ----------------------------------------------------------------------------------
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Soiree q) {
		ModelAndView result;

		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (d != null) {
			if (q.getOrganizer() == d) {
				soireeService.delete(q);
				result = new ModelAndView("redirect:/diner/soiree/organizedList.do");
			} else {
				result = new ModelAndView("redirect:/misc/403.do");
			}
		} else {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createNewModelAndView(Soiree soiree, String message) {
		ModelAndView result;
		if (soiree.getId() == 0) {
			result = new ModelAndView("soiree/create");
		} else {
			result = new ModelAndView("soiree/edit");
		}

		result.addObject("soiree", soiree);
		result.addObject("message", message);
		return result;
	}

}
