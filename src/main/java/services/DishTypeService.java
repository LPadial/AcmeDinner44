package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.DishRepository;
import repositories.DishTypeRepository;

import domain.Dish;
import domain.DishType;

@Service
@Transactional
public class DishTypeService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private DishTypeRepository dishTypeRepository;

	// Supporting services ----------------------------------------------------
	public DishTypeService(){
		super();
	}

	// Constructors -----------------------------------------------------------

	// Simple CRUD methods ----------------------------------------------------
	public List<DishType> findAll() {
		return dishTypeRepository.findAll();
	}

	// Other business methods -------------------------------------------------

}
