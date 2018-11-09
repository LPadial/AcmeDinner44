package controllers;

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
import services.DishService;
import services.DishTypeService;
import services.SoireeService;

@Controller
@RequestMapping("/soiree")
public class SoireeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SoireeService soireeService;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private DishTypeService dishTypeService;


	// Constructors -----------------------------------------------------------
	public SoireeController() {
		super();
	}

	// Actions
	
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
	
	//Create ---------------------------------------------------------------
	@RequestMapping(value = "/dish/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) final int q) {
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Soiree s = soireeService.findOne(q);
		if(s.getOrganizer() == d){
			result = createNewModelAndView(dishService.create(s), null);
		}

		return result;
	}
	
	// Save ---------------------------------------------------------------
	@RequestMapping(value="/dish/save-create",method=RequestMethod.POST,params = "save")
	public ModelAndView saveCreate(@Valid Dish dish, BindingResult binding){ 
		ModelAndView res;
		if(binding.hasErrors()){ 
			res = createNewModelAndView(dish,null); 
		}else{
			try{
				dishService.save(dish); 
				res = new ModelAndView("redirect:/diner/soiree/organizedList.do");
			}catch(Throwable e){ 
				res = createNewModelAndView(dish,"dish.commit.error"); 
			} 
		} 
		return res;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Soiree q) {
		ModelAndView result;
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (d != null) {
			if (q.getOrganizer()==d) {
				result = createSoireeNewModelAndView(q, null);
			} else {
				result = new ModelAndView("redirect:/misc/403.do");
			}
		} else {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
	
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
	protected ModelAndView createNewModelAndView(Dish dish, String message) {
		ModelAndView result;

		if (dish.getId() == 0) {
			result = new ModelAndView("dish/create");
		} else {
			result = new ModelAndView("dish/edit");
		}
		result.addObject("dish", dish);
		result.addObject("dishTypes",dishTypeService.findAll());
		result.addObject("message", message);
		return result;
	}
	
	protected ModelAndView createSoireeNewModelAndView(Soiree soiree, String message) {
		ModelAndView result;
		result = new ModelAndView("soiree/edit");
		
		result.addObject("soiree", soiree);
		result.addObject("message", message);
		return result;
	}

}

