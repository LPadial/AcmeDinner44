package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DishRepository;


import domain.Dish;

@Service
@Transactional
public class DishService {
	
	// Managed repository -----------------------------------------------------	
	@Autowired
	private DishRepository dishRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public DishService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public void delete(Dish dish) {
		Assert.notNull(dish);		
		dishRepository.delete(dish);
	}


	// Other business methods -------------------------------------------------

}
