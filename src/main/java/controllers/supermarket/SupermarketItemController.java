package controllers.supermarket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import services.DeliveryService;
import services.ItemService;
import services.SupermarketService;
import controllers.AbstractController;
import domain.Delivery;
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
	
	@Autowired
	private DeliveryService deliveryService;
		
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
			result.addObject("requestURI","/supermarket/item/mylist.do");
		}		
		return result;
	}
	
	// Supermarkets can list their items that they have to deliver grouped by delivery address ----------------------------------------------------------------
	@RequestMapping(value = "/notDelivered", method = RequestMethod.GET)
	public ModelAndView itemsNotDelivery() {
		ModelAndView result;
		result = new ModelAndView("item/group");
		
		if (LoginService.hasRole("SUPERMARKET")) {
			Supermarket s = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			List<Object[]> items = itemService.itemsOfSupermarketNotDeliveredGroupByDeliveredAddress(s.getId());
			HashMap<String, ArrayList<Item>> dirItems = new HashMap<String, ArrayList<Item>>();
			ArrayList<Item> listItems = new ArrayList<Item>();
			for(Object[] i: items){
				if(!dirItems.containsKey(i[0])){
					listItems = new ArrayList<Item>();
					listItems.add((Item)i[1]);
					dirItems.put((String)i[0], listItems);
				}else{
					listItems.add((Item)i[1]);
					dirItems.put((String)i[0], listItems);
				}
			}
			result.addObject("dirItems",dirItems);
			result.addObject("items", items);	
			result.addObject("notDelivered", 1);
			result.addObject("a", 1);
			result.addObject("requestURI","/supermarket/item/notDelivered.do");
		}		
		return result;
	}
	
	// Supermarkets can list their items that they have delivered grouped by delivery address ----------------------------------------------------------------
	@RequestMapping(value = "/delivered", method = RequestMethod.GET)
	public ModelAndView itemsDelivery() {
		ModelAndView result;
		result = new ModelAndView("item/group");
		if (LoginService.hasRole("SUPERMARKET")) {
			Supermarket s = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			List<Object[]> items = itemService.itemsOfSupermarketDeliveredGroupByDeliveredAddress(s.getId());
			HashMap<String, ArrayList<Item>> dirItems = new HashMap<String, ArrayList<Item>>();
			ArrayList<Item> listItems = new ArrayList<Item>();
			for(Object[] i: items){
				if(!dirItems.containsKey(i[0])){
					listItems = new ArrayList<Item>();
					listItems.add((Item)i[1]);
					dirItems.put((String)i[0], listItems);
				}else{
					listItems.add((Item)i[1]);
					dirItems.put((String)i[0], listItems);
				}
			}
			result.addObject("dirItems",dirItems);
			result.addObject("items", items);	
			result.addObject("a", 1);
			result.addObject("requestURI","/supermarket/item/delivered.do");
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
	
	@RequestMapping(value = "/save-copy", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCopy(@Valid Item item, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createCopyNewModelAndView(item, null);
		} else {
			try {
				itemService.save(item);
				result = new ModelAndView("redirect:/supermarket/item/mylist.do");

			} catch (Throwable th) {
				result = createCopyNewModelAndView(item, "item.commit.error");
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
	
	//Mark as delivered ----------------------------------------------------------------
	@RequestMapping(value = "/markDelivered", method = RequestMethod.GET)
	public ModelAndView markItemsDelivery(@RequestParam(required = true) final String address) {
		ModelAndView result  = new ModelAndView("redirect:/misc/403.do");

		if (LoginService.hasRole("SUPERMARKET")) {
			Supermarket s = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			List<Delivery> itemsToDeliver = itemService.itemsNotDeliveredInAddress(address, s.getId());
			for(Delivery d: itemsToDeliver){
				deliveryService.changeToDelivered(d);
			}
			result = new ModelAndView("redirect:/supermarket/item/notDelivered.do");
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
	
	protected ModelAndView createCopyNewModelAndView(Item item, String message) {
		ModelAndView result;
		
		result = new ModelAndView("item/copy");		
		
		result.addObject("item", item);
		result.addObject("message", message);
		
		return result;
	}
	


}
