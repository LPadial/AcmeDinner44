
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Chirp;
import domain.Diner;
import domain.Event;
import domain.Finder;
import domain.Soiree;
import domain.Vote;
import repositories.AdministratorRepository;
import repositories.ChirpRepository;
import repositories.DinerRepository;
import repositories.ItemRepository;
import repositories.SponsorshipRepository;
import repositories.SupermarketRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Autowired
	private ChirpRepository chirpRepository;
	
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private DinerRepository dinerRepository;
	
	@Autowired
	private SupermarketRepository supermarketRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private FinderService finderService;
	
	@Autowired
	private DinerService dinerService;
	
	@Autowired
	private SoireeService soireeService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private LoginService loginService;

	

	// Constructors -----------------------------------------------------------
	
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Administrator create() {
		Administrator administrator = new Administrator();
		
		Finder finder = finderService.create();
		finder = finderService.save(finder);	
		
		administrator.setFinder(finder);
		
		administrator.setActorName(new String());
		administrator.setSurname(new String());
		administrator.setEmail(new String());
		administrator.setFollowers(new ArrayList<Actor>());		
		administrator.setChirps(new ArrayList<Chirp>());
		

		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		administrator.setUserAccount(account);

		return administrator;

	}
	
	public Administrator save(Administrator administrator) {
		Assert.notNull(administrator);
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		Administrator adm = null;

		if (exists(administrator.getId())) {
			adm = findOne(administrator.getId());

			adm.setActorName(administrator.getActorName());
			adm.setSurname(administrator.getSurname());
			adm.setEmail(administrator.getEmail());
			adm.setChirps(administrator.getChirps());
			adm.setFinder(administrator.getFinder());
			adm.getUserAccount().setUsername(administrator.getUserAccount().getUsername());
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			adm.getUserAccount().setPassword(encoder.encodePassword(administrator.getUserAccount().getPassword(), null));

			adm = administratorRepository.save(adm);
		} else {
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			administrator.getUserAccount().setPassword(
					encoder.encodePassword(
							administrator.getUserAccount().getPassword(), null));			
			
			adm = administratorRepository.save(administrator);
		}
		return adm;
	}

	public boolean exists(Integer administratorID) {
		return administratorRepository.exists(administratorID);
	}
	
	public Administrator findOne(Integer adminID) {
		return administratorRepository.findOne(adminID);
	}
	
	public List<Administrator> findAll() {
		return administratorRepository.findAll();
	}
	
	public void flush(){
		administratorRepository.flush();
	}
	

	// Other business methods -------------------------------------------------
	
	//Update the scores of the diners
	public void updateScores(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		Collection<Diner> diners = dinerService.findAll();
		
		for(Diner d: diners){
			Integer points = 0;
			Integer counter = 0;
			for(Soiree s: soireeService.soireesOfDiner(d.getId())){
				Collection<Vote> votesOfSoireeOrganizedByDiners = soireeService.votesOfSoiree(s.getId());
				for(Vote v: votesOfSoireeOrganizedByDiners){
					points += v.getPoints();
					counter ++;
				}
			}
			if(points != 0){
				Double avgScore = (double) (points/counter);
				d.setAvgScore(avgScore);
			}else{
				d.setAvgScore(0.0);
			}
		}
		
	}
	
	//Queries
	public Integer numDiners(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return administratorRepository.numDiners();
	}
	
	public Object[] avgMinMaxScore(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return administratorRepository.avgMinMaxScore();
	}
	
	public Double avgNumberOfEventsOrganisedByDiners(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		Integer counter = 0;
		Integer events = 0;
		for(Number i: administratorRepository.numEventForDinner()){
			events = events + i.intValue();
			counter++;
		}
		Double avg = (double) events/counter;		
		return avg;
	}
	
	public Number maxNumberOfEventsOrganisedByDiners(){			
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		List<Number> numEventsByDiner = administratorRepository.numEventForDinner();
		Number max = numEventsByDiner.get(0);
		return max;
	}
	
	public Number minNumberOfEventsOrganisedByDiners(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		List<Number> numEventsByDiner = administratorRepository.numEventForDinner();
		Number min = numEventsByDiner.get(numEventsByDiner.size()-1);
		return min;
	}
	
	
	public List<Array[]> dinersWhoHaveMoreEvents(){		
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		List<Array[]> dinersMoreEvents = administratorRepository.dinersWhoHaveMoreEvents();
		return dinersMoreEvents.subList(0,3);
	}
	
	public Double ratioOfEventsOver(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		Integer counter = 0;
		Double ratioOfEventsOver = 0.0;
		for(Event e: eventService.eventsWith4Soirees()){
			if(eventService.isOver(e)){
				counter++;
			}
		}
		ratioOfEventsOver = (double) counter/eventService.findAll().size();
		return ratioOfEventsOver;
	}
	
	public Object[] avgMinMaxNumberOfDishesPerSoiree(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return administratorRepository.avgMinMaxNumberOfDishesPerSoiree();
	}
	
	public Integer ratioOfDinersWhoHaveAtLeastOneProfessionalSection(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return administratorRepository.ratioOfDinersWhoHaveAtLeastOneProfessionalSection();
	}
	
	public Integer ratioOfDinersWhoHaveAtLeastOneSocialSection(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return administratorRepository.ratioOfDinersWhoHaveAtLeastOneSocialSection();
	}
	
	public Object[] avgMinMaxChirpsPerActor(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return chirpRepository.avgMinMaxChirpsPerActor();
	}
	
	public Object[] avgMinMaxRequestSponsorshipPerSponsor(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return sponsorshipRepository.avgMinMaxRequestSponsorshipPerSponsor();
	}
	
	public Object[] avgMinMaxAcceptedSponsorshipPerSponsor(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return sponsorshipRepository.avgMinMaxAcceptedSponsorshipPerSponsor();
	}
	public List<Object[]> bestSelledItems(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return itemRepository.bestSelledItems();
	}
	
	public List<Object[]> bestBuyingDiners(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return dinerRepository.bestBuyingDiners();
	}
	
	public List<Object[]> bestSellingSupermarkets(){
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		return supermarketRepository.bestSellingSupermarkets();
	}
	
}

