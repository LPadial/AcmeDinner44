package controllers.diner;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.SocialSectionService;

import controllers.AbstractController;
import domain.Diner;
import domain.SocialSection;

@Controller
@RequestMapping("/diner/socialSection")
public class DinerSocialSectionController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private SocialSectionService socialSectionService;

	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------
	public DinerSocialSectionController() {
		super();
	}

	// Create ------------------------------------------------------------------
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result;

		SocialSection socialSection = socialSectionService.create();

		result = new ModelAndView("socialSection/edit");
		result.addObject("socialSection", socialSection);

		return result;
	}

	// Edit ---------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int q) {
		ModelAndView result;

		Diner d = (Diner) loginService.findActorByUsername(LoginService
				.getPrincipal().getUsername());
		Collection<SocialSection> socialSectionsOfDiner = d.getBusinessCard()
				.getSocialSections();
		SocialSection param = socialSectionService.findOne(q);

		if (socialSectionsOfDiner.contains(param)) {
			result = new ModelAndView("socialSection/edit");
			result.addObject("socialSection", param);
		} else {
			result = new ModelAndView("redirect:/misc/403.do");
		}

		return result;
	}

	@RequestMapping(value = "/save-edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSocialSection(@Valid SocialSection socialSection,
			BindingResult binding) {
		ModelAndView result;

		for (FieldError e : binding.getFieldErrors()) {
			System.out.println(e.getDefaultMessage());
		}

		if (binding.hasErrors()) {
			result = createEditModelAndView(socialSection, null);
		} else {
			try {
				socialSectionService.save(socialSection);
				result = new ModelAndView(
						"redirect:/diner/businessCard/myView.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(socialSection,
						"socialsection.commit.error");
			}
		}

		return result;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int q) {
		ModelAndView result;

		Diner d = (Diner) loginService.findActorByUsername(LoginService
				.getPrincipal().getId());

		SocialSection ss = socialSectionService.findOne(q);

		if (d != null) {
			if (d.getBusinessCard().getSocialSections().contains(ss)) {
				socialSectionService.delete(ss);
				result = new ModelAndView(
						"redirect:/diner/businessCard/myView.do");
			} else {
				result = new ModelAndView("redirect:/misc/403.do");
			}
		} else {
			return new ModelAndView("redirect:/misc/index.do");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(SocialSection socialSection,
			String message) {
		ModelAndView result;

		result = new ModelAndView("socialSection/edit");
		result.addObject("socialSection", socialSection);
		result.addObject("message", message);

		return result;
	}

}
