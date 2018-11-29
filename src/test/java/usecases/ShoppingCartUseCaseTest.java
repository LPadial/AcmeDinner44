package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import services.DinerService;
import services.EventService;
import services.ItemService;
import services.ShoppingCartService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Diner;
import domain.Item;
import domain.ShoppingCart;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ShoppingCartUseCaseTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private LoginService loginService;

	@Autowired
	private DinerService dinerService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ItemService itemService;

	// Templates

	/*
	 * 23.1: Manage his or her shopping carts, which includes listing, showing, creating, updating, and deleting them.  Updating a shopping cart means either: ordering it, which means that the supermarkets involved must handle it and serve the corresponding items; adding or removing items from it as long as it is not been ordered.  Note that a shopping cart may include items from different supermarkets and that they cannot be deleted if they have already been ordered.
	 */
	
	/*
	 * 23.1.1: Create shopping cart
	 */
	public void templateCreateShoppingCart(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			ShoppingCart shoppingCart = shoppingCartService.create(); 
			shoppingCartService.save(shoppingCart);
			shoppingCartService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateShoppingCart() {

		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{ "diner1", null },

				// Test #02: Attempt to create a shopping cart by a supermarket.
				// Expected false.
				{ "supermarket1", ClassCastException.class },

				// Test #03: Attempt to create a shopping cart by a anonymous.
				// Expected false.
				{ null, IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateShoppingCart((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/*
	 * 23.1.2: Show and update shopping cart
	 */
	public void templateEditShoppingCart(final String username, final Integer shoppingCartid, final String address, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Diner diner = (Diner) loginService.findActorByUsername(username);
			
			//Integer shoppingCartid = super.getEntityId(shoppingCart);			
			ShoppingCart shoppingCartEntity = shoppingCartService.findOne(shoppingCartid);
			
			shoppingCartEntity.setDeliveryAddress(address);
			
			CreditCard creditCard = dinerService.findCreditCardsOfDiner(diner.getId()).iterator().next();
			shoppingCartEntity.setCreditCard(creditCard);
			
			shoppingCartService.save(shoppingCartEntity);

			//shoppingCartService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditShoppingCart() {

		final Object testingData[][] = {

				// Test #01: Correct access. Expected true.
				{ "diner3", 2169, "address1", null },

				// Test #02: Attempt to a diner of edit a shopping cart that is not his
				{ "diner1", 2169, "address1", IllegalArgumentException.class },

				// Test #03: Attempt to a supermarket of edit a shopping cart
				{ "supermarket1", 2169, "address1", ClassCastException.class },

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditShoppingCart((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2],(Class<?>) testingData[i][3]);
	}

	/*
	 * 23.1.3: List shopping cart of a diner and delete shopping cart
	 */
	public void templateDeleteShoppingCart(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Diner diner = (Diner) loginService.findActorByUsername(username);
			ShoppingCart shoppingCart = dinerService.findShoppingCartsOfDiner(diner.getId()).iterator().next();
			shoppingCartService.delete(shoppingCart);
			shoppingCartService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeleteShoppingCart() {

		final Object testingData[][] = {

				// Test #01: Correct access. Expected true.
				{ "diner1", null },

				// Test #02: Attempt to delete a event by a supermarket.
				// Expected false.
				{ "supermarket1", ClassCastException.class },

				// Test #03: Attempt to delete a event of a user anonymous.
				// Expected false.
				{ null, IllegalArgumentException.class }

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteShoppingCart((String) testingData[i][0],(Class<?>) testingData[i][1]);
	}

	/*
	 * 23.1.4: Add item to shopping cart
	 */
	public void templateAddItem(final String username, final Integer shoppingCartid, final Integer itemid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);			
			
			Integer sizeBefore = shoppingCartService.listItemsOfShoppingCart(shoppingCartid).size();
			
			ShoppingCart shoppingCart = shoppingCartService.findOne(shoppingCartid);
			Item item = itemService.findOne(itemid);
			
			shoppingCartService.addItem(shoppingCart, item);
			
			Integer sizeAfter = shoppingCartService.listItemsOfShoppingCart(shoppingCartid).size();
			Assert.isTrue(sizeBefore<sizeAfter);
			
			shoppingCartService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverAddItem() {

		final Object testingData[][] = {

		// Test #01: Correct access. Expected true.
		{ "diner3", 2169, 2125, null },
		
		// Test #02: Attempt to add item in ordered shopping cart. Expected false.
		{ "diner1", 2126, 2124, IllegalArgumentException.class },
		
		// Test #03: Attempt to add item in a shopping cart by supermarket. Expected false.
		{ "supermarket1", 2169, 2124, ClassCastException.class },

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateAddItem((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	/*
	 * 23.1.5: Add item to shopping cart
	 */
	public void templateRemoveItem(final String username, final Integer shoppingCartid, final Integer itemid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Integer sizeBefore = shoppingCartService.listItemsOfShoppingCart(shoppingCartid).size();
			
			ShoppingCart shoppingCart = shoppingCartService.findOne(shoppingCartid);
			Item item = itemService.findOne(itemid);
			
			shoppingCartService.removeItem(shoppingCart, item);
			
			Integer sizeAfter = shoppingCartService.listItemsOfShoppingCart(shoppingCartid).size();
			Assert.isTrue(sizeBefore>sizeAfter);
			
			shoppingCartService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverRemoveItem() {

		final Object testingData[][] = {

		// Test #01: Correct access. Expected true.
		{ "diner3", 2169, 2124, null },
		
		// Test #02: Attempt to delete item not ordered. Expected false.
		{ "diner3", 2169, 2127, IllegalArgumentException.class },
		
		// Test #03: Attempt to delete item in a shopping cart by other diner. Expected false.
		{ "diner1", 2169, 2124, IllegalArgumentException.class },

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveItem((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

}
