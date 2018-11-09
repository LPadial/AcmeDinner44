package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Actor;
import domain.BussinessCard;
import domain.Chirp;
import domain.Diner;
import domain.Event;
import domain.Finder;

import repositories.DinerRepository;
import security.Authority;
import security.UserAccount;

@Service
@Transactional
public class DinerService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private DinerRepository dinerRepository;
	
	

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private FinderService finderService;
	
	@Autowired
	private BussinessCardService bussinessCardService;

	// Constructors -----------------------------------------------------------
	public DinerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Diner create() {
		Diner diner = new Diner();
		
		Finder finder = finderService.create();
		finder = finderService.save(finder);
		BussinessCard bussinessCard = bussinessCardService.create();
		bussinessCard = bussinessCardService.save(bussinessCard);
		
		
		
		diner.setFinder(finder);
		diner.setBussinessCard(bussinessCard);
		
		diner.setActorName(new String());
		diner.setSurname(new String());
		diner.setEmail(new String());
		diner.setAvgScore(new Double(0.0));
		diner.setFollowers(new ArrayList<Actor>());		
		diner.setChirps(new ArrayList<Chirp>());
		

		Authority a = new Authority();
		a.setAuthority(Authority.DINER);
		UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		diner.setUserAccount(account);

		return diner;

	}

	public List<Diner> findAll() {
		return dinerRepository.findAll();
	}

	public Diner findOne(Integer dinner) {
		Assert.notNull(dinner);
		return dinerRepository.findOne(dinner);
	}

	public Diner save(Diner diner) {
		Assert.notNull(diner);
		Diner aca = null;

		if (exists(diner.getId())) {
			aca = findOne(diner.getId());

			aca.setActorName(diner.getActorName());
			aca.setSurname(diner.getSurname());
			aca.setEmail(diner.getEmail());
			aca.setChirps(diner.getChirps());
			aca.setFollowers(diner.getFollowers());
			String fullName = diner.getActorName() + diner.getSurname();
			aca.getBussinessCard().getPersonalSection().setFullName(fullName);
			aca.setFinder(diner.getFinder());
			aca.getUserAccount().setUsername(diner.getUserAccount().getUsername());
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			aca.getUserAccount().setPassword(encoder.encodePassword(diner.getUserAccount().getPassword(), null));

			aca = dinerRepository.save(aca);
		} else {
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			diner.getUserAccount().setPassword(
					encoder.encodePassword(
							diner.getUserAccount().getPassword(), null));
			
			diner.getBussinessCard().getPersonalSection().setFullName(diner.getActorName() + " " + diner.getSurname());

			aca = dinerRepository.save(diner);
		}
		return aca;
	}

	public boolean exists(Integer dinerID) {
		return dinerRepository.exists(dinerID);
	}

	// Other business methods -------------------------------------------------
	
	public Collection<Diner> findDinersByKeyWord(String keyword){
		Collection<Diner> diners = new HashSet<Diner>();
		
		Collection<Diner> dinersName = dinerRepository.findDinersByKeywordInNameOrPersonalSection(keyword);
		diners.addAll(dinersName);
		
		Collection<Diner> dinersLike = dinerRepository.findDinersByKeyWordInLikesDislikes(keyword);		
		diners.addAll(dinersLike);
		
		Collection<Diner> dinersProfessional = dinerRepository.findDinersByKeyWordProfessionalSections(keyword);	
		diners.addAll(dinersProfessional);
		
		Collection<Diner> dinersSocial = dinerRepository.findDinersByKeyWordSocialSections(keyword);	
		diners.addAll(dinersSocial);
		
		return diners;
	}
	
	public Collection<Event> findEventsOfDiner(int dinerID) { 
		return dinerRepository.findEventsOfDiner(dinerID); 
	}
	
	public Collection<Event> findEventsPastOfDiner(int dinerID) {
		return dinerRepository.findEventsPastOfDiner(dinerID); 
	}
	 

}
