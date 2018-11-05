package services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Finder;


import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private FinderRepository finderRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	
	public FinderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Finder create() {
		Finder finder = new Finder();
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
	    
	    finder.setKeyword(new String());
	    finder.setCity(new String());		
		finder.setMaxResults(new Integer(10));
		finder.setStorageCache(cal.getTime());

		return finder;

	}

	public List<Finder> findAll() {
		return finderRepository.findAll();
	}

	public Finder findOne(Integer finder) {
		Assert.notNull(finder);
		return finderRepository.findOne(finder);
	}

	public Finder save(Finder finder) {
		Assert.notNull(finder);
		Finder aca = null;

		if (exists(finder.getId())) {
			aca = findOne(finder.getId());

			aca.setKeyword(finder.getKeyword());
			aca.setCity(finder.getCity());
			aca.setMaxResults(finder.getMaxResults());
			aca.setStorageCache(finder.getStorageCache());

			aca = finderRepository.save(aca);
		} else {
			aca = finderRepository.save(finder);
		}
		return aca;
	}

	public boolean exists(Integer finderID) {
		return finderRepository.exists(finderID);
	}


	// Other business methods -------------------------------------------------

}
