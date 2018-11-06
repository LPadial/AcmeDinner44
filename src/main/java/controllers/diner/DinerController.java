package controllers.diner;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Diner;

import security.LoginService;
import services.DinerService;

@Controller
@RequestMapping("/diner")
public class DinerController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private DinerService dinerService;
	
	@Autowired
	private LoginService loginService;
	
	// Constructors -----------------------------------------------------------
	
	public DinerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		
		ModelAndView mav;
		mav = new ModelAndView("diner/list");
		
		Collection<Diner> diners;
		diners = dinerService.findAll();		
		mav.addObject("diners", diners);

		return mav;
	}
	
	@RequestMapping(value = "eventPast/list", method = RequestMethod.GET)
	public ModelAndView events(@RequestParam(required = true) final int q) {
		ModelAndView result;
		result = new ModelAndView("event/list");
		result.addObject("a", 0);
		result.addObject("events", dinerService.findEventsPastOfDiner(q));	
		
		return result;
	}
	
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(required = false) String q) {
		ModelAndView result;
		result = new ModelAndView("diner/list");
		
		result.addObject("a", 0);
		result.addObject("diners", dinerService.findDinersByKeyWord(q));
		
		return result;
	}
	
	
	//View
	@RequestMapping("/view")
	public ModelAndView view(@RequestParam Diner diner) {
		ModelAndView result;

		result = new ModelAndView("diner/view");
		result.addObject("diner", diner);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;

		res = new ModelAndView("diner/create");
		res.addObject("diner",dinerService.create());

		return res;
	}
	
	// Save ---------------------------------------------------------------
	@RequestMapping(value="/save",method=RequestMethod.POST,params = "save")
	public ModelAndView saveCreate(@Valid Diner diner, BindingResult binding){
		ModelAndView res;

		if(binding.hasErrors()){
			res = createModelAndView(diner,null);
		}else{
			try{
				dinerService.save(diner);
				res = new ModelAndView("redirect:/welcome/index.do");

			}catch(Throwable e){
				res = createModelAndView(diner, "diner.commit.error");
			}
		}
		return res;

	}


	/*@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Diner diner) {
		ModelAndView result;

		dinerService.delete(diner);
		result = new ModelAndView("redirect:list.do");

		return result;
	}*/
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createModelAndView(Diner diner,String message){
		ModelAndView res;
		res = new ModelAndView("diner/create");
		res.addObject("diner", diner);
		res.addObject("message",message);
		return res;
	}


}