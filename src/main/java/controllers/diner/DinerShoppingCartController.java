package controllers.diner;

import java.util.ArrayList;
import java.util.List;

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
import domain.CreditCard;
import domain.Diner;
import domain.Item;
import domain.ShoppingCart;

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
	
	// Items that have a shopping cart ----------------------------------------------------------------
	@RequestMapping(value = "/seeItems", method = RequestMethod.GET)
	public ModelAndView seeItems(@RequestParam(required = true) final int q) {
		ModelAndView result;
		result = new ModelAndView("item/list");
		result.addObject("a",0);
		result.addObject("b",0);
		List<Item> items = shoppingCartService.listItemsOfShoppingCart(q);
		result.addObject("items", items);	
		result.addObject("requestURI","/diner/shoppingCart/seeItems.do?q"+q);	
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
	
	
	//Order shoppingCart--------------------------------------------------------------------------------------------------
	@RequestMapping("/formOrder")
	public ModelAndView order(@RequestParam(required = true) final int q) {
		ModelAndView result;
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		ShoppingCart shoppingCart = shoppingCartService.findOne(q);
		
		if(shoppingCart.getOwner()==d){
			result = createNewModelAndView(shoppingCartService.findOne(q), null);
			List<CreditCard> creditCards = dinerService.findCreditCardsOfDiner(d.getId());
			
			result.addObject("creditCards",creditCards);		
		} else {
			result = new ModelAndView("redirect:/misc/403.do");
		}
		return result;
	}
	
	//Save shopping cart --------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/save-order", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreateEdit(@Valid ShoppingCart shoppingcart, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(shoppingcart, null);
		} else {
			try {
				shoppingCartService.order(shoppingcart);
				result = new ModelAndView("redirect:/diner/shoppingCart/mylist.do");

			} catch (Throwable th) {
				result = createNewModelAndView(shoppingcart, "shoppingcart.commit.error");
				Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
				List<CreditCard> creditCards = dinerService.findCreditCardsOfDiner(d.getId());				
				result.addObject("creditCards",creditCards);
			}
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
		List<Item> itemsToDelete = new ArrayList<Item>();
		result = new ModelAndView("item/list");
		result.addObject("a",0);
		result.addObject("items", itemService.findAll());	
		for(Item i : itemService.findAll()){
			if(shoppingCartService.countItemInShoppingCart(q, i.getId()) >= 1){
				itemsToDelete.add(i);
			}			
		}
		result.addObject("itemsToDelete",itemsToDelete);
		result.addObject("shoppingCart",q);
		result.addObject("requestURI","/diner/shoppingCart/modifyItems.do?q="+q);
				
		return result;
	}
	
	@RequestMapping(value = "/addItem")
	public ModelAndView addItem(@RequestParam(required = true) final int shoppingCart, @RequestParam(required = true) final int item) {
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		ShoppingCart sc = shoppingCartService.findOne(shoppingCart);
		if(sc.getOwner() == d){
			shoppingCartService.addItem(sc, itemService.findOne(item));
			result = new ModelAndView("redirect:/diner/shoppingCart/modifyItems.do?q="+shoppingCart);
		}
		
				
		return result;
	}
	
	@RequestMapping(value = "/removeItem")
	public ModelAndView removeItem(@RequestParam(required = true) final int shoppingCart, @RequestParam(required = true) final int item) {
		ModelAndView result = new ModelAndView("redirect:/misc/403.do");
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		ShoppingCart sc = shoppingCartService.findOne(shoppingCart);
		Integer numItem = shoppingCartService.countItemInShoppingCart(shoppingCart, item);
		if(sc.getOwner() == d && numItem>=1){
			shoppingCartService.removeItem(shoppingCartService.findOne(shoppingCart), itemService.findOne(item));
			result = new ModelAndView("redirect:/diner/shoppingCart/modifyItems.do?q="+shoppingCart);
		}
				
		return result;
	}
		
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createNewModelAndView(ShoppingCart shoppingCart, String message) {
		ModelAndView result;
		
		result = new ModelAndView("shoppingCart/formOrder");
		
		result.addObject("shoppingcart", shoppingCart);
		result.addObject("message", message);
		
		return result;
	}
	


}
