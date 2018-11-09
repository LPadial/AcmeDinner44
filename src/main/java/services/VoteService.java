package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Sponsor;
import domain.Sponsorship;
import domain.Vote;

import repositories.EventRepository;
import repositories.VoteRepository;

@Service
@Transactional
public class VoteService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private VoteRepository voteRepository;

	// Supporting services ----------------------------------------------------
	

	// Constructors -----------------------------------------------------------
	public VoteService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public void delete(Vote vote) {
		Assert.notNull(vote);		
		voteRepository.delete(vote);
	}

	// Other business methods -------------------------------------------------

}
