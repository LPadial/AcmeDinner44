package services;

import java.util.ArrayList;
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

@Service
@Transactional
public class SoireeService {

	// Managed repository -----------------------------------------------------
	
		@Autowired
		private SoireeRepository soireeRepository;

		// Supporting services ----------------------------------------------------
		
		@Autowired
		private LoginService loginService;


		// Constructors -----------------------------------------------------------
		public SoireeService(){
			super();
		}

		// Simple CRUD methods ----------------------------------------------------
		
		public Soiree create(Event e) {
			Soiree soiree = new Soiree();
			
			soiree.setDate(new Date());		
			soiree.setAddress(new String());
			soiree.setPictures(new ArrayList<String>());
			Diner d = (Diner)loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
			soiree.setOrganizer(d);
			soiree.setDishes(new ArrayList<Dish>());
			soiree.setSponsorships(new ArrayList<Sponsorship>());
			soiree.setEvent(e);

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

		// Other business methods -------------------------------------------------
		
		/*public List<Event> findEventsByKeyWord(String keyword){
			return eventRepository.findEventsByKeyWord(keyword);
		}*/


}
