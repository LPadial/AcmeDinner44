package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SoireeRepository;
import security.LoginService;
import domain.Diner;
import domain.Dish;
import domain.Event;
import domain.Soiree;
import domain.Sponsorship;
import domain.Vote;

@Service
@Transactional
public class SoireeService {

	// Managed repository -----------------------------------------------------
	
		@Autowired
		private SoireeRepository soireeRepository;

		// Supporting services ----------------------------------------------------
		
		@Autowired
		private LoginService loginService;
		
		@Autowired
		private DishService dishService;
		
		@Autowired
		private SponsorshipService sponsorshipService;


		// Constructors -----------------------------------------------------------
		public SoireeService(){
			super();
		}

		// Simple CRUD methods ----------------------------------------------------
		
		public Soiree create(Event e) {
			
			Collection<Diner> organizers = organizerOfSoireesOfEvent(e.getId());
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			
			Soiree soiree = null;
			if(!(organizers.contains(d)) && d.getEvents().contains(e)){
			
				soiree = new Soiree();
				
				soiree.setDate(new Date());		
				soiree.setAddress(new String());
				soiree.setPictures(new ArrayList<String>());
				soiree.setOrganizer(d);
				soiree.setDishes(new ArrayList<Dish>());
				soiree.setEvent(e);				
			}
			return soiree;

		}

		public List<Soiree> findAll() {
			return soireeRepository.findAll();
		}

		public Soiree findOne(Integer soiree) {
			Assert.notNull(soiree);
			return soireeRepository.findOne(soiree);
		}

		public Soiree save(Soiree soiree) {
			Assert.isTrue(Calendar.getInstance().getTime().before(soiree.getDate()),"Date must be in the future");
			Assert.notNull(soiree);
			Soiree aca = null;

			if (exists(soiree.getId())) {
				aca = findOne(soiree.getId());
				
				aca.setDate(soiree.getDate());
				aca.setAddress(soiree.getAddress());
				aca.setPictures(soiree.getPictures());
				aca.setDishes(soiree.getDishes());

				aca = soireeRepository.save(aca);
			} else {
				aca = soireeRepository.save(soiree);
			}
			return aca;
		}

		public boolean exists(Integer soireeID) {
			return soireeRepository.exists(soireeID);
		}
		
		public void delete(Soiree soiree) {
			Assert.notNull(soiree);
			
			Collection<Sponsorship> sponsorshipsToDelete = sponsorsihpOfSoiree(soiree.getId());
			for(Sponsorship ss: sponsorshipsToDelete){
				sponsorshipService.delete(ss);
			}
			
			if(soiree.getDishes().size() != 0){
				for(Dish d: soiree.getDishes() ){
					dishService.delete(d);
				}
			}
			
			soireeRepository.delete(soiree);
		}


		// Other business methods -------------------------------------------------
		
		public Collection<Diner> organizerOfSoireesOfEvent(int eventID){
			return soireeRepository.organizerOfSoireesOfEvent(eventID);
		}
		
		public Collection<Sponsorship> sponsorsihpOfSoiree(int soireeID){
			return soireeRepository.sponsorsihpOfSoiree(soireeID);
		}
		
		public Collection<Soiree> soireesOfDiner(int dinerID){
			return soireeRepository.soireesOfDiner(dinerID);
		}
		
		public Collection<Vote> votesOfSoiree(int soireeID){
			return soireeRepository.votesOfSoiree(soireeID);
		}
		
		public Collection<Dish> dishesOfSoiree(int soireeID){
			return soireeRepository.dishesOfSoiree(soireeID);
		}


}
