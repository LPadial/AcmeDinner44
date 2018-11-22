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
import domain.Finder;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {
	// Managed repository -----------------------------------------------------
		@Autowired
		public SponsorRepository sponsorRepository;

		// Supporting services ----------------------------------------------------
		
		@Autowired
		private FinderService finderService;

		// Constructors -----------------------------------------------------------
		public SponsorService() {
			super();
		}

		// Simple CRUD methods ----------------------------------------------------
		public Sponsor create() {
			Sponsor sponsor = new Sponsor();
			
			Finder finder = finderService.create();
			finder = finderService.save(finder);
			sponsor.setFinder(finder);
			
			sponsor.setActorName(new String());
			sponsor.setSurname(new String());
			sponsor.setEmail(new String());
			sponsor.setFollowers(new ArrayList<Actor>());			
			sponsor.setChirps(new ArrayList<Chirp>());

			Authority a = new Authority();
			a.setAuthority(Authority.SPONSOR);
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
			Assert.notNull(sponsor.getUserAccount().getUsername(),"error.username");
			Assert.notNull(sponsor.getUserAccount().getPassword(),"error.password");
			Assert.isTrue(sponsor.getUserAccount().getUsername().length()>=5 && sponsor.getUserAccount().getUsername().length()<=32, "error.username.length");
			Assert.isTrue(sponsor.getUserAccount().getPassword().length()>=5 && sponsor.getUserAccount().getPassword().length()<=32, "error.password.length");
			Sponsor aca = null;

			if (exists(sponsor.getId())) {
				aca = findOne(sponsor.getId());

				aca.setActorName(sponsor.getActorName());
				aca.setSurname(sponsor.getSurname());
				aca.setEmail(sponsor.getEmail());
				aca.setChirps(sponsor.getChirps());
				aca.setFollowers(sponsor.getFollowers());
				aca.setFinder(sponsor.getFinder());
				aca.getUserAccount().setUsername(sponsor.getUserAccount().getUsername());
				Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				aca.getUserAccount().setPassword(encoder.encodePassword(sponsor.getUserAccount().getPassword(), null));

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
