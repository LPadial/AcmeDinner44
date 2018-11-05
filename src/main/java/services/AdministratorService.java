
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import repositories.AdministratorRepository;
import repositories.ChirpRepository;
import repositories.DinerRepository;
import repositories.SponsorshipRepository;

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


	// Constructors -----------------------------------------------------------
	
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
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

			adm = administratorRepository.save(adm);
		} else {
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
}

