package services;




import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.BusinessCard;
import domain.PersonalSection;
import domain.ProfessionalSection;
import domain.SocialSection;

import repositories.BusinessCardRepository;


@Service
@Transactional
public class BusinessCardService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BusinessCardRepository businessCardRepository;


	// Supporting services ----------------------------------------------------
	
	@Autowired
	private PersonalSectionService personalSectionService;


	// Constructors -----------------------------------------------------------
	
	public BusinessCardService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public BusinessCard create() {
		BusinessCard businessCard = new BusinessCard();
		PersonalSection personalSection = personalSectionService.create();
		personalSection = personalSectionService.save(personalSection);
		
		businessCard.setPersonalSection(personalSection);
		
		ArrayList<ProfessionalSection> professionalSections = new ArrayList<ProfessionalSection>();
		ArrayList<SocialSection> socialSections = new ArrayList<SocialSection>();
		
		businessCard.setProfessionalSections(professionalSections);
		businessCard.setSocialSections(socialSections);
		
		return businessCard;

	}

	public List<BusinessCard> findAll() {
		return businessCardRepository.findAll();
	}

	public BusinessCard findOne(Integer businessCard) {
		Assert.notNull(businessCard);
		return businessCardRepository.findOne(businessCard);
	}

	public BusinessCard save(BusinessCard businessCard) {
		Assert.notNull(businessCard);
		BusinessCard aca = null;

		if (exists(businessCard.getId())) {
			aca = findOne(businessCard.getId());

			aca.setPersonalSection(businessCard.getPersonalSection());
			aca.setProfessionalSections(businessCard.getProfessionalSections());
			aca.setSocialSections(businessCard.getSocialSections());
			
			aca = businessCardRepository.save(aca);
		} else {
			aca = businessCardRepository.save(businessCard);
		}
		return aca;
	}

	public boolean exists(Integer businessCardID) {
		return businessCardRepository.exists(businessCardID);
	}

	// Other business methods -------------------------------------------------
	
	public BusinessCard findBusinessCardOfProfessionalSection(int IDProfessional){
		return businessCardRepository.findBusinessCardOfProfessionalSection(IDProfessional);
	}
	
	public BusinessCard findBusinessCardOfSocialSection(int IDSocial){
		return businessCardRepository.findBusinessCardOfSocialSection(IDSocial);
	}

}
