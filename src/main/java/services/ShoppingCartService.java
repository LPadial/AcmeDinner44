package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Delivery;
import domain.Diner;
import domain.Item;
import domain.ShoppingCart;

import repositories.ShoppingCartRepository;
import security.LoginService;

@Service
@Transactional
public class ShoppingCartService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private DeliveryService deliveryService;

	// Constructors -----------------------------------------------------------
	public ShoppingCartService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public ShoppingCart create() {
		ShoppingCart shoppingCart = new ShoppingCart();	
		
		shoppingCart.setDateCreation(new Date());
		shoppingCart.setIsOrdered(new Boolean(false));
		shoppingCart.setDeliveryAddress(new String());
		shoppingCart.setPriceTotal(new Double(0.0));
		
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());		
		shoppingCart.setOwner(d);
		
		shoppingCart.setCreditCard(new CreditCard());
		
		return shoppingCart;
	}
	
	public List<ShoppingCart> findAll() {
		return shoppingCartRepository.findAll();
	}

	public ShoppingCart findOne(Integer shoppingCart) {
		Assert.notNull(shoppingCart);
		return shoppingCartRepository.findOne(shoppingCart);
	}

	public ShoppingCart save(ShoppingCart shoppingCart) {
		Assert.notNull(shoppingCart);
		ShoppingCart aca = null;

		if (exists(shoppingCart.getId())) {
			aca = findOne(shoppingCart.getId());
			shoppingCart.setIsOrdered(aca.getIsOrdered());

			aca = shoppingCartRepository.save(aca);
		} else {

			aca = shoppingCartRepository.save(shoppingCart);
		}
		return aca;
	}

	public boolean exists(Integer shoppingCartID) {
		return shoppingCartRepository.exists(shoppingCartID);
	}

	public ShoppingCart addItem(ShoppingCart shoppingCart,Item item) {
		Assert.notNull(shoppingCart);
		Assert.notNull(item);
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(shoppingCart.getOwner() == d);
		
		Delivery delivery = deliveryService.create(shoppingCart,item);
		deliveryService.save(delivery);
		
		return shoppingCart;		
	}
	
	public ShoppingCart removeItem(ShoppingCart shoppingCart,Item item) {
		Assert.notNull(shoppingCart);
		Assert.notNull(item);
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(shoppingCart.getOwner() == d);
		
		Delivery deliveryToDelete = deliveryService.findDelivery(shoppingCart.getId(), item.getId());
		deliveryService.delete(deliveryToDelete);
		
		return shoppingCart;		
	}

	

	// Other business methods -------------------------------------------------

}
