package usecases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Chirp;
import domain.Finder;


import security.Authority;
import security.UserAccount;
import services.AdministratorService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorUseCaseTest extends AbstractTest {

	//The SUT
	
	@Autowired
	private AdministratorService administratorService;


	//Templates

	/*
	 * 12.1: An actor who is not authenticated as an administrator must be able to register a new administrator to the system.
	 */
	protected void template(final String username, final String actorname, final String surname, final String email, final UserAccount userAccount, final List<Actor> followers, final List<Chirp> chirps,
		final Finder finder, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Administrator administrator = administratorService.findAll().iterator().next();
			administrator.setActorName(actorname);
			administrator.setSurname(surname);
			administrator.setEmail(email);
			administrator.setUserAccount(userAccount);
			administrator.setFinder(finder);
			administrator.setChirps(chirps);
			administrator.setFollowers(followers);

			administratorService.save(administrator);
			administratorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}
	
	/*
	 * 11.2: An authenticated actor can edit his or her personal data.
	 */
	public void editTemplate(final String username, final String actorName, final String surname, final String email, final UserAccount userAccount, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
				
			Assert.notNull(username);
			Assert.notNull(surname);
			Administrator a = administratorService.findAll().iterator().next();
			a.setActorName(actorName);
			a.setSurname(surname);
			a.setEmail(email);
			a.setUserAccount(userAccount);
			administratorService.save(a);

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Driver
	
	//Test #01: All parameters correct. Expected true.
	@Test
	public void positiveTest0() {

		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));

		template("administrator2", "admin", "admins", "admin@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), new Finder(), null);

	}

	//Test #02: All parameters correct. Expected true.
	@Test
	public void positiveTest1() {

		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));

		template("admin1", "admin", "admins", "admin@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), new Finder(), null);

	}

	//Test #03: All parameters correct. Expected true.
	@Test
	public void positiveTest2() {

		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));

		template("admin1", "admin", "admins", "admin@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), new Finder(), null);

	}

	//Test #04: Incorrect username. Expected false.
	@Test
	public void negativeTest0() {
		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));
		template("admin1", "admin", "admins", "admin@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), new Finder(), null);
		template("diner1", "admin", "admins", "admin@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), new Finder(), ConstraintViolationException.class);
	}

	//Test #05: Incorrect mail. Expected false.
	@Test
	public void negativeTest1() {

		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));
		template("admin1", "admin", "admins", "admin@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), new Finder(), null);
		template("candidate3", "candi", "cand3igmail.com", "+34 (88) 9999", "41100", userAccount, new ArrayList<ActivityReport>(), new ArrayList<Folder>(), new ArrayList<Curricula>(), new ArrayList<Application>(), ConstraintViolationException.class);

	}

	//Test #06: Incorrect phone number. Expected false.
	@Test
	public void negativeTest2() {

		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));
		template("admin1", "admin", "admins", "admin@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), new Finder(), null);
		template("candidate3", "candi", "cand3i@gmail.com", "6555", "41100", userAccount, new ArrayList<ActivityReport>(), new ArrayList<Folder>(), new ArrayList<Curricula>(), new ArrayList<Application>(), ConstraintViolationException.class);

	}
	
	@Test
	public void editDriver() {

		final Object testingData[][] = {
				
			
					
			//Test #01: Correct edition. Expected true.
			{"admin1", "Carlos", "surname","carlos@carlos1.es",userAccount, null},
				
			//Test #02: Attempt to access by anonymous user. Expected false.
			{null, "surname", IllegalArgumentException.class},
				
			//Test #03: Attempt to insert null value. Expected false.
			{"candidate1", null, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][2]);
	}
}

