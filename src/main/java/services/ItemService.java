package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Delivery;
import domain.Item;
import domain.Supermarket;

import repositories.ItemRepository;
import security.LoginService;

@Service
@Transactional
public class ItemService {
	// Managed repository -----------------------------------------------------
	@Autowired
	private ItemRepository itemRepository;

	// Supporting services ----------------------------------------------------	
	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------
	public ItemService(){
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Item create() {
		Supermarket sm = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());	
		Assert.isTrue(sm instanceof Supermarket);
		Item item = new Item();	
		
		item.setSKU(new String());
		item.setName(new String());
		item.setPhoto(new String());
		item.setPrice(new Double(0.1));
		item.setVAT(new Double(10.0));
		item.setRetailed(new Boolean(true));
				
		item.setSupermarket(sm);

		return item;
	}
	
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	public Item findOne(Integer item) {
		Assert.notNull(item);
		return itemRepository.findOne(item);
	}

	public Item save(Item item) {
		Supermarket sm = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());	
		Assert.isTrue(sm instanceof Supermarket);
		Assert.notNull(item);
		Assert.isTrue(item.getPrice()!=null);
		Assert.isTrue(item.getVAT()!=null);
		Assert.isTrue(item.getPrice()>=0);
		Assert.isTrue(item.getVAT()>=0);
		Item aca = null;

		if (!exists(item.getId())) {
			aca = itemRepository.save(item);
		}
		return aca;
	}

	public boolean exists(Integer itemID) {
		return itemRepository.exists(itemID);
	}

	public Item changeToRetailed(Item item) {
		Supermarket sm = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());	
		Assert.isTrue(sm instanceof Supermarket);
		Assert.notNull(item);
		Assert.isTrue(item.getRetailed()==false);
		item.setRetailed(true);
		item = itemRepository.save(item);
		return item;		
	}
	
	public Item changeToNotRetailed(Item item) {
		Supermarket sm = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());	
		Assert.isTrue(sm instanceof Supermarket);
		Assert.notNull(item);
		Assert.isTrue(item.getRetailed()==true);
		item.setRetailed(false);
		item = itemRepository.save(item);
		return item;		
	}


	// Other business methods -------------------------------------------------
	public List<Object[]> itemsOfSupermarketNotDeliveredGroupByDeliveredAddress(int idSupermarket){
		return itemRepository.itemsOfSupermarketNotDeliveredGroupByDeliveredAddress(idSupermarket);
	}
	
	public List<Object[]> itemsOfSupermarketDeliveredGroupByDeliveredAddress(int idSupermarket){
		return itemRepository.itemsOfSupermarketDeliveredGroupByDeliveredAddress(idSupermarket);
	}
	
	public List<Delivery> itemsNotDeliveredInAddress(String address, int idSupermarket){
		return itemRepository.itemsNotDeliveredInAddress(address, idSupermarket);
	}
	
	public List<Item> itemsRetailed(){
		return itemRepository.itemsRetailed();
	}

	public void flush() {
		itemRepository.flush();
	}

}
