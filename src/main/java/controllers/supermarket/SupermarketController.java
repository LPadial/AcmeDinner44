package controllers.supermarket;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SupermarketService;
import controllers.AbstractController;
import domain.Supermarket;

@Controller
@RequestMapping("/supermarket")
public class SupermarketController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private SupermarketService supermarketService;
	
	// Constructors -----------------------------------------------------------
	
	public SupermarketController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;

		res = new ModelAndView("supermarket/create");
		res.addObject("supermarket",supermarketService.create());

		return res;
	}
	
	// Save ---------------------------------------------------------------
	@RequestMapping(value="/save",method=RequestMethod.POST,params = "save")
	public ModelAndView saveCreate(@Valid Supermarket supermarket, BindingResult binding){
		ModelAndView res;

		if(binding.hasErrors()){
			res = createModelAndView(supermarket,null);
		}else{
			try{
				supermarketService.save(supermarket);
				res = new ModelAndView("redirect:/welcome/index.do");

			}catch(Throwable e){
				res = createModelAndView(supermarket, "supermarket.commit.error");
			}
		}
		return res;

	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createModelAndView(Supermarket supermarket,String message){
		ModelAndView res;
		res = new ModelAndView("supermarket/create");
		res.addObject("supermarket", supermarket);
		res.addObject("message",message);
		return res;
	}


}
