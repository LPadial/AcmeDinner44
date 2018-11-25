package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialSectionRepository;
import security.LoginService;
import domain.BusinessCard;
import domain.Diner;
import domain.SocialSection;

@Service
@Transactional
public class SocialSectionService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private SocialSectionRepository socialSectionRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private BusinessCardService businessCardService;
	
	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------
	public SocialSectionService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public SocialSection create() {
		System.out.println("Entro en create service");
		SocialSection socialSection = new SocialSection();
		
		socialSection.setTitle(new String("Social Section"));
		socialSection.setNetwork(new String());
		socialSection.setNickname(new String());
		socialSection.setLink(new String());
		
		return socialSection;

	}
	
	public List<SocialSection> findAll() {
		return socialSectionRepository.findAll();
	}

	public SocialSection findOne(Integer socialSection) {
		Assert.notNull(socialSection);
		return socialSectionRepository.findOne(socialSection);
	}
	
	
	
	public SocialSection save(SocialSection socialSection) {
		Assert.notNull(socialSection);
		Diner d = (Diner)loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(d instanceof Diner);
		SocialSection aca = null;

		if (exists(socialSection.getId())) {
			aca = findOne(socialSection.getId());

			aca.setTitle(socialSection.getTitle());
			aca.setNetwork(socialSection.getNetwork());
			aca.setNickname(socialSection.getNickname());
			aca.setLink(socialSection.getLink());
			
			aca = socialSectionRepository.save(aca);
		} else {
			aca = socialSectionRepository.save(socialSection);
			d.getBusinessCard().addSocialSection(aca);
		}
		return aca;
	}

	public boolean exists(Integer socialSectionID) {
		return socialSectionRepository.exists(socialSectionID);
	}
	
	
	public void delete(SocialSection socialSection) {
		Assert.notNull(socialSection);
		
		BusinessCard bc = businessCardService.findBusinessCardOfSocialSection(socialSection.getId());
		bc.removeSocialSection(socialSection);		

		socialSectionRepository.delete(socialSection);
	}

	public void flush() {
		socialSectionRepository.flush();
	}


}
