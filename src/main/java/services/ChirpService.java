
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Administrator;
import domain.Chirp;
import domain.Diner;
import domain.Sponsor;
import domain.Supermarket;

@Service
@Transactional
public class ChirpService {
	
	// Managed repository -----------------------------------------------------



	// Supporting services ----------------------------------------------------


	// Constructors -----------------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

	//Repository
	@Autowired
	private ChirpRepository			chirpRepository;

	//Services
	@Autowired
	private SupermarketService			supermarketService;

	@Autowired
	private DinerService			dinerService;
	
	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private LoginService			loginService;


	//Constructor
	public ChirpService() {
		super();
	}

	//CRUD Methods

	public Chirp create() {
		Chirp chirp = new Chirp();
		
		Actor a = loginService.findActorByUsername(LoginService.getPrincipal().getId());
		a.getChirps().add(chirp);
		chirp.setText(new String());
		chirp.setDateCreation(new Date());

		return chirp;
	}
	public void delete(Chirp chirp) {
		Assert.notNull(chirp);
		Actor a = loginService.findActorByUsername(LoginService.getPrincipal().getId());
		a.getChirps().remove(chirp);
		Assert.notNull(a);
		List<Chirp> chirps = a.getChirps();
		chirps.remove(chirp);
		a.setChirps(chirps);


		if (a.getUserAccount().getAuthorities().contains(Authority.DINER)) {
			dinerService.save((Diner) a);
		} else if (a.getUserAccount().getAuthorities().contains(Authority.SUPERMARKET)) {
			supermarketService.save((Supermarket) a);
		} else if (a.getUserAccount().getAuthorities().contains(Authority.ADMINISTRATOR)){
			administratorService.save((Administrator) a);
		} else if (a.getUserAccount().getAuthorities().contains(Authority.SPONSOR)){
			sponsorService.save((Sponsor) a);
		}


		chirpRepository.delete(chirp);
	}

	public List<Chirp> findAll() {
		return chirpRepository.findAll();
	}

	public Chirp findOne(int id) {
		return chirpRepository.findOne(id);
	}

	public Chirp save(Chirp chirp) {
		Assert.notNull(chirp);
		
		Actor a = loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		chirp = chirpRepository.save(chirp);
		
		List<Chirp> chirpsActor = a.getChirps();
		chirpsActor.add(chirp);
		a.setChirps(chirpsActor);
		
		if (a.getUserAccount().getAuthorities().contains(Authority.DINER)) {
			dinerService.save((Diner) a);
		} else if (a.getUserAccount().getAuthorities().contains(Authority.SUPERMARKET)) {
			supermarketService.save((Supermarket) a);
		} else if (a.getUserAccount().getAuthorities().contains(Authority.ADMINISTRATOR)){
			administratorService.save((Administrator) a);
		} else if (a.getUserAccount().getAuthorities().contains(Authority.SPONSOR)){
			sponsorService.save((Sponsor) a);
		}
		
		return chirpRepository.save(chirp);
	}

	public Actor suscribe(Actor actor) {
		Assert.notNull(actor);

		Actor ac = loginService.findActorByUsername(LoginService.getPrincipal().getId());

		ac.getFollowers().add(actor);

		if (ac.getUserAccount().getAuthorities().contains(Authority.DINER)) {
			dinerService.save((Diner) ac);
		} else if (ac.getUserAccount().getAuthorities().contains(Authority.SUPERMARKET)) {
			supermarketService.save((Supermarket) ac);
		} else if (ac.getUserAccount().getAuthorities().contains(Authority.ADMINISTRATOR)){
			administratorService.save((Administrator) ac);
		} else if (ac.getUserAccount().getAuthorities().contains(Authority.SPONSOR)){
			sponsorService.save((Sponsor) ac);
		}

		return actor;
	}

	//Other Methods
	public Collection<Chirp> chirpsOfActor(int actorId) {
		return chirpRepository.chirpsOfActor(actorId);
	}

	public Collection<Chirp> allChirpsOrderByMommentDesc() {
		return chirpRepository.allChirpsOrderByMommentDesc();
	}

	public Collection<Chirp> listChirpByFollower(int followerId) {
		return chirpRepository.listChirpByFollower(followerId);
	}

}
