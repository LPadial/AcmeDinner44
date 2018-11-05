package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



import domain.Actor;
import domain.Diner;
import domain.Event;
import domain.Soiree;

import repositories.EventRepository;
import security.LoginService;

@Service
@Transactional
public class EventService {
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private EventRepository eventRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private LoginService loginService;


	// Constructors -----------------------------------------------------------
	public EventService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Event create() {
		Event event = new Event();
		
		event.setTicker(createTicker());		
		event.setTitle(new String());
		event.setCity(new String());
		event.setDescription(new String());
		Actor actor = loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		event.setOrganizer((Diner)actor);
		
		event.setSoirees(new ArrayList<Soiree>());

		return event;

	}
	
	public String createTicker(){
		StringBuilder str = new StringBuilder();
		
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd-HHmmss");
		str.insert(0, format.format(new Date()) + "-");
		
		final String dictionary = new String("ABCDEFGHIJKLOPQRSTUVWXYZ");
		
		
		Random rand = new Random();
		for(int i = 0; i < 4; i++) {
			str.append(dictionary.charAt(rand.nextInt(dictionary.length()))); 
		}
		
		return str.toString();
	}

	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	public Event findOne(Integer event) {
		Assert.notNull(event);
		return eventRepository.findOne(event);
	}

	public Event save(Event event) {
		Assert.notNull(event);
		Event aca = null;

		if (exists(event.getId())) {
			aca = findOne(event.getId());
			
			aca.setTitle(event.getTitle());
			aca.setCity(event.getCity());
			aca.setDescription(event.getDescription());
			aca.setSoirees(event.getSoirees());

			aca = eventRepository.save(aca);
		} else {

			aca = eventRepository.save(event);
		}
		return aca;
	}

	public boolean exists(Integer eventID) {
		return eventRepository.exists(eventID);
	}

	// Other business methods -------------------------------------------------
	
	public List<Event> findEventsByKeyWord(String keyword){
		return eventRepository.findEventsByKeyWord(keyword);
	}
	
	
	public Collection<Diner> findRegisteredDinerInEvents(int eventID){
		return eventRepository.findRegisteredDinerInEvents(eventID);
	}
}
