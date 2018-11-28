package usecases;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Diner;
import domain.Dish;
import domain.DishType;
import domain.Event;
import domain.Soiree;

import security.LoginService;
import services.DinerService;
import services.DishService;
import services.DishTypeService;
import services.EventService;
import services.SoireeService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DishUseCaseTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private EventService eventService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private DinerService dinerService;

	@Autowired
	private SoireeService soireeService;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private DishTypeService dishTypeService;

	/*
	 * 11.4: Manage one soiree in an event to which he or she has registered,
	 * which involves listing, showing, creating, updating, and deleting them
	 * and their corresponding dishes. Note that a diner who has registered for
	 * an event can register a maximum of one soiree in that event.
	 */
	/*
	 * 11.4.1: Create dish
	 */
	public void templateCreateDish(final String username,
			final Integer soireeid, final String name, final String description,
			final ArrayList<String> ingredients, final Integer orderServer, final DishType dishType, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Soiree soiree = soireeService.findOne(soireeid);
			Dish dish = dishService.create(soiree);
			dish.setName(name);
			dish.setDescription(description);
			dish.setIngredients(ingredients);
			dish.setOrderServed(orderServer);
			dish.setDishType(dishType);

			dishService.save(dish);
			dishService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateDish() {

		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add("tomate");
		ingredients.add("ajo");
		DishType dishType = dishTypeService.findAll().iterator().next();

		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{ "diner1", 2184, "Cocido", "Muy bueno", ingredients,1,dishType, null },

				// Test #02: Attempt to create a dish in a soiree without name. Expected false.
				{ "diner1", 2184, "", "Muy bueno", ingredients,1,dishType, ConstraintViolationException.class },

				// Test #03: Attempt to create a dish without ingredients. Expected false.
				{ "diner1", 2184, "Cocido", "Muy bueno", null,1,dishType, IllegalArgumentException.class },

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateDish((String) testingData[i][0],
					(Integer) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3],
					(ArrayList<String>) testingData[i][4],
					(Integer) testingData[i][5],
					(DishType) testingData[i][6],
					(Class<?>) testingData[i][7]);
	}

	/*
	 * 11.4.2: Update dish
	 */
	public void templateUpdateDish(final String username,
			final Integer dishid, final String name, final String description,
			final ArrayList<String> ingredients, final Integer orderServer, final DishType dishType, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Dish dish = dishService.findOne(dishid);
			dish.setName(name);
			dish.setDescription(description);
			dish.setIngredients(ingredients);
			dish.setOrderServed(orderServer);
			dish.setDishType(dishType);

			dishService.save(dish);
			dishService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverUpdateDish() {

		ArrayList<String> ingredients = new ArrayList<String>();
		ingredients.add("tomate");
		ingredients.add("ajo");
		DishType dishType = dishTypeService.findAll().iterator().next();

		final Object testingData[][] = {
				// Test #01: Correct access. Expected true.
				{ "diner1", 2193, "Cocido", "Muy bueno", ingredients,1,dishType, null },

				// Test #02: Attempt to update a dish in a soiree without name. Expected false.
				{ "diner1", 2193, "", "Muy bueno", ingredients,1,dishType, ConstraintViolationException.class },

				// Test #03: Attempt to update a dish without ingredients. Expected false.
				{ "diner1", 2193, "Cocido", "Muy bueno", null,1,dishType, IllegalArgumentException.class },

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateUpdateDish((String) testingData[i][0],
					(Integer) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3],
					(ArrayList<String>) testingData[i][4],
					(Integer) testingData[i][5],
					(DishType) testingData[i][6],
					(Class<?>) testingData[i][7]);
	}

	/*
	 * 11.4.3: Delete dish
	 */
	public void templateDeleteDish(final String username,final Integer dishid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Dish dish = dishService.findOne(dishid);
			dishService.delete(dish);
			dishService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverDeleteDish() {

		final Object testingData[][] = {

				// Test #01: Correct access. Expected true.
				{ "diner1",2193, null },

				// Test #02: Attempt to delete a dish by a supermarket.
				// Expected false.
				{ "supermarket1",2193, ClassCastException.class },

				// Test #03: Attempt to delete a dish of a user anonymous.
				// Expected false.
				{ null, 2193,IllegalArgumentException.class }

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteDish((String) testingData[i][0],(Integer) testingData[i][1],
					(Class<?>) testingData[i][2]);
	}

}
