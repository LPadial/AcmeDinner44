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
import domain.BusinessCard;
import domain.Chirp;
import domain.CreditCard;
import domain.Diner;
import domain.Event;
import domain.Finder;
import domain.ShoppingCart;

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
	private BusinessCardService businessCardService;

	// Constructors -----------------------------------------------------------
	public DinerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Diner create() {
		Diner diner = new Diner();
		
		Finder finder = finderService.create();
		finder = finderService.save(finder);
		BusinessCard businessCard = businessCardService.create();
		businessCard = businessCardService.save(businessCard);
		
		
		
		diner.setFinder(finder);
		diner.setBusinessCard(businessCard);
		
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
		Assert.notNull(diner.getUserAccount().getUsername(),"error.username");
		Assert.notNull(diner.getUserAccount().getPassword(),"error.password");
		Assert.isTrue(diner.getUserAccount().getUsername().length()>=5 && diner.getUserAccount().getUsername().length()<=32, "error.username.length");
		Assert.isTrue(diner.getUserAccount().getPassword().length()>=5 && diner.getUserAccount().getPassword().length()<=32, "error.password.length");
		Diner aca = null;
		
		
		if (exists(diner.getId())) {
			
			aca = findOne(diner.getId());
			

			aca.setActorName(diner.getActorName());
			aca.setSurname(diner.getSurname());
			aca.setEmail(diner.getEmail());
			aca.setChirps(diner.getChirps());
			aca.setFollowers(diner.getFollowers());
			String fullName = diner.getActorName() + diner.getSurname();
			aca.getBusinessCard().getPersonalSection().setFullName(fullName);
			aca.setFinder(diner.getFinder());
			
			aca.getUserAccount().setUsername(diner.getUserAccount().getUsername());
			
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String encryptPassword = encoder.encodePassword(diner.getUserAccount().getPassword(), null);
			aca.getUserAccount().setPassword(encryptPassword);

			aca = dinerRepository.save(aca);
		} else {
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			diner.getUserAccount().setPassword(
					encoder.encodePassword(
							diner.getUserAccount().getPassword(), null));
			
			diner.getBusinessCard().getPersonalSection().setFullName(diner.getActorName() + " " + diner.getSurname());

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

	public List<ShoppingCart> findShoppingCartsOfDiner(int idDiner) {
		return dinerRepository.findShoppingCartsOfDiner(idDiner);
	}
	
	public List<CreditCard> findCreditCardsOfDiner(int idDiner){
		return dinerRepository.findCreditCardsOfDiner(idDiner);
	}
	 

}
