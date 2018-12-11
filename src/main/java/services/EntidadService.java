package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Diner;
import domain.Entidad;

import repositories.EntidadRepository;
import security.LoginService;

@Service
@Transactional
public class EntidadService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EntidadRepository	entidadRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private LoginService			loginService;


	// Constructors -----------------------------------------------------------

	public EntidadService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Entidad create(Diner diner) {
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		
		Entidad entidad = new Entidad();	
		
		entidad.setTicker(createTicker());
		entidad.setTitle(new String());
		entidad.setDescription(new String());
		entidad.setGauge(new Integer(1));
		entidad.setMoment(new Date());
		entidad.setIsSaveFinalMode(new Boolean(false));
		entidad.setAdmin(admin);
		entidad.setDiner(diner);
		
		return entidad;
	}

	public Collection<Entidad> findAll() {
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		Collection<Entidad> result;

		result = this.entidadRepository.findAll();

		return result;
	}

	public Entidad findOne(int entidadId) {
		Entidad result;

		result = this.entidadRepository.findOne(entidadId);

		return result;
	}
	
	public boolean exists(Integer entidadID) {
		return entidadRepository.exists(entidadID);
	}	
	
	public Entidad save(Entidad entidad) {
		Assert.notNull(entidad);
		Administrator admin = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.isTrue(admin instanceof Administrator);
		System.out.println(entidad);
		Entidad aca = null;
		
		if(exists(entidad.getId())){
			
			aca = findOne(entidad.getId());
			
			aca.setDescription(entidad.getDescription());
			aca.setGauge(entidad.getGauge());
			aca.setTitle(entidad.getTitle());
			aca.setMoment(entidad.getMoment());
			aca.setAdmin(entidad.getAdmin());
			aca.setDiner(entidad.getDiner());
			aca.setTicker(entidad.getTicker());
			
			aca = entidadRepository.save(aca);
		} else {
			
			aca = entidadRepository.save(entidad);
		}
		return aca;
	}
	
	public Entidad isSaveFinalMode(Entidad e){
		Assert.notNull(e);
		Assert.isTrue(e.getIsSaveFinalMode() == false);
		Administrator a = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(e.getAdmin() == a);
		
		e.setIsSaveFinalMode(true);
		e = entidadRepository.save(e);
		
		return e;
	}
	
	public void delete(Entidad e){
		Assert.notNull(e);
		Assert.isTrue(e.getIsSaveFinalMode() == false);
		Administrator a = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(e.getAdmin() == a);
		
		entidadRepository.delete(e);
	}

	
	public String createTicker(){
		StringBuilder str = new StringBuilder();
		
		final String dictionary = new String("ABCDEFGHIJKLOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
		
		Random rand = new Random();		
		for(int i = 0; i < 5; i++) {
			str.append(dictionary.charAt(rand.nextInt(dictionary.length()))); 
		}
		
		str.append("-");
		
		final String numbers = new String("1234567890");
		
		for(int i = 0; i < 5; i++) {
			str.append(numbers.charAt(rand.nextInt(numbers.length()))); 
		}
		
		return str.toString();
	}

	// Other business methods -------------------------------------------------
	
	public Integer ratioAdminwith1oMoreEntidades() {
		return entidadRepository.ratioAdminwith1oMoreEntidades();
	}
	
	public Object[] adminwhoHaveCreatedMoreEntities() {
		return entidadRepository.adminwhoHaveCreatedMoreEntities();
	}

}

