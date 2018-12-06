package services;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Brand;
import domain.CreditCard;
import domain.Diner;

import repositories.CreditCardRepository;
import security.LoginService;

@Service
@Transactional
public class CreditCardService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private CreditCardRepository creditCardRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private LoginService loginService;
	

	// Constructors -----------------------------------------------------------
	public CreditCardService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public CreditCard create() {
		CreditCard creditCard= new CreditCard();
		
		Brand brand= new Brand();
		brand.setValue("VISA");
		creditCard.setBrandName(brand);
		creditCard.setCVV(new Integer(100));
		creditCard.setExpirationMonth(new Integer(1));
		creditCard.setExpirationYear(new Integer(2020));
		creditCard.setHolderName(new String());
		creditCard.setNumber(new BigInteger("0"));
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		creditCard.setOwner(d);
		
		return creditCard;
	}
	
	public void flush() {
		creditCardRepository.flush();
	}

	public List<CreditCard> findAll() {
		return creditCardRepository.findAll();
	}

	public CreditCard findOne(Integer creditCard) {
		Assert.notNull(creditCard);		
		return creditCardRepository.findOne(creditCard);
	}

	public CreditCard save(CreditCard creditCard) {
		Assert.notNull(creditCard);	
		
		CreditCard cc = creditCardRepository.save(creditCard);
		
		return cc;
	}



	// Other business methods -------------------------------------------------

}
