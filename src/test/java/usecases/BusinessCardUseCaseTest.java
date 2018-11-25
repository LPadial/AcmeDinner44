package usecases;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Diner;
import domain.PersonalSection;
import domain.ProfessionalSection;
import domain.SocialSection;

import security.LoginService;
import services.PersonalSectionService;
import services.ProfessionalSectionService;
import services.SocialSectionService;
import utilities.AbstractTest;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BusinessCardUseCaseTest extends AbstractTest {
	
	// System under test ------------------------------------------------------
	
	@Autowired
	private PersonalSectionService personalSectionService;
	
	@Autowired
	private ProfessionalSectionService professionalSectionService;
	
	@Autowired
	private SocialSectionService socialSectionService;
	
	@Autowired
	private LoginService loginService;
	
	
	//Templates
	
	/*
	 * 11.1:	Manage his or her business card, which involves showing it, modifying the personal section
	 */
	public void templateShowAndEditMyPersonalSection(final String username, final String title, final String fullName, final String photo, final Date birthdate, final ArrayList<String> likes, final ArrayList<String> dislikes, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Diner diner = (Diner) loginService.findActorByUsername(username);
			PersonalSection personalSection = diner.getBusinessCard().getPersonalSection();
			personalSection.setTitle(title);
			personalSection.setFullName(fullName);
			personalSection.setPhoto(photo);
			personalSection.setBirthdate(birthdate);
			personalSection.setLikes(likes);
			personalSection.setDislikes(dislikes);
				
			personalSectionService.save(personalSection);
			personalSectionService.flush();
					
		} catch (final Throwable oops) {			
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverEditPersonalSection() {
		
		ArrayList<String> likes = new ArrayList<String>();
		likes.add("jamón");
		likes.add("tomate");
		
		ArrayList<String> dislikes = new ArrayList<String>();
		dislikes.add("manitas");
		dislikes.add("sesos");
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(GregorianCalendar.YEAR, 1990);
	    gc.set(GregorianCalendar.MONTH, 12);
	    gc.set(GregorianCalendar.DATE, 1);
	

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"diner1","title1","Diner surname","https://i.pinimg.com/564x/d8/78/c0/d878c0bfe7b04c595279f824645fcda4.jpg",gc.getTime(),likes,dislikes, null},
				
			//Test #02: Attempt to modify personal section with a incorrect url in photo. Expected false.
			{"diner1","title1","Diner surname","foto",gc.getTime(),likes,dislikes, ConstraintViolationException.class},
				
			//Test #03: Attempt to modify personal section with title null. Expected false.
			{"diner1","","Diner surname","foto",gc.getTime(),likes,dislikes, ConstraintViolationException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateShowAndEditMyPersonalSection((String)testingData[i][0], (String)testingData[i][1], (String)testingData[i][2], (String)testingData[i][3], (Date) testingData[i][4], (ArrayList<String>)testingData[i][5],(ArrayList<String>)testingData[i][6],(Class<?>) testingData[i][7]);
	}
	
	/*
	 * 11.1:	Manage his or her business card, which involves crating professional sections
	 */
	public void templateCreateProfessionalSection(final String username, final String title, final String company, final String position, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			ProfessionalSection professionalSection = professionalSectionService.create();
			professionalSection.setTitle(title);
			professionalSection.setCompany(company);
			professionalSection.setPosition(position);
				
			professionalSectionService.save(professionalSection);
			professionalSectionService.flush();
					
		} catch (final Throwable oops) {			
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverCreateProfessionalSection() {

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"diner1","title1","company1","position1", null},
				
			//Test #02: Attempt to create professional section a supermarket. Expected false.
			{"supermarket1","title1","company1","position1", ClassCastException.class},
				
			//Test #03: Attempt to create professional section with title null. Expected false.
			{"diner1","","company1","position1", ConstraintViolationException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateProfessionalSection((String)testingData[i][0], (String)testingData[i][1], (String)testingData[i][2], (String)testingData[i][3],(Class<?>) testingData[i][4]);
	}
	
	/*
	 * 11.1:	Manage his or her business card, which involves updating professional sections
	 */
	public void templateEditProfessionalSection(final String username, final String title, final String company, final String position, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Diner diner = (Diner) loginService.findActorByUsername(username);
			ProfessionalSection professionalSection = diner.getBusinessCard().getProfessionalSections().iterator().next();
			professionalSection.setTitle(title);
			professionalSection.setCompany(company);
			professionalSection.setPosition(position);
				
			professionalSectionService.save(professionalSection);
			professionalSectionService.flush();
					
		} catch (final Throwable oops) {			
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverEditProfessionalSection() {

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"diner1","title1","company1","position1", null},
				
			//Test #02: Attempt to modify professional section a supermarket. Expected false.
			{"supermarket1","title1","company1","position1", ClassCastException.class},
				
			//Test #03: Attempt to modify professional section with title null. Expected false.
			{"diner1","","company1","position1", ConstraintViolationException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditProfessionalSection((String)testingData[i][0], (String)testingData[i][1], (String)testingData[i][2], (String)testingData[i][3],(Class<?>) testingData[i][4]);
	}
	
	/*
	 * 11.1:	Manage his or her business card, which involves deleting professional sections
	 */
	public void templateDeleteProfessionalSection(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Diner diner = (Diner) loginService.findActorByUsername(username);
			ProfessionalSection professionalSection = diner.getBusinessCard().getProfessionalSections().iterator().next();
				
			professionalSectionService.delete(professionalSection);
			professionalSectionService.flush();
					
		} catch (final Throwable oops) {			
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverDeleteProfessionalSection() {

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"diner1", null},
				
			//Test #02: Attempt to delete professional section a supermarket. Expected false.
			{"supermarket1", ClassCastException.class},
				
			//Test #03: Attempt to delete professional section anonymous. Expected false.
			{null, IllegalArgumentException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteProfessionalSection((String)testingData[i][0], (Class<?>) testingData[i][1]);
	}
	
	/*
	 * 11.1:	Manage his or her business card, which involves crating social sections
	 */
	public void templateCreateSocialSection(final String username, final String title, final String network, final String nickname, final String link, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			SocialSection socialSection = socialSectionService.create();
			socialSection.setTitle(title);
			socialSection.setNetwork(network);
			socialSection.setNickname(nickname);
			socialSection.setLink(link);
				
			socialSectionService.save(socialSection);
			socialSectionService.flush();
					
		} catch (final Throwable oops) {			
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverCreateSocialSection() {

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"diner1","title1","network1","nickname1","https://miperfil.com", null},
				
			//Test #02: Attempt to create social section with invalid link. Expected false.
			{"diner1","title1","network1","nickname1","link", ConstraintViolationException.class},
				
			//Test #03: Attempt to create social section with title null. Expected false.
			{"diner1","","network1","nickname1","link", ConstraintViolationException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSocialSection((String)testingData[i][0], (String)testingData[i][1], (String)testingData[i][2], (String)testingData[i][3], (String)testingData[i][4],(Class<?>) testingData[i][5]);
	}
	
	/*
	 * 11.1:	Manage his or her business card, which involves updating social sections
	 */
	public void templateEditSocialSection(final String username, final String title, final String network, final String nickname, final String link, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Diner diner = (Diner) loginService.findActorByUsername(username);
			SocialSection socialSection = diner.getBusinessCard().getSocialSections().iterator().next();
			socialSection.setTitle(title);
			socialSection.setNetwork(network);
			socialSection.setNickname(nickname);
			socialSection.setLink(link);
				
			socialSectionService.save(socialSection);
			socialSectionService.flush();
					
		} catch (final Throwable oops) {			
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverEditSocialSection() {

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"diner1","title1","network1","nickname1","https://miperfil.com", null},
				
			//Test #02: Attempt to create social section with invalid link. Expected false.
			{"diner1","title1","network1","nickname1","link", ConstraintViolationException.class},
				
			//Test #03: Attempt to create social section with title null. Expected false.
			{"diner1","","network1","nickname1","link", ConstraintViolationException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditSocialSection((String)testingData[i][0], (String)testingData[i][1], (String)testingData[i][2], (String)testingData[i][3], (String)testingData[i][4],(Class<?>) testingData[i][5]);
	}
	
	/*
	 * 11.1:	Manage his or her business card, which involves deleting social sections
	 */
	public void templateDeleteSocialSection(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			Diner diner = (Diner) loginService.findActorByUsername(username);
			SocialSection socialSection = diner.getBusinessCard().getSocialSections().iterator().next();
				
			socialSectionService.delete(socialSection);
			socialSectionService.flush();
					
		} catch (final Throwable oops) {			
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	
	@Test
	public void driverDeleteSocialSection() {

		final Object testingData[][] = {
					
			//Test #01: Correct access. Expected true.
			{"diner1", null},
				
			//Test #02: Attempt to delete professional section a supermarket. Expected false.
			{"supermarket1", ClassCastException.class},
				
			//Test #03: Attempt to delete professional section anonymous. Expected false.
			{null, IllegalArgumentException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteSocialSection((String)testingData[i][0], (Class<?>) testingData[i][1]);
	}
}