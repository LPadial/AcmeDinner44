package usecases;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Delivery;
import domain.Diner;
import domain.Event;
import domain.Item;
import domain.ShoppingCart;
import domain.Soiree;
import domain.Supermarket;

import security.LoginService;
import services.CreditCardService;
import services.DeliveryService;
import services.DinerService;
import services.EventService;
import services.ItemService;
import services.ShoppingCartService;
import services.SoireeService;
import services.SupermarketService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ItemUseCaseTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ItemService itemService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private DinerService dinerService;
	
	@Autowired
	private SupermarketService supermarketService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private CreditCardService creditCardService;

	/*
	 * 22.1: Manage their catalogue of items, which includes listing, showing, and creating them.  Once an item is registered, the system must not allow to change anything, but the indication on whether it is currently retailed or not.  To create an item, a supermarket should be able to copy its data from an existing item.
	 */
	/*
	 * 22.1.1: Create item
	 */
	public void templateCreateItem(final String username,final String SKU, final String name, final String photo, final Double price, final Double VAT, final Boolean retailed, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Item item = itemService.create();
			item.setSKU(SKU);
			item.setName(name);
			item.setPhoto(photo);
			item.setPrice(price);
			item.setVAT(VAT);
			item.setRetailed(retailed);

			itemService.save(item);
			itemService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateItem() {

		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{ "supermarket1", "Tomate1", "Tomate", "https://i.pinimg.com/236x/a4/53/fe/a453fe994bd6a1eb0a61be0afaa4f363.jpg", 2.0, 10.0, true, null },

				// Test #02: Attempt to create a item with existed SKU in other supermarket to same product. Expected false.
				{ "supermarket1", "TOM-BOL-5", "Tomate bola", "https://i.pinimg.com/236x/a4/53/fe/a453fe994bd6a1eb0a61be0afaa4f363.jpg", 2.0, 10.0, true, DataIntegrityViolationException.class },

				// Test #03: Attempt to create a item with negative price. Expected false.
				{ "supermarket1", "Tomate1", "Tomate", "https://i.pinimg.com/236x/a4/53/fe/a453fe994bd6a1eb0a61be0afaa4f363.jpg", -2.0, 10.0, true, IllegalArgumentException.class },

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateItem((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (Double) testingData[i][4],
					(Double) testingData[i][5], (Boolean) testingData[i][6],
					(Class<?>) testingData[i][7]);
	}

	/*
	 * 22.1.2: Update retailed false item
	 */
	public void templateItemNotRetailed(final String username, final Integer itemid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Item item = itemService.findOne(itemid);
			itemService.changeToNotRetailed(item);
			Assert.isTrue(item.getRetailed()==false);
			itemService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverRetailetNotItem() {

		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{ "supermarket1", 1931, null },

				// Test #02: Attempt to change  a property item by a supermarket. Expected false.
				{ "diner1", 1931,ClassCastException.class },

				// Test #03: Attempt to change  a property item by a anonymous. Expected false.
				{ null, 1931, IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateItemNotRetailed((String) testingData[i][0],(Integer) testingData[i][1],(Class<?>) testingData[i][2]);
	}

	/*
	 * 22.1.3: Update retailed true item
	 */
	public void templateItemRetailed(final String username, final Integer itemid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Item item = itemService.findOne(itemid);
			itemService.changeToRetailed(item);
			Assert.isTrue(item.getRetailed()==true);
			itemService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverRetailedItem() {

		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{ "supermarket2", 1932, null },

				// Test #02: Attempt to change  a property item by a supermarket. Expected false.
				{ "diner1", 1932,ClassCastException.class },

				// Test #03: Attempt to change  a property item by a anonymous. Expected false.
				{ null, 1932, IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateItemRetailed((String) testingData[i][0],(Integer) testingData[i][1],(Class<?>) testingData[i][2]);
	}
	
	/*
	 * 22.2:	List the items that they have to deliver, grouped by delivery address.
	 */
	public void templateListItemGrouped(final String username, final String address,final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Supermarket supermarket = (Supermarket) loginService.findActorByUsername(username);
			
			
			List<Object[]> delivery = itemService.itemsOfSupermarketNotDeliveredGroupByDeliveredAddress(supermarket.getId());
			List<Delivery> deliveries = itemService.itemsNotDeliveredInAddress(address, supermarket.getId());
			for(Delivery d: deliveries){
				Assert.isTrue(d.getDelivered()==false);
				deliveryService.changeToDelivered(d);			
				Assert.isTrue(d.getDelivered()==true);
			}
			List<Object[]> deliveryAfter = itemService.itemsOfSupermarketNotDeliveredGroupByDeliveredAddress(supermarket.getId());
			Assert.isTrue(delivery.size()>deliveryAfter.size());
			
			itemService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverListItemGrouped() {

		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{ "supermarket1", "calle Trucha , 3. Sevilla", null },

				// Test #02: Attempt to list his or her items grouped by address by diner. Expected false.
				{ "diner1", "calle Trucha , 3. Sevilla", ClassCastException.class },

				// Test #03: Attempt to list his or her items grouped by address by anonymous. Expected false.
				{ null,"calle Trucha , 3. Sevilla", IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListItemGrouped((String) testingData[i][0],(String) testingData[i][1],
					(Class<?>) testingData[i][2]);
	}
	
	/*
	 * 22.3: Mark a group of items that must be delivered to a given delivery address as delivered
	 */
	public void templateMarkDelivered(final String username, final String address, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Supermarket s = (Supermarket) loginService.findActorByUsername(username);
			
			Delivery d = itemService.itemsNotDeliveredInAddress(address, s.getId()).iterator().next();
			
			Assert.isTrue(d.getDelivered()==false);
			deliveryService.changeToDelivered(d);			
			Assert.isTrue(d.getDelivered()==true);
			
			itemService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverMarkDelivered() {

		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{ "supermarket1", "calle Trucha , 3. Sevilla", null },

				// Test #02: Attempt to mark items delivered by diner. Expected false.
				{ "diner2", "calle Trucha , 3. Sevilla", ClassCastException.class },

				// Test #03: Attempt to mark items delivered by a supermarket that have not got item in this address. Expected false.
				{ "supermarket3", "calle Trucha , 3. Sevilla", NoSuchElementException.class },

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateMarkDelivered((String) testingData[i][0],(String) testingData[i][1],
					(Class<?>) testingData[i][2]);
	}
	
	
	/*
	 * 22.4:	List the items that they have to deliver, grouped by delivery address.
	 */
	public void templateListItemGroupedDelivered(final String username, final Integer itemid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Diner diner = (Diner) loginService.findActorByUsername(username);			
			
			Integer supermarketid = itemService.findOne(itemid).getSupermarket().getId();
			List<Object[]> deliveredItems = itemService.itemsOfSupermarketDeliveredGroupByDeliveredAddress(supermarketid);
			ShoppingCart shoppingCart = shoppingCartService.create();
			shoppingCart.setDeliveryAddress("nueva address");
			shoppingCart.setDateCreation(new Date());
			shoppingCart.setIsOrdered(false);
			shoppingCart.setOwner(diner);
			shoppingCart.setPriceTotal(1.0);
			shoppingCart.setCreditCard(dinerService.findCreditCardsOfDiner(diner.getId()).iterator().next());
			shoppingCartService.save(shoppingCart);
			Delivery delivery = deliveryService.create(shoppingCart, itemService.findOne(itemid));
			delivery.setDelivered(false);
			deliveryService.save(delivery);
			List<Object[]> deliveredItemsAfter = itemService.itemsOfSupermarketDeliveredGroupByDeliveredAddress(supermarketid);
			Assert.isTrue(deliveredItems.size()<deliveredItemsAfter.size());
			
			itemService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverListItemGroupedDelivered() {

		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{ "diner1", 1933, null },

				// Test #02: Attempt to order in a supermarket that already have a order. Expected false.
				{ "diner1", 1931, ClassCastException.class },

				// Test #03: Attempt to order by anonymous. Expected false.
				{ null,1933, IllegalArgumentException.class }

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListItemGroupedDelivered((String) testingData[i][0],(Integer) testingData[i][1],
					(Class<?>) testingData[i][2]);
	}


}
