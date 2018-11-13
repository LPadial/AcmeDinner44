package controllers;

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
import domain.Diner;
import domain.Dish;

@Controller
@RequestMapping("/dish")
public class DishController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private DishTypeService dishTypeService;


	// Constructors -----------------------------------------------------------
	public DishController() {
		super();
	}

	// Actions
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) int q) {
		ModelAndView result;
		result = new ModelAndView("dish/view");
		
		Dish d = dishService.findOne(q);
		
		result.addObject("dish", d);
		
		return result;
	}
	
	// Edit --------------------------------------------------------------------------------------
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Dish q) {
		ModelAndView result;
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (d != null) {
			if (q.getSoiree().getOrganizer()==d) {
				result = createNewModelAndView(q, null);
			} else {
				result = new ModelAndView("redirect:/misc/403.do");
			}
		} else {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
	
	//Save ------------------------------------------------------------------------------------------
	@RequestMapping(value="/save-create",method=RequestMethod.POST,params = "save")
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
	
	
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Dish q) {
		ModelAndView result;

		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (d != null) {
			if (q.getSoiree().getOrganizer() == d) {
				dishService.delete(q);
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
		result = new ModelAndView("dish/edit");
		
		result.addObject("dish", dish);
		result.addObject("dishTypes",dishTypeService.findAll());
		result.addObject("message", message);
		return result;
	}

}
