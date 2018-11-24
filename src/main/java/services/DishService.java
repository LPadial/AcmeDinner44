package services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DishRepository;
import security.LoginService;


import domain.Diner;
import domain.Dish;
import domain.DishType;
import domain.Soiree;

@Service
@Transactional
public class DishService {
	
	// Managed repository -----------------------------------------------------	
	@Autowired
	private DishRepository dishRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------
	public DishService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Dish create(Soiree s) {
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		Dish dish = null;
		if(s.getOrganizer()==d){		
			dish = new Dish();
			
			dish.setName(new String());		
			dish.setDescription(new String());
			dish.setIngredients(new ArrayList<String>());
			dish.setOrderServed(new Integer(1));
			dish.setDishType(new DishType());
			dish.setSoiree(s);
			
		}
		return dish;
	}
	
	public List<Dish> findAll() {
		return dishRepository.findAll();
	}

	public Dish findOne(Integer dish) {
		Assert.notNull(dish);
		return dishRepository.findOne(dish);
	}

	public Dish save(Dish dish) {
		Assert.notNull(dish);
		Assert.notNull(dish.getIngredients());
		Assert.notEmpty(dish.getIngredients());
		System.out.println(dish.getDishType());
		Assert.isTrue(dish.getIngredients().size()>=1);
		Dish aca = null;

		if (exists(dish.getId())) {
			aca = findOne(dish.getId());
			
			aca.setName(dish.getName());
			aca.setDescription(dish.getDescription());
			aca.setIngredients(dish.getIngredients());
			aca.setOrderServed(dish.getOrderServed());
			aca.setDishType(dish.getDishType());

			aca = dishRepository.save(aca);
		} else {
			aca = dishRepository.save(dish);
		}
		return aca;
	}

	public boolean exists(Integer dishID) {
		return dishRepository.exists(dishID);
	}
	
	public void delete(Dish dish) {
		Assert.notNull(dish);		
		dishRepository.delete(dish);
	}


	// Other business methods -------------------------------------------------

}
