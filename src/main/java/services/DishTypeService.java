package services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DishTypeRepository;

import domain.DishType;

@Service
@Transactional
public class DishTypeService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private DishTypeRepository dishTypeRepository;

	// Supporting services ----------------------------------------------------
	

	// Constructors -----------------------------------------------------------
	public DishTypeService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public DishType create() {
		DishType dishType = new DishType();		
		dishType.setValue(new String());		
		return dishType;
	}
	
	public List<DishType> findAll() {
		return dishTypeRepository.findAll();
	}
	
	public DishType findOne(Integer dishType) {
		Assert.notNull(dishType);
		return dishTypeRepository.findOne(dishType);
	}
	
	public DishType save(DishType dishType) {
		Assert.notNull(dishType);
		DishType aca = null;

		if (exists(dishType.getId())) {
			aca = findOne(dishType.getId());
			
			aca.setValue(dishType.getValue());

			aca = dishTypeRepository.save(aca);
		} else {
			aca = dishTypeRepository.save(dishType);
		}
		return aca;
	}

	public boolean exists(Integer dishTypeID) {
		return dishTypeRepository.exists(dishTypeID);
	}

	// Other business methods -------------------------------------------------

}
