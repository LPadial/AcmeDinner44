
package services;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Chirp;
import domain.Finder;
import repositories.AdministratorRepository;
import repositories.ChirpRepository;
import repositories.DinerRepository;
import repositories.SponsorshipRepository;
import security.Authority;
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
	private DinerRepository dinerRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private FinderService finderService;


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

	// Other business methods -------------------------------------------------
	
	public Object[] avgMinMaxChirpsPerActor(){
		return chirpRepository.avgMinMaxChirpsPerActor();
	}
	
	public Object[] avgMinMaxRequestSponsorshipPerSponsor(){
		return sponsorshipRepository.avgMinMaxRequestSponsorshipPerSponsor();
	}
	
	public Object[] avgMinMaxAcceptedSponsorshipPerSponsor(){
		return sponsorshipRepository.avgMinMaxAcceptedSponsorshipPerSponsor();
	}
	
	public Integer numDiners(){
		return dinerRepository.numDiners();
	}
	
	public Object[] avgMinMaxScore(){
		return dinerRepository.avgMinMaxScore();
	}
}

