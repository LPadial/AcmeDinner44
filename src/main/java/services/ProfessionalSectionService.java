package services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.BusinessCard;
import domain.Diner;
import domain.ProfessionalSection;

import repositories.ProfessionalSectionRepository;
import security.LoginService;

@Service
@Transactional
public class ProfessionalSectionService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private ProfessionalSectionRepository professionalSectionRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private BusinessCardService businessCardService;
	
	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------
	public ProfessionalSectionService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public ProfessionalSection create() {
		ProfessionalSection professionalSection = new ProfessionalSection();		
		
		professionalSection.setTitle(new String("Professional Section"));
		professionalSection.setCompany(new String());
		professionalSection.setPosition(new String());
		
		return professionalSection;

	}
	
	public List<ProfessionalSection> findAll() {
		return professionalSectionRepository.findAll();
	}

	public ProfessionalSection findOne(Integer professionalSection) {
		Assert.notNull(professionalSection);
		return professionalSectionRepository.findOne(professionalSection);
	}
	
	
	
	public ProfessionalSection save(ProfessionalSection professionalSection) {
		Assert.notNull(professionalSection);
		Diner d = (Diner)loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(d instanceof Diner);
		ProfessionalSection aca = null;

		if (exists(professionalSection.getId())) {
			aca = findOne(professionalSection.getId());

			aca.setTitle(professionalSection.getTitle());
			aca.setCompany(professionalSection.getCompany());
			aca.setPosition(professionalSection.getPosition());

			aca = professionalSectionRepository.save(aca);
		} else {
			aca = professionalSectionRepository.save(professionalSection);
			
			d.getBusinessCard().addProfessionalSection(aca);
		}
		return aca;
	}

	public boolean exists(Integer professionalSectionID) {
		return professionalSectionRepository.exists(professionalSectionID);
	}
	
	
	public void delete(ProfessionalSection professionalSection) {
		Assert.notNull(professionalSection);
		
		BusinessCard bc = businessCardService.findBusinessCardOfProfessionalSection(professionalSection.getId());
		bc.removeProfessionalSection(professionalSection);		

		professionalSectionRepository.delete(professionalSection);
	}

	public void flush() {
		professionalSectionRepository.flush();
	}


	// Other business methods -------------------------------------------------

}
