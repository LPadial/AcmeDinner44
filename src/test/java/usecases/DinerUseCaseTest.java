package usecases;

import java.util.Arrays;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Diner;


import security.Authority;
import security.UserAccount;
import services.DinerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DinerUseCaseTest extends AbstractTest {

	//The SUT
	
	@Autowired
	private DinerService dinerService;


	//Templates

	/*
	 * 9.1: An actor who is not authenticated must be able to register to the system as a diner.
	 */
	protected void template(final String actorname, final String surname, final String email, final UserAccount userAccount, final Class<?> expected) {
		Class<?> caught = null;

		try {

			Diner diner = dinerService.create();
			diner.setActorName(actorname);
			diner.setSurname(surname);
			diner.setEmail(email);
			diner.setUserAccount(userAccount);

			dinerService.save(diner);
			dinerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}
	
	//Drivers

	//Test #01: All parameters correct. Expected true.
	@Test
	public void positiveTest0() {

		Authority a = new Authority();
		a.setAuthority(Authority.DINER);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));
		userAccount.setPassword("diner7");
		userAccount.setUsername("diner7");

		template("diner7", "diner7", "diner7@gmail.com", userAccount, null);

	}
	
	//Test #02: UserAccount with username and password blank. Expected false.
	@Test
	public void negativeTest1() {

		Authority a = new Authority();
		a.setAuthority(Authority.DINER);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));
		userAccount.setPassword("");
		userAccount.setUsername("");

		template("diner5", "diner5", "diner5@gmail.com", userAccount, IllegalArgumentException.class);

	}
	
	//Test #03: Name, surname null and email incorrect. Expected false.
	@Test
	public void negativeTest2() {

		Authority a = new Authority();
		a.setAuthority(Authority.DINER);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));
		userAccount.setPassword("diner5");
		userAccount.setUsername("diner5");

		template("", "", "diner5", userAccount, ConstraintViolationException.class);

	}
	
	/*
	 * 9.3: Search for diners using a single keyword that must appear in their names, surnames, or any of the sections of their business cards.  If a user can show information about a diner, then he or she must be able navigate to his or her business card as well as to the events that he or she has organised.
	 */
	public void templateDinerSearch(final String keyword, final Class<?> expected) {
		Class<?> caught = null;

		try {
			dinerService.findDinersByKeyWord(keyword);			
		} catch (final Throwable oops) {			
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void driverDinerSearch() {

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"diner", null},
				
			//Test #02: Attempt to search without keyword. Expected false.
			{null, IllegalArgumentException.class},
				
			//Test #03: Attempt to search with empty keyword. Expected false.
			{"", IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDinerSearch((String)testingData[i][0], (Class<?>) testingData[i][1]);
	}
	
	/*
	 * 10.3: 3.	List the diners of the system and display their names, surnames, business cards, and events that they have organised or they are going to organise.
	 */
	public void templateListDiner(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			dinerService.findAll();	
			this.unauthenticate();
		} catch (final Throwable oops) {			
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void driverListDiner() {

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"supermarket1", null}				

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListDiner((String)testingData[i][0], (Class<?>) testingData[i][1]);
	}
}
