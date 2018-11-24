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
import domain.Dish;
import domain.DishType;
import domain.Soiree;
import domain.Vote;


@Controller
@RequestMapping("/diner/dish")
public class DinerDishController extends AbstractController {

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
	public DinerDishController() {
		super();
	}

	// Actions
	
	//Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = true) final int q) {
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Soiree s = soireeService.findOne(q);
		if(s.getOrganizer() == d){
			result = createNewModelAndView(dishService.create(s),q, null);
		}

		return result;
	}
	
	// Save ---------------------------------------------------------------
	@RequestMapping(value="/save-create",method=RequestMethod.POST,params = "save")
	public ModelAndView saveCreate(@Valid Dish dish, BindingResult binding,@RequestParam(required = true) final int q){ 
		ModelAndView res;
		if(binding.hasErrors()){ 
			res = createNewModelAndView(dish,q,null); 
		}else{
			try{
				dishService.save(dish); 
				
				res = new ModelAndView("redirect:/diner/soiree/organizedList.do");
			}catch(Throwable e){ 
				res = createNewModelAndView(dish,q,"dish.commit.error"); 
			} 
		} 
		return res;
	}	
	
	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createNewModelAndView(Dish dish, int soiree, String message) {
		ModelAndView result;

		if (dish.getId() == 0) {
			result = new ModelAndView("dish/create");
		} else {
			result = new ModelAndView("dish/edit");
		}
		result.addObject("dish", dish);
		result.addObject("soiree",soiree);
		result.addObject("dishTypes",dishTypeService.findAll());
		result.addObject("message", message);
		return result;
	}
	

}
