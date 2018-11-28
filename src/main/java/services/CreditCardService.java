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
		CreditCard card= new CreditCard();
		
		Brand brand= new Brand();
		brand.setValue("VISA");
		card.setBrandName(brand);
		card.setCVV(new Integer(100));
		card.setExpirationMonth(new Integer(1));
		card.setExpirationYear(new Integer(2020));
		card.setHolderName(new String());
		card.setNumber(new BigInteger("0"));
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		card.setOwner(d);
		
		return card;
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
