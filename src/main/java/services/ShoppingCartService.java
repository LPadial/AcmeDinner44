package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
		shoppingCart.setDeliveryAddress(new String(""));
		shoppingCart.setPriceTotal(new Double(0.0));
		
		
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());		
		shoppingCart.setOwner(d);
		
		shoppingCart.setCreditCard(null);
		
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
			Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());		
			Assert.isTrue(shoppingCart.getOwner() == d, "shoppingcart.error.nomine");
			aca = findOne(shoppingCart.getId());
			aca.setDeliveryAddress(shoppingCart.getDeliveryAddress());
			aca.setCreditCard(shoppingCart.getCreditCard());
			aca.setDateCreation(shoppingCart.getDateCreation());
			aca.setPriceTotal(shoppingCart.getPriceTotal());
			aca.setOwner(shoppingCart.getOwner());		

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
		
		shoppingCart.setPriceTotal(priceOfShoppingCart(shoppingCart.getId()));
		ShoppingCart sc = shoppingCartRepository.save(shoppingCart);
		
		return sc;		
	}
	
	public ShoppingCart removeItem(ShoppingCart shoppingCart,Item item) {
		Assert.notNull(shoppingCart);
		Assert.notNull(item);
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(shoppingCart.getOwner() == d);
		Integer numItems = countItemInShoppingCart(shoppingCart.getId(), item.getId());
		Assert.isTrue(numItems>=1,"item.error.numItems");
		Delivery deliveryToDelete = deliveryService.findDelivery(shoppingCart.getId(), item.getId());
		deliveryService.delete(deliveryToDelete);
		
		shoppingCart.setPriceTotal(priceOfShoppingCart(shoppingCart.getId()));
		ShoppingCart sc = shoppingCartRepository.save(shoppingCart);
		
		return sc;		
	}
	
	public ShoppingCart order(ShoppingCart shoppingCart) {
		Assert.notNull(shoppingCart);
		Assert.isTrue(shoppingCart.getCreditCard() != null);
		Assert.isTrue(shoppingCart.getDeliveryAddress() != null);
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(shoppingCart.getOwner() == d);
		
		shoppingCart.setIsOrdered(true);
		shoppingCart = shoppingCartRepository.save(shoppingCart);
		
		return shoppingCart;		
	}
	
	public void delete(ShoppingCart shoppingCart){
		Assert.notNull(shoppingCart);
		
		List<Delivery> deliveries = shoppingCartRepository.deliveriesOfShoppingCart(shoppingCart.getId());
		
		for(Delivery d: deliveries){
			deliveryService.delete(d);
		}
		
		shoppingCartRepository.delete(shoppingCart);
	}

	

	// Other business methods -------------------------------------------------
	
	public List<Item> listItemsOfShoppingCart(int idShoppingCart){
		return shoppingCartRepository.listItemsOfShoppingCart(idShoppingCart);
	}
	
	public Double priceOfShoppingCart(int idShoppingCart){
		return shoppingCartRepository.priceOfShoppingCart(idShoppingCart);
	}
	
	public Integer countItemInShoppingCart(int idShoppingCart, int idItem){
		return shoppingCartRepository.countItemInShoppingCart(idShoppingCart, idItem);
	}

	public void flush() {
		shoppingCartRepository.flush();
	}

}
