package controllers.sponsor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import controllers.AbstractController;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private SponsorService sponsorService;
	
	// Constructors -----------------------------------------------------------
	
	public SponsorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;

		res = new ModelAndView("sponsor/create");
		res.addObject("sponsor",sponsorService.create());

		return res;
	}
	
	// Save ---------------------------------------------------------------
	@RequestMapping(value="/save",method=RequestMethod.POST,params = "save")
	public ModelAndView saveCreate(@Valid Sponsor sponsor, BindingResult binding){
		ModelAndView res;

		if(binding.hasErrors()){
			res = createModelAndView(sponsor,null);
		}else{
			try{
				sponsorService.save(sponsor);
				res = new ModelAndView("redirect:/welcome/index.do");

			}catch(Throwable e){
				res = createModelAndView(sponsor, "sponsor.commit.error");
			}
		}
		return res;

	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createModelAndView(Sponsor supermarket,String message){
		ModelAndView res;
		res = new ModelAndView("sponsor/create");
		res.addObject("sponsor", supermarket);
		res.addObject("message",message);
		return res;
	}


}
