package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.EventService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EventUseCaseTest extends AbstractTest {
	
	// System under test ------------------------------------------------------
	
	@Autowired
	private EventService eventService;
	
	
	//Templates
	
	/*
	 * 9.2: Search for events using a single keyword that must appear in its title or its description.  If a user can show an event, then he or she must be able to navigate to the corresponding soirees, dishes, and diners.
	 */
	public void templateEventSearch(final String keyword, final Class<?> expected) {
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
					
			//Test #01: Correct access. Expected true.
			{"event", null},
				
			//Test #02: Attempt to search without keyword. Expected false.
			{null, IllegalArgumentException.class},
				
			//Test #03: Attempt to search with empty keyword. Expected false.
			{"", IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEventSearch((String)testingData[i][0], (Class<?>) testingData[i][1]);
	}
	
}