package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DeliveryRepository;
import security.LoginService;
import domain.Delivery;
import domain.Diner;
import domain.Item;
import domain.ShoppingCart;
import domain.Supermarket;

@Service
@Transactional
public class DeliveryService {
	// Managed repository -----------------------------------------------------
	@Autowired
	public DeliveryRepository deliveryRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------
	public DeliveryService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Delivery create(ShoppingCart shoppingCart, Item item) {
		Delivery delivery = new Delivery();	
		
		delivery.setShoppingCart(shoppingCart);
		delivery.setItem(item);
		delivery.setDelivered(new Boolean(false));		
		
		return delivery;
	}
	
	public List<Delivery> findAll() {
		return deliveryRepository.findAll();
	}

	public Delivery findOne(Integer delivery) {
		Assert.notNull(delivery);
		return deliveryRepository.findOne(delivery);
	}

	public Delivery save(Delivery delivery) {
		Assert.notNull(delivery);
		Diner d = (Diner) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(delivery.getShoppingCart().getOwner() == d, "Not is your shoppingCart");		
		Delivery aca = null;

		if (exists(delivery.getId())) {
			aca = findOne(delivery.getId());		
			
			delivery.setDelivered(aca.getDelivered());
			aca = deliveryRepository.save(aca);
		} else {

			aca = deliveryRepository.save(delivery);
		}
		return aca;
	}
	
	public Delivery changeToDelivered(Delivery delivery) {
		Assert.notNull(delivery);
		Supermarket s = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(delivery.getItem().getSupermarket() == s);
		delivery.setDelivered(true);
		delivery = deliveryRepository.save(delivery);
		return delivery;		
	}

	public boolean exists(Integer deliveryID) {
		return deliveryRepository.exists(deliveryID);
	}
	
	public void delete(Delivery delivery) {
		Assert.notNull(delivery);
		
		deliveryRepository.delete(delivery);
	}

	// Other business methods -------------------------------------------------
	
	public Delivery findDelivery(int idShoppingCart, int idItem){
		return deliveryRepository.findDelivery(idShoppingCart, idItem).get(0);
	}

}