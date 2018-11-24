package controllers.diner;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DishTypeService;
import controllers.AbstractController;
import domain.DishType;

@Controller
@RequestMapping("/diner/dishType")
public class DinerDishTypeController extends AbstractController {

	// Services ---------------------------------------------------------------
	
	@Autowired
	private DishTypeService dishTypeService;


	// Constructors -----------------------------------------------------------
	public DinerDishTypeController() {
		super();
	}

	// Actions
	//Create ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView dishTypeCreate(@RequestParam(required = true) final int q) {
		ModelAndView result;
		
		result = createNewModelAndView(dishTypeService.create(),q,null);
		
		return result;
	}
	
	// Save ---------------------------------------------------------------
	
	@RequestMapping(value="/save-create",method=RequestMethod.POST,params = "save")
	public ModelAndView saveCreate(@Valid DishType dishType, BindingResult binding,@RequestParam (required = true) final Integer q){ 
		ModelAndView res = new ModelAndView("redirect:/misc/403.do");
		if(binding.hasErrors()){ 
			res = createNewModelAndView(dishType,q,null);
		}else{
			try{
				dishTypeService.save(dishType);
				res = new ModelAndView("redirect:/diner/dish/create.do?q="+q);
			}catch(Throwable e){ 
				createNewModelAndView(dishType,q,"dishType.commit.error");
			} 
		} 
		return res;
	}
	
	
	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createNewModelAndView(DishType dishType,Integer q, String message) {
		ModelAndView result;

		result = new ModelAndView("dishType/create");
	
		result.addObject("dishType", dishType);
		result.addObject("dishReturn",q);
		result.addObject("message", message);
		return result;
	}
	

}