package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
		Item item = new Item();	
		
		item.setSKU(new String());
		item.setName(new String());
		item.setPhoto(new String());
		item.setPrice(new Double(0.1));
		item.setVAT(new Double(10.0));
		item.setRetailed(new Boolean(true));
		
		Supermarket sm = (Supermarket) loginService.findActorByUsername(LoginService.getPrincipal().getId());		
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
		Assert.notNull(item);
		Item aca = null;

		if (exists(item.getId())) {
			aca = findOne(item.getId());
			item.setRetailed(aca.getRetailed());

			aca = itemRepository.save(aca);
		} else {

			aca = itemRepository.save(item);
		}
		return aca;
	}

	public boolean exists(Integer itemID) {
		return itemRepository.exists(itemID);
	}


	// Other business methods -------------------------------------------------

}
