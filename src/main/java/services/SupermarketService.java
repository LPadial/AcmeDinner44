package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SupermarketRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Chirp;
import domain.Supermarket;

@Service
@Transactional
public class SupermarketService {
	// Managed repository -----------------------------------------------------
	@Autowired
	public SupermarketRepository supermarketRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public SupermarketService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Supermarket create() {
		Supermarket supermarket = new Supermarket();
		supermarket.setActorName(new String());
		supermarket.setEmail(new String());
		supermarket.setFollowers(new ArrayList<Actor>());
		supermarket.setSurname(new String());
		supermarket.setChirps(new ArrayList<Chirp>());

		Authority a = new Authority();
		a.setAuthority(Authority.DINER);
		UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		supermarket.setUserAccount(account);

		return supermarket;

	}

	public List<Supermarket> findAll() {
		return supermarketRepository.findAll();
	}

	public Supermarket findOne(Integer dinner) {
		Assert.notNull(dinner);
		return supermarketRepository.findOne(dinner);
	}

	public Supermarket save(Supermarket supermarket) {
		Assert.notNull(supermarket);
		Supermarket aca = null;

		if (exists(supermarket.getId())) {
			aca = findOne(supermarket.getId());

			aca.setActorName(supermarket.getActorName());
			aca.setSurname(supermarket.getSurname());
			aca.setEmail(supermarket.getEmail());
			aca.setChirps(supermarket.getChirps());
			aca.setFollowers(supermarket.getFollowers());

			aca = supermarketRepository.save(aca);
		} else {
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			supermarket.getUserAccount().setPassword(
					encoder.encodePassword(
							supermarket.getUserAccount().getPassword(), null));

			aca = supermarketRepository.save(supermarket);
		}
		return aca;
	}

	public boolean exists(Integer supermarketID) {
		return supermarketRepository.exists(supermarketID);
	}

	// Other business methods -------------------------------------------------

}
