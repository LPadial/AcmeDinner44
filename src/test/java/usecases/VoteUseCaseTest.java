package usecases;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.SoireeService;
import services.VoteService;
import utilities.AbstractTest;

import domain.Soiree;
import domain.Vote;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class VoteUseCaseTest extends AbstractTest{
	
	// System under test ------------------------------------------------------
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
				{"diner1", 2184, 3, comments, pictures, null },
				
				// Test #02: Attempt to voter in a soiree from a event not over. Expected false.
				{"diner1", 2186, 3, comments, pictures, IllegalArgumentException.class },
				
				// Test #03: Soiree of a event with negative points. Expected false.
				{"diner2", 2184, -1, comments, pictures, ConstraintViolationException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateVoteSoireesOfEvent((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (ArrayList<String>) testingData[i][3],(ArrayList<String>) testingData[i][4],(Class<?>) testingData[i][5]);
	}

}
