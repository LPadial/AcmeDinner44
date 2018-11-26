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
		private VoteService voteService;
		
		@Autowired
		private SoireeService soireeService;
	
	/*
	 * 11.5: When an event in which he or she has registered is over, he or she can cast a vote regarding the corresponding soirees.  An event is over when it has exactly four soirees and their moments have elapsed. 
	 */
	public void templateVoteSoireesOfEvent(final String username, final Integer soireeid, final Integer points, final ArrayList<String> comments, final ArrayList<String> pictures, final Class<?> expected) {
		Class<?> caught = null;

		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.set(GregorianCalendar.YEAR, 2017);
		    gc.set(GregorianCalendar.MONTH, 11);
		    gc.set(GregorianCalendar.DATE, 1);
		    
			this.authenticate(username);
			Soiree soiree = soireeService.findOne(soireeid);
			Event event = soiree.getEvent();
			for(Soiree s: event.getSoirees()){
				s.setDate(gc.getTime());
				soireeService.save(s);
			}
			Vote vote = voteService.create(soiree);
			vote.setPoints(points);
			vote.setComments(comments);
			vote.setPictures(pictures);
			
			
			voteService.save(vote);
			voteService.flush();
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverVoteSoiree() {
		
		ArrayList<String> comments = new ArrayList<String>();
		comments.add("Hola");
		comments.add("He estado muy bien");
		
		ArrayList<String> pictures = new ArrayList<String>();
		pictures.add("https://i.pinimg.com/564x/d8/78/c0/d878c0bfe7b04c595279f824645fcda4.jpg");
		
		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{"diner1", 1990, 3, comments, pictures, null },
				
				// Test #02: Attempt to voter in a soiree from a event not over. Expected false.
				{"diner1", 1995, 3, comments, pictures, NullPointerException.class },
				
				// Test #03: Soiree of a event with negative points. Expected false.
				{"diner2", 1990, -1, comments, pictures, NullPointerException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateVoteSoireesOfEvent((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (ArrayList<String>) testingData[i][3],(ArrayList<String>) testingData[i][4],(Class<?>) testingData[i][5]);
	}
	
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
				{"diner2",1994,gc.getTime(),"address1",pictures, NullPointerException.class },
				
				// Test #03: Attempt to create a soiree with past date. Expected false.
				{"diner1",1994,pastDate.getTime(),"address1",pictures, NullPointerException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSoiree((String) testingData[i][0], (Integer) testingData[i][1],(Date) testingData[i][2], (String) testingData[i][3], (ArrayList<String>) testingData[i][4], (Class<?>) testingData[i][5]);
	}
}
