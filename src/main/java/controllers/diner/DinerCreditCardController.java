package controllers.diner;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;

import controllers.AbstractController;
import domain.CreditCard;

@Controller
@RequestMapping("/diner/creditCard")
public class DinerCreditCardController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CreditCardService creditCardService;

	// Constructors -----------------------------------------------------------
	public DinerCreditCardController() {
		super();
	}

	// Actions

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam (required = true) final int q) {
		ModelAndView result;

		result = createNewModelAndView(creditCardService.create(), null);
		result.addObject("idsc",q);

		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreateEdit(@Valid CreditCard creditCard, BindingResult binding, @RequestParam (required = true) final Integer q) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(creditCard, null);
			result.addObject("idsc",q);
		} else {
			try {
				creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/diner/shoppingCart/formOrder.do?q="+q);

			} catch (Throwable th) {
				result = createNewModelAndView(creditCard, "creditcard.commit.error");
				result.addObject("idsc",q);
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(CreditCard creditCard, String message) {
		ModelAndView result;

		result = new ModelAndView("creditCard/create");
		
		result.addObject("creditcard", creditCard);
		result.addObject("message", message);
		return result;
	}

}
