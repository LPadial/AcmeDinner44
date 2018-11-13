package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.PersonalSection;

import repositories.PersonalSectionRepository;

@Service
@Transactional
public class PersonalSectionService {
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private PersonalSectionRepository personalSectionRepository;

	// Supporting services ----------------------------------------------------
	

	// Constructors -----------------------------------------------------------
	public PersonalSectionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public PersonalSection create() {
		PersonalSection personalSection = new PersonalSection();
		
		personalSection.setTitle(new String("Personal Section"));
		personalSection.setFullName(new String());
		personalSection.setPhoto(new String());
		personalSection.setBirthdate(new Date());
		personalSection.setLikes(new ArrayList<String>());
		personalSection.setDislikes(new ArrayList<String>());
		
		return personalSection;

	}
	
	public List<PersonalSection> findAll() {
		return personalSectionRepository.findAll();
	}

	public PersonalSection findOne(Integer personalSection) {
		Assert.notNull(personalSection);
		return personalSectionRepository.findOne(personalSection);
	}
	
	
	
	public PersonalSection save(PersonalSection personalSection) {
		Assert.notNull(personalSection);
		PersonalSection aca = null;

		if (exists(personalSection.getId())) {
			aca = findOne(personalSection.getId());

			aca.setTitle(personalSection.getTitle());
			aca.setFullName(personalSection.getFullName());
			aca.setPhoto(personalSection.getPhoto());
			aca.setBirthdate(personalSection.getBirthdate());
			aca.setLikes(personalSection.getLikes());
			aca.setDislikes(personalSection.getDislikes());

			aca = personalSectionRepository.save(aca);
		} else {
			aca = personalSectionRepository.save(personalSection);
		}
		return aca;
	}

	public boolean exists(Integer personalSectionID) {
		return personalSectionRepository.exists(personalSectionID);
	}


	// Other business methods -------------------------------------------------

}
