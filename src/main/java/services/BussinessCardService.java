package services;




import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.BussinessCard;
import domain.PersonalSection;
import domain.ProfessionalSection;
import domain.SocialSection;

import repositories.BussinessCardRepository;


@Service
@Transactional
public class BussinessCardService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BussinessCardRepository bussinessCardRepository;


	// Supporting services ----------------------------------------------------
	
	@Autowired
	private PersonalSectionService personalSectionService;


	// Constructors -----------------------------------------------------------
	
	public BussinessCardService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public BussinessCard create() {
		BussinessCard bussinessCard = new BussinessCard();
		PersonalSection personalSection = personalSectionService.create();
		personalSection = personalSectionService.save(personalSection);
		
		bussinessCard.setPersonalSection(personalSection);
		
		ArrayList<ProfessionalSection> professionalSections = new ArrayList<ProfessionalSection>();
		ArrayList<SocialSection> socialSections = new ArrayList<SocialSection>();
		
		bussinessCard.setProfessionalSections(professionalSections);
		bussinessCard.setSocialSections(socialSections);
		
		return bussinessCard;

	}

	public List<BussinessCard> findAll() {
		return bussinessCardRepository.findAll();
	}

	public BussinessCard findOne(Integer bussinessCard) {
		Assert.notNull(bussinessCard);
		return bussinessCardRepository.findOne(bussinessCard);
	}

	public BussinessCard save(BussinessCard bussinessCard) {
		Assert.notNull(bussinessCard);
		BussinessCard aca = null;

		if (exists(bussinessCard.getId())) {
			aca = findOne(bussinessCard.getId());

			aca.setPersonalSection(bussinessCard.getPersonalSection());
			aca.setProfessionalSections(bussinessCard.getProfessionalSections());
			aca.setSocialSections(bussinessCard.getSocialSections());

			aca = bussinessCardRepository.save(aca);
		} else {
			aca = bussinessCardRepository.save(bussinessCard);
		}
		return aca;
	}

	public boolean exists(Integer bussinessCardID) {
		return bussinessCardRepository.exists(bussinessCardID);
	}

	// Other business methods -------------------------------------------------
	
	public BussinessCard findBussinessCardOfProfessionalSection(int IDProfessional){
		return bussinessCardRepository.findBussinessCardOfProfessionalSection(IDProfessional);
	}
	
	public BussinessCard findBussinessCardOfSocialSection(int IDSocial){
		return bussinessCardRepository.findBussinessCardOfSocialSection(IDSocial);
	}

}
