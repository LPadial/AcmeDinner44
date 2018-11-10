package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Diner;
import domain.Soiree;
import domain.Vote;

import repositories.VoteRepository;
import security.LoginService;

@Service
@Transactional
public class VoteService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private VoteRepository voteRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService loginService;
	

	// Constructors -----------------------------------------------------------
	public VoteService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Vote create(Soiree s) {
		
		Assert.isTrue(s.getDate().before(Calendar.getInstance().getTime()),"Soiree must be over");		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Vote vote = null;
		if(dinerHasVoteInSoiree(d.getId(), s.getId())<1){			
			if(d.getEvents().contains(s.getEvent())){		
				vote = new Vote();
				
				vote.setPoints(new Integer(10));
				vote.setComments(new ArrayList<String>());
				vote.setPictures(new ArrayList<String>());
				vote.setVoter(d);
				vote.setsoiree(s);
				
			}
		}
		return vote;
	}
	
	public List<Vote> findAll() {
		return voteRepository.findAll();
	}

	public Vote findOne(Integer vote) {
		Assert.notNull(vote);
		return voteRepository.findOne(vote);
	}

	public Vote save(Vote vote) {
		Assert.notNull(vote);
		Vote aca = null;

		if (exists(vote.getId())) {
			aca = findOne(vote.getId());
			
			aca.setPoints(vote.getPoints());
			aca.setComments(vote.getComments());
			aca.setPictures(vote.getPictures());

			aca = voteRepository.save(aca);
		} else {
			aca = voteRepository.save(vote);
		}
		return aca;
	}

	public boolean exists(Integer voteID) {
		return voteRepository.exists(voteID);
	}
	
	public void delete(Vote vote) {
		Assert.notNull(vote);		
		voteRepository.delete(vote);
	}

	// Other business methods -------------------------------------------------
	 public Integer dinerHasVoteInSoiree(int dinerID, int soireeID){
		 return voteRepository.dinerHasVoteInSoiree(dinerID, soireeID);
	 }
}
