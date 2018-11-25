package usecases;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import domain.BusinessCard;
import domain.Chirp;
import domain.Event;
import domain.Finder;
import domain.Supermarket;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.EventService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorUseCaseTest extends AbstractTest {
	
	// System under test ------------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private LoginService loginService;
	
	//10.2: Edit his or her user account data.
	protected void templateEditUserData(final String username, final String actorname, final String surname, final String email, final String usernameAccount, final String passwordAccount, final String brand, final Class<?> expected) {
		Class<?> caught = null;

		try {
			authenticate(username);
			Actor a = loginService.findActorByUsername(username);
			if(LoginService.hasRole("SUPERMARKET")){
				Supermarket supermarket = (Supermarket) a;
				supermarket.setBrand(brand);
			}
			a.setActorName(actorname);
			a.setSurname(surname);
			a.setEmail(email);
			a.getUserAccount().setUsername(usernameAccount);
			a.getUserAccount().setPassword(passwordAccount);
			

			actorService.save(a);
			actorService.flush();

			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
	}
	
	@Test
	public void displayDriver() {

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"diner1", "Paca", "Padial", "padial@padial.es","Padial","Padial",null,null},
				
			//Test #02: Correct access. Expected true.
			{"supermarket1", "Paca11", "Padial11", "padial11@padial.es","Padial11","Padial11","BRand41",null},
				
			//Test #03: Attempt to edit his data without authenticated. Expected false.
			{"", "Paca", "Padial", "padial@padial.es","Padial","Padial",null,IllegalArgumentException.class},
			
			//Test #04: Attempt to edit his data without data. Expected false.
			{"diner1", "", "", "","","",null,IllegalArgumentException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditUserData((String)testingData[i][0], (String)testingData[i][1], (String)testingData[i][2], (String)testingData[i][3], (String)testingData[i][4], (String)testingData[i][5], (String)testingData[i][6],(Class<?>) testingData[i][7]);
	}
	
}