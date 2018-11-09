package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Sponsor;
import domain.Sponsorship;

import repositories.SponsorshipRepository;

@Service
@Transactional
public class SponsorshipService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private SponsorshipRepository sponsorshipRepository;

	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	public SponsorshipService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public void delete(Sponsorship sponsorship) {
		Assert.notNull(sponsorship);		
		Sponsor sponsor = sponsorshipRepository.findSponposOfSponsorship(sponsorship.getId());
		sponsor.removeSponsorship(sponsorship);
		sponsorshipRepository.delete(sponsorship);
	}

	// Other business methods -------------------------------------------------

}
