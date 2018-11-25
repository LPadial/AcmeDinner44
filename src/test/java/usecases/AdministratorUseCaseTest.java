package usecases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import domain.Administrator;
import domain.Chirp;
import domain.Finder;


import security.Authority;
import security.UserAccount;
import services.AdministratorService;
import services.FinderService;
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
	
	@Autowired
	private FinderService finderService;


	//Templates

	/*
	 * 12.1: An actor who is not authenticated as an administrator must be able to register a new administrator to the system.
	 */
	protected void templateCreateNewAdministrator(final String username, final String actorname, final String surname, final String email, final UserAccount userAccount, final List<Actor> followers, final List<Chirp> chirps,
		final Finder finder, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Administrator administrator = administratorService.create();
			administrator.setActorName(actorname);
			administrator.setSurname(surname);
			administrator.setEmail(email);
			administrator.setUserAccount(userAccount);
			administrator.setFinder(finder);
			administrator.setChirps(chirps);
			administrator.setFollowers(followers);

			administratorService.save(administrator);
			administratorService.flush();
			
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);

	}
	
	//Driver
	
	//Test #01: All parameters correct. Expected true.
	@Test
	public void positiveTest0() {

		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));
		userAccount.setUsername("admin25");
		userAccount.setPassword("admin25");
		
		Finder finder = finderService.create();
		Finder finderAdmin= finderService.save(finder);

		templateCreateNewAdministrator("admin1", "admin25", "admin25", "admin25@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), finderAdmin, null);

	}

	//Test #02: Username is repit. Expected false.
	@Test
	public void negativeTest1() {

		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));
		userAccount.setUsername("admin1");
		userAccount.setPassword("admin1");
		
		Finder finder = finderService.create();
		Finder finderAdmin= finderService.save(finder);

		templateCreateNewAdministrator("admin1", "admin", "admins", "admin@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), finderAdmin, DataIntegrityViolationException.class);

	}


	//Test #03: Attempt to create admin a diner. Expected false.
	@Test
	public void negativeTest2() {
		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount userAccount = new UserAccount();
		userAccount.setAuthorities(Arrays.asList(a));
		userAccount.setUsername("admin40");
		userAccount.setPassword("admin40");
		
		Finder finder = finderService.create();
		Finder finderAdmin= finderService.save(finder);
		
		templateCreateNewAdministrator("diner1", "admin", "admins", "admin@gmail.com", userAccount, new ArrayList<Actor>(), new ArrayList<Chirp>(), finderAdmin, ConstraintViolationException.class);
	}
	
	
	/*
	 * 12.3: An administrator can display a dashboard with system information.
	 */
	public void dashboardTemplate(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			administratorService.numDiners();
			administratorService.avgMinMaxScore();
			administratorService.avgNumberOfEventsOrganisedByDiners();
			administratorService.maxNumberOfEventsOrganisedByDiners();
			administratorService.minNumberOfEventsOrganisedByDiners();
			administratorService.dinersWhoHaveMoreEvents();
			administratorService.ratioOfEventsOver();
			administratorService.avgMinMaxNumberOfDishesPerSoiree();
			administratorService.ratioOfDinersWhoHaveAtLeastOneProfessionalSection();
			administratorService.ratioOfDinersWhoHaveAtLeastOneSocialSection();
			administratorService.avgMinMaxChirpsPerActor();
			administratorService.avgMinMaxRequestSponsorshipPerSponsor();
			administratorService.bestSelledItems();
			administratorService.bestBuyingDiners();
			administratorService.bestSellingSupermarkets();

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers

	@Test
	public void dashboardDriver() {

		final Object testingData[][] = {

			//Test #01: Correct access. Expected true.
			{
				"admin1", null
			},

			//Test #02: Attempt to access by anonymous user. Expected false.
			{
				null, IllegalArgumentException.class
			},

			//Test #03: Attempt to access by unauthorized user. Expected false.
			{
				"diner2", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.dashboardTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	
}

