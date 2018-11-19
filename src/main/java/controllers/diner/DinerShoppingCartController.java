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
import controllers.AbstractController;
import domain.Diner;
import domain.Event;
import domain.ShoppingCart;
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
	
	@Autowired
	private ItemService itemService;
		
	// Constructors -----------------------------------------------------------
		
	public DinerShoppingCartController() {
		super();
	}

	// Supermarkets can list their items ----------------------------------------------------------------
	@RequestMapping(value = "/mylist", method = RequestMethod.GET)
	public ModelAndView myShoppingCarts() {
		ModelAndView result;
		result = new ModelAndView("shoppingCart/list");
		if (LoginService.hasRole("DINER")) {
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			result.addObject("shoppingCarts", dinerService.findShoppingCartsOfDiner(d.getId()));	
			result.addObject("requestURI","/diner/shoppingCart/mylist.do");
		}		
		return result;
	}
	
	//View item ----------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) int q) {
		ModelAndView result;
		result = new ModelAndView("shoppingCart/view");
		
		ShoppingCart shoppingCart = shoppingCartService.findOne(q);
		
		result.addObject("shoppingCart", shoppingCart);
		result.addObject("items", shoppingCartService.listItemsOfShoppingCart(q));
		
		return result;
	}
	
	//Create item -----------------------------------------------------------------------------------------------
	@RequestMapping(value = "/createShoppingCart", method = RequestMethod.GET)
	public ModelAndView createShoppingCart() {
		
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");
		
		ShoppingCart shoppingCart = shoppingCartService.create();
		
		shoppingCartService.save(shoppingCart);
		result = new ModelAndView("redirect:/diner/shoppingCart/mylist.do");

		return result;
	}
	
	//Save item --------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/save-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreateEdit(@Valid ShoppingCart shoppingCart, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(shoppingCart, null);
		} else {
			try {
				shoppingCartService.save(shoppingCart);
				result = new ModelAndView("redirect:/diner/shoppingCart/mylist.do");

			} catch (Throwable th) {
				result = createNewModelAndView(shoppingCart, "shoppingCart.commit.error");
			}
		}
		return result;
	} 
	
	//Order shoppingCart--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/formOrder", method = RequestMethod.POST)
	public ModelAndView order(@RequestParam(required = true) final int q) {
		ModelAndView result;
		
		result = createNewModelAndView(shoppingCartService.findOne(q), null);
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		ShoppingCart shoppingCart = shoppingCartService.findOne(q);
		if(shoppingCart.getOwner()==d){
			shoppingCartService.order(shoppingCart);
			result = new ModelAndView("redirect:/diner/shoppingCart/mylist.do");			
		}			
		return result;
	}
	
	//Delete shopping cart -------------------------------------------------------------------------------------------------------
	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam ShoppingCart q) {
		ModelAndView result;

		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());

		if (d != null) {
			if (q.getOwner() == d && q.getIsOrdered() == false) {
				shoppingCartService.delete(q);
				result = new ModelAndView("redirect:/diner/shoppingCart/mylist.do");
			} else {
				result = new ModelAndView("redirect:/misc/403.do");
			}
		} else {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
	
	//Add or delete items
	@RequestMapping("/modifyItems")
	public ModelAndView modifyItems(@RequestParam(required = true) final int q) {
		ModelAndView result;
		
		result = new ModelAndView("item/list");
		result.addObject("a",0);
		result.addObject("items", itemService.findAll());	
		result.addObject("requestURI","/diner/shoppingCart/modifyItems.do?q="+q);
				
		return result;
	}
		
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createNewModelAndView(ShoppingCart shoppingCart, String message) {
		ModelAndView result;
		
		result = new ModelAndView("shoppingCart/formOrder");
		
		result.addObject("shoppingCart", shoppingCart);
		result.addObject("message", message);
		
		return result;
	}
	


}
