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
import services.ProfessionalSectionService;

import controllers.AbstractController;
import domain.Diner;
import domain.ProfessionalSection;

@Controller
@RequestMapping("/diner/professionalSection")
public class DinerProfessionalSectionController extends AbstractController {

	// Services ---------------------------------------------------------------
	@Autowired
	private ProfessionalSectionService professionalSectionService;

	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------
	public DinerProfessionalSectionController() {
		super();
	}

	// Create -------------------------------------------------------------------
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result;

		ProfessionalSection professionalSection = professionalSectionService
				.create();

		result = new ModelAndView("professionalSection/edit");
		result.addObject("professionalSection", professionalSection);

		return result;
	}

	// Edit ---------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int q) {
		ModelAndView result;

		Diner d = (Diner) loginService.findActorByUsername(LoginService
				.getPrincipal().getUsername());
		Collection<ProfessionalSection> professionalSectionsOfDiner = d
				.getBusinessCard().getProfessionalSections();
		ProfessionalSection param = professionalSectionService.findOne(q);

		if (professionalSectionsOfDiner.contains(param)) {
			result = new ModelAndView("professionalSection/edit");
			result.addObject("professionalSection", param);
		} else {
			result = new ModelAndView("redirect:/misc/403.do");
		}

		return result;
	}

	@RequestMapping(value = "/save-edit", method = RequestMethod.POST, params = "save")
	public ModelAndView savePersonalSection(
			@Valid ProfessionalSection professionalSection,
			BindingResult binding) {
		ModelAndView result;

		for (FieldError e : binding.getFieldErrors()) {
			System.out.println(e.getDefaultMessage());
		}

		if (binding.hasErrors()) {
			result = createEditModelAndView(professionalSection, null);
		} else {
			try {
				professionalSectionService.save(professionalSection);
				result = new ModelAndView(
						"redirect:/diner/businessCard/myView.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(professionalSection,
						"professionalsection.commit.error");
			}
		}

		return result;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam int q) {
		ModelAndView result;

		Diner d = (Diner) loginService.findActorByUsername(LoginService
				.getPrincipal().getId());

		ProfessionalSection ps = professionalSectionService.findOne(q);

		if (d != null) {
			if (d.getBusinessCard().getProfessionalSections().contains(ps)) {
				professionalSectionService.delete(ps);
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

	protected ModelAndView createEditModelAndView(
			ProfessionalSection professionalSection, String message) {
		ModelAndView result;

		result = new ModelAndView("professionalSection/edit");
		result.addObject("professionalSection", professionalSection);
		result.addObject("message", message);

		return result;
	}

}
