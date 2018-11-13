package controllers.supermarket;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ItemService;
import services.SupermarketService;
import controllers.AbstractController;
import domain.Item;
import domain.Supermarket;

@Controller
@RequestMapping("/supermarket/item")
public class SupermarketItemController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private SupermarketService supermarketService;
	
	@Autowired
	private ItemService itemService;
		
	@Autowired
	private LoginService loginService;
		
	// Constructors -----------------------------------------------------------
		
	public SupermarketItemController() {
		super();
	}

	// Supermarkets can list their items ----------------------------------------------------------------
	@RequestMapping(value = "/mylist", method = RequestMethod.GET)
	public ModelAndView items() {
		ModelAndView result;
		result = new ModelAndView("item/list");
		result.addObject("a", 0);
		if (LoginService.hasRole("SUPERMARKET")) {
			Supermarket s = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			result.addObject("items", supermarketService.findItemsOfSupermarket(s.getId()));	
		}		
		return result;
	}
	
	//View item ----------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) int q) {
		ModelAndView result;
		result = new ModelAndView("item/view");
		
		Item i = itemService.findOne(q);
		
		result.addObject("item", i);
		
		return result;
	}
	
	//Create item -----------------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		result = createNewModelAndView(itemService.create(), null);

		return result;
	}
	
	//Save item --------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/save-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreateEdit(@Valid Item item, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(item, null);
		} else {
			try {
				itemService.save(item);
				result = new ModelAndView("redirect:/supermarket/item/mylist.do");

			} catch (Throwable th) {
				result = createNewModelAndView(item, "item.commit.error");
			}
		}
		return result;
	}
		
	
		
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createNewModelAndView(Item item, String message) {
		ModelAndView result;
		
		result = new ModelAndView("item/create");
		
		result.addObject("item", item);
		result.addObject("message", message);
		
		return result;
	}
	


}
