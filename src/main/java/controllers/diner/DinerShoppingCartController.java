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
import services.DinerService;
import services.ItemService;
import services.ShoppingCartService;
import services.SupermarketService;
import controllers.AbstractController;
import domain.Diner;
import domain.Item;
import domain.Supermarket;

@Controller
@RequestMapping("/diner/shoppingCart")
public class DinerShoppingCartController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private DinerService dinerService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
		
	@Autowired
	private LoginService loginService;
		
	// Constructors -----------------------------------------------------------
		
	public DinerShoppingCartController() {
		super();
	}

	// Supermarkets can list their items ----------------------------------------------------------------
	@RequestMapping(value = "/mylist", method = RequestMethod.GET)
	public ModelAndView myShoppingCarts() {
		ModelAndView result;
		result = new ModelAndView("shoppingCart/list");
		result.addObject("a", 0);
		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			result.addObject("shoppingCarts", dinerService.findShoppingCartsOfDiner(d.get));	
			result.addObject("requestURI","/diner/shoppingCart/mylist.do");
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
	
	// Change property retailed of a item ----------------------------------------------------------------

	@RequestMapping(value = "/changeToRetailed", method = RequestMethod.GET)
	public ModelAndView retailed(@RequestParam(required = true) final int q) {
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");
		Supermarket s = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Item item = itemService.findOne(q);
		if(item.getSupermarket()==s){
			itemService.changeToRetailed(item);
			result = new ModelAndView("redirect:/supermarket/item/mylist.do");			
		}			
		return result;			
	}
	
	@RequestMapping(value = "/changeToNotRetailed", method = RequestMethod.GET)
	public ModelAndView notRetailed(@RequestParam(required = true) final int q) {
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");
		Supermarket s = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Item item = itemService.findOne(q);
		if(item.getSupermarket()==s){
			itemService.changeToNotRetailed(item);
			result = new ModelAndView("redirect:/supermarket/item/mylist.do");			
		}			
		return result;			
	}
	
	//Copy the data to create new item
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public ModelAndView copy(@RequestParam(required = true) final int q) {
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");;
		Supermarket s = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Item item = itemService.findOne(q);
		if(item.getSupermarket()==s){
			result = new ModelAndView("item/copy");
			result.addObject("item", item);	
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
