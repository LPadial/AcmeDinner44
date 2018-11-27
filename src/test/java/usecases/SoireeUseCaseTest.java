package usecases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import services.DinerService;
import services.EventService;
import services.SoireeService;
import services.VoteService;
import utilities.AbstractTest;

import domain.Diner;
import domain.Event;
import domain.Soiree;
import domain.Vote;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SoireeUseCaseTest extends AbstractTest{
	
	// System under test ------------------------------------------------------

		@Autowired
		private EventService eventService;
		
		@Autowired
		private LoginService loginService;
		
		@Autowired
		private DinerService dinerService;
		
		@Autowired
		private SoireeService soireeService;
	
	
	
	/*
	 * 11.4: Manage one soiree in an event to which he or she has registered, which involves listing, showing, creating, updating, and deleting them and their corresponding dishes.  Note that a diner who has registered for an event can register a maximum of one soiree in that event.
	 */
	/*
	 * 11.4.1: Create soiree
	 */
	public void templateCreateSoiree(final String username, final Integer eventid, final Date date, final String address, final ArrayList<String> pictures, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Event event = eventService.findOne(eventid);
			Soiree soiree = soireeService.create(event);
			soiree.setDate(date);
			soiree.setAddress(address);
			soiree.setPictures(pictures);

			soireeService.save(soiree);
			soireeService.flush();
			
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateSoiree() {
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(GregorianCalendar.YEAR, 2019);
	    gc.set(GregorianCalendar.MONTH, 12);
	    gc.set(GregorianCalendar.DATE, 1);
	    
	    GregorianCalendar pastDate = new GregorianCalendar();
	    pastDate.set(GregorianCalendar.YEAR, 2017);
	    pastDate.set(GregorianCalendar.MONTH, 12);
	    pastDate.set(GregorianCalendar.DATE, 1);
	    
	    ArrayList<String> pictures = new ArrayList<String>();
	    pictures.add("https://i.pinimg.com/564x/d8/78/c0/d878c0bfe7b04c595279f824645fcda4.jpg");
		
		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{"diner1",1994,gc.getTime(),"address1",pictures, null },

				// Test #02: Attempt to create a soiree in a event that the diner is not registered. Expected false.
				{"diner3",1994,gc.getTime(),"address1",pictures, IllegalArgumentException.class },
				
				// Test #03: Attempt to create a soiree with past date. Expected false.
				{"diner1",1994,pastDate.getTime(),"address1",pictures, IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSoiree((String) testingData[i][0], (Integer) testingData[i][1],(Date) testingData[i][2], (String) testingData[i][3], (ArrayList<String>) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	
	/*
	 * 11.4.2: Update soiree
	 */
	public void templateUpdateSoiree(final String username, final Integer soireeid, final Date date, final String address, final ArrayList<String> pictures, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Soiree soiree = soireeService.findOne(soireeid);
			soiree.setDate(date);
			soiree.setAddress(address);
			soiree.setPictures(pictures);

			soireeService.save(soiree);
			soireeService.flush();
			
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverUpdateSoiree() {
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(GregorianCalendar.YEAR, 2019);
	    gc.set(GregorianCalendar.MONTH, 12);
	    gc.set(GregorianCalendar.DATE, 1);
	    
	    GregorianCalendar pastDate = new GregorianCalendar();
	    pastDate.set(GregorianCalendar.YEAR, 2017);
	    pastDate.set(GregorianCalendar.MONTH, 12);
	    pastDate.set(GregorianCalendar.DATE, 1);
	    
	    ArrayList<String> pictures = new ArrayList<String>();
	    pictures.add("https://i.pinimg.com/564x/d8/78/c0/d878c0bfe7b04c595279f824645fcda4.jpg");
		
		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{"diner1",1992,gc.getTime(),"address1",pictures, null },

				// Test #02: Attempt to create a soiree in a event that the diner is not registered. Expected false.
				{"supermarket1",1992,gc.getTime(),"address1",pictures, ClassCastException.class },
				
				// Test #03: Attempt to create a soiree with past date. Expected false.
				{"diner1",1992,pastDate.getTime(),"address1",pictures, IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateUpdateSoiree((String) testingData[i][0], (Integer) testingData[i][1],(Date) testingData[i][2], (String) testingData[i][3], (ArrayList<String>) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	
	/*
	 * 11.4.3: Delete soiree
	 */
	public void templateDeleteSoiree(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);			
			Diner diner = (Diner) loginService.findActorByUsername(username);
			Soiree soiree = soireeService.soireesOfDiner(diner.getId()).iterator().next();
			soireeService.delete(soiree);
			soireeService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeleteSoiree() {
		
		final Object testingData[][] = {
				
				// Test #01: Correct access. Expected true.
				{"diner1", null },
				
				// Test #02: Attempt to delete a event by a supermarket. Expected false.
				{"supermarket1", ClassCastException.class },
				
				// Test #03: Attempt to delete a event of a user anonymous. Expected false.
				{null, IllegalArgumentException.class }			

		};
		
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteSoiree((String) testingData[i][0],(Class<?>) testingData[i][1]);
	}
}
