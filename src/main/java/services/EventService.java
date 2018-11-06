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



import domain.Diner;
import domain.Dish;
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
	
	@Autowired
	private SoireeService soireeService;
	
	@Autowired
	private DishService dishService;


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
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		event.setOrganizer(d);
		
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
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
			d.getEvents().add(aca);
		}
		return aca;
	}

	public boolean exists(Integer eventID) {
		return eventRepository.exists(eventID);
	}
	
	public void delete(Event event) {
		Assert.notNull(event);
		
		Collection<Diner> diners = findRegisteredDinerInEvents(event.getId());
		
		for(Diner d: diners){
			d.getEvents().remove(event);
		}
		
		eventRepository.delete(event);
	}

	// Other business methods -------------------------------------------------
	
	public List<Event> findEventsByKeyWord(String keyword){
		return eventRepository.findEventsByKeyWord(keyword);
	}
	
	
	public Collection<Diner> findRegisteredDinerInEvents(int eventID){
		return eventRepository.findRegisteredDinerInEvents(eventID);
	}
	
	public void registerToEvent(int e){
		Collection<Diner> registeredDiners = findRegisteredDinerInEvents(e);
		Integer numRegistered = registeredDiners.size();
		if(numRegistered<4){
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
			d.getEvents().add(findOne(e));
		}
	}
}