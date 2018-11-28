package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Diner;
import domain.Event;

import security.LoginService;
import services.DinerService;
import services.EventService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EventUseCaseTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private EventService eventService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private DinerService dinerService;

	// Templates

	/*
	 * 9.2: Search for events using a single keyword that must appear in its
	 * title or its description. If a user can show an event, then he or she
	 * must be able to navigate to the corresponding soirees, dishes, and
	 * diners.
	 */
	public void templateEventSearch(final String keyword,
			final Class<?> expected) {
		Class<?> caught = null;

		try {
			eventService.findEventsByKeyWord(keyword);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void displayDriver() {

		final Object testingData[][] = {

				// Test #01: Correct access. Expected true.
				{ "event", null },

				// Test #02: Attempt to search without keyword. Expected false.
				{ null, IllegalArgumentException.class },

				// Test #03: Attempt to search with empty keyword. Expected
				// false.
				{ "", IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEventSearch((String) testingData[i][0],
					(Class<?>) testingData[i][1]);
	}

	// Templates

	/*
	 * 11.2: Manage his or her events, which include listing, showing, creating, updating, and deleting them.   Note that deleting an event implies that the corresponding soirees and dishes are also deleted.
	 */
	/*
	 * 11.2.1: Create event
	 */
	public void templateCreateEvent(final String username, final String title, final String city, final String description, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Event event = eventService.create();
			event.setTitle(title);
			event.setCity(city);
			event.setDescription(description);

			eventService.save(event);
			eventService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateEvent() {
		
		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{"diner1","title1","city1","description1", null },

				// Test #02: Attempt to create a event with fields blank. Expected false.
				{"diner1","","","", ConstraintViolationException.class },
				
				// Test #03: Attempt to create a event by a supermarket. Expected false.
				{"supermarket1","title1","city1","description1", ConstraintViolationException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateEvent((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	
	/*
	 * 11.2.2: Update event
	 */
	public void templateEditEvent(final String username, final String title, final String city, final String description, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);			
			Diner diner = (Diner) loginService.findActorByUsername(username);
			Event event = dinerService.findEventsOfDiner(diner.getId()).iterator().next();
			event.setTitle(title);
			event.setCity(city);
			event.setDescription(description);

			eventService.save(event);
			eventService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditEvent() {
		
		final Object testingData[][] = {
				
				// Test #01: Correct access. Expected true.
				{"diner1","title1","city1","description1", null },
				
				// Test #02: Correct access. Expected true.
				{"diner2","title2","city2","description2", null },
				
				// Test #03: Correct access. Expected true.
				{"diner3","title2","city2","description2", null },
				
				// Test #04: Attempt to edit a event by a admin. Expected false.
				{"admin1","title1","city1","description1", ClassCastException.class },
				
				// Test #05: Attempt to edit a event by a supermarket. Expected false.
				{"supermarket1","title1","city1","description1", ClassCastException.class },

				// Test #06: Attempt to edit a event with title blank. Expected false.
				{"diner1","","city1","description1", ConstraintViolationException.class },
				
				// Test #07: Attempt to edit a event with city blank. Expected false.
				{"diner1","title1","","description1", ConstraintViolationException.class },
				
				// Test #08: Attempt to edit a event with description blank. Expected false.
				{"diner1","title1","city1","", ConstraintViolationException.class },
				
				// Test #09: Attempt to edit a event of a user anonymous. Expected false.
				{null,"title1","city1","description1", IllegalArgumentException.class },
				
				// Test #10: Attempt to edit a event with fields null. Expected false.
				{"diner1",null,null,null, ConstraintViolationException.class },			
				

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditEvent((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	
	/*
	 * 11.2.3: Delete event
	 */
	public void templateDeleteEvent(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);			
			Diner diner = (Diner) loginService.findActorByUsername(username);
			Event event = dinerService.findEventsOfDiner(diner.getId()).iterator().next();
			eventService.delete(event);
			eventService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeleteEvent() {
		
		final Object testingData[][] = {
				
				// Test #01: Correct access. Expected true.
				{"diner1", null },
				
				// Test #02: Attempt to delete a event by a supermarket. Expected false.
				{"supermarket1", ClassCastException.class },
				
				// Test #03: Attempt to delete a event of a user anonymous. Expected false.
				{null, IllegalArgumentException.class }			

		};
		
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteEvent((String) testingData[i][0],(Class<?>) testingData[i][1]);
	}
	
	/*
	 * 11.2.4: List and show event
	 */
	public void templateListEvent(final String city, final Class<?> expected) {
		Class<?> caught = null;

		try {
					
			Integer eventid = eventService.findAll().iterator().next().getId();
			Event eventUpdate = eventService.findOne(eventid);
			Diner diner = eventUpdate.getOrganizer();
			this.authenticate(diner.getUserAccount().getUsername());	
			eventUpdate.setCity(city);
			eventService.save(eventUpdate);
			eventService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverListEvent() {
		
		final Object testingData[][] = {
				
				// Test #01: Correct access. Expected true.
				{"city1", null }		

		};
		
		for (int i = 0; i < testingData.length; i++)
			this.templateListEvent((String) testingData[i][0],(Class<?>) testingData[i][1]);
	}
	
	/*
	 * 11.3: Manage his or her registrations to events, which includes listing, showing, creating, and deleting them.  A diner cannot register or unregister from an event that he or she organises.  A diner can unregister from an event that he or she has not going to organise at will.
	 */
	/*
	 * 11.3.1: Register event and list registrations to events of diner
	 */
	public void templateRegisterToEvent(final String username, int eventid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Diner diner = (Diner) loginService.findActorByUsername(username);
			
			Integer sizeBefore = diner.getEvents().size();
			
			eventService.registerToEvent(eventid);
			
			Integer sizeAfter = diner.getEvents().size();
			Assert.isTrue(sizeBefore<sizeAfter);

			eventService.flush();
			
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverRegisterEvent() {
		
		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{"diner2",2194, null },

				// Test #02: Attempt to register in a event already registered . Expected false.
				{"diner2",2188, IllegalArgumentException.class },
				
				// Test #03: Attempt to register in a event that he has organized . Expected false.
				{"diner2",2185, IllegalArgumentException.class },

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterToEvent((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	/*
	 * 11.3.2: Unregister event
	 */
	public void templateUnregisterToEvent(final String username, int eventid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Diner diner = (Diner) loginService.findActorByUsername(username);
			
			Integer sizeBefore = diner.getEvents().size();
			
			eventService.unregisterToEvent(eventid);
			
			Integer sizeAfter = diner.getEvents().size();
			Assert.isTrue(sizeBefore>sizeAfter);

			eventService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverUnregisterEvent() {
		
		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{"diner2",2188, null },

				// Test #02: Attempt to unregister in a event not registered . Expected false.
				{"diner1",2185, IllegalArgumentException.class },
				
				// Test #03: Attempt to unregister in a event that he has organized . Expected false.
				{"diner1",2188, IllegalArgumentException.class },

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateUnregisterToEvent((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	

}