package usecases;

import java.util.Arrays;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Supermarket;

import security.Authority;
import security.UserAccount;
import services.SupermarketService;
import utilities.AbstractTest;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SupermarketUseCaseTest extends AbstractTest{
	
	//The SUT
	
		@Autowired
		private SupermarketService supermarketService;


		//Templates

		/*
		 * 21.1: An actor who is not authenticated must be able to register to the system as a supermarket.
		 */
		protected void templateRegister(final String actorname, final String surname, final String email, final UserAccount userAccount, final String brand, final Class<?> expected) {
			Class<?> caught = null;

			try {

				Supermarket supermarket = supermarketService.create();
				supermarket.setActorName(actorname);
				supermarket.setSurname(surname);
				supermarket.setEmail(email);
				supermarket.setUserAccount(userAccount);
				supermarket.setBrand(brand);

				supermarketService.save(supermarket);
				supermarketService.flush();

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
			a.setAuthority(Authority.SUPERMARKET);
			UserAccount userAccount = new UserAccount();
			userAccount.setAuthorities(Arrays.asList(a));
			userAccount.setPassword("supermarket7");
			userAccount.setUsername("supermarket7");

			templateRegister("supermarket7", "supermarket7", "supermarket7@gmail.com", userAccount,"supermarket7", null);

		}
		
		//Test #02: Attempt to register a supermarket with existed brand. Expected false.
		@Test
		public void negativeTest1() {

			Authority a = new Authority();
			a.setAuthority(Authority.SUPERMARKET);
			UserAccount userAccount = new UserAccount();
			userAccount.setAuthorities(Arrays.asList(a));
			userAccount.setPassword("supermarket7");
			userAccount.setUsername("supermarket7");

			templateRegister("supermarket7", "supermarket7", "supermarket7@gmail.com", userAccount,"LACO", DataIntegrityViolationException.class);

		}
		
		//Test #03: Attempt to register a supermarket with email incorrect. Expected false.
		@Test
		public void negativeTest2() {

			Authority a = new Authority();
			a.setAuthority(Authority.SUPERMARKET);
			UserAccount userAccount = new UserAccount();
			userAccount.setAuthorities(Arrays.asList(a));
			userAccount.setPassword("supermarket7");
			userAccount.setUsername("supermarket7");

			templateRegister("supermarket7", "supermarket7", "supermarket7gmail.com", userAccount,"supermarket7", ConstraintViolationException.class);

		}

}
