package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Chirp;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {
	// Managed repository -----------------------------------------------------
		@Autowired
		public SponsorRepository sponsorRepository;

		// Supporting services ----------------------------------------------------

		// Constructors -----------------------------------------------------------
		public SponsorService() {
			super();
		}

		// Simple CRUD methods ----------------------------------------------------
		public Sponsor create() {
			Sponsor sponsor = new Sponsor();
			sponsor.setActorName(new String());
			sponsor.setEmail(new String());
			sponsor.setFollowers(new ArrayList<Actor>());
			sponsor.setSurname(new String());
			sponsor.setChirps(new ArrayList<Chirp>());

			Authority a = new Authority();
			a.setAuthority(Authority.DINER);
			UserAccount account = new UserAccount();
			account.setAuthorities(Arrays.asList(a));
			sponsor.setUserAccount(account);

			return sponsor;

		}

		public List<Sponsor> findAll() {
			return sponsorRepository.findAll();
		}

		public Sponsor findOne(Integer dinner) {
			Assert.notNull(dinner);
			return sponsorRepository.findOne(dinner);
		}

		public Sponsor save(Sponsor sponsor) {
			Assert.notNull(sponsor);
			Sponsor aca = null;

			if (exists(sponsor.getId())) {
				aca = findOne(sponsor.getId());

				aca.setActorName(sponsor.getActorName());
				aca.setSurname(sponsor.getSurname());
				aca.setEmail(sponsor.getEmail());
				aca.setChirps(sponsor.getChirps());
				aca.setFollowers(sponsor.getFollowers());

				aca = sponsorRepository.save(aca);
			} else {
				Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				sponsor.getUserAccount().setPassword(
						encoder.encodePassword(
								sponsor.getUserAccount().getPassword(), null));

				aca = sponsorRepository.save(sponsor);
			}
			return aca;
		}

		public boolean exists(Integer sponsorID) {
			return sponsorRepository.exists(sponsorID);
		}

		// Other business methods -------------------------------------------------

}
