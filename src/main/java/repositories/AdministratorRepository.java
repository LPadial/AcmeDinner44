
package repositories;

import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Entidad;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
	
	//Number of diners in the system
	@Query("select count(d) from Diner d")
	Integer numDiners();
		
	//Average, minimum, maximum score of the diners
	@Query("select avg(d.avgScore), min(d.avgScore), max(d.avgScore) from Diner d")
	Object[] avgMinMaxScore();
	
	//Number of events organised of the diners
	@Query("select count(e) from Event e group by e.organizer order by count(e) desc")
	List<Number> numEventForDinner();
	
	//Diners who have organised more events
	@Query("select e.organizer.actorName, count(e) from Event e group by e.organizer order by count(e) desc")
	List<Array[]> dinersWhoHaveMoreEvents();
	
	//Avg, min, max number of dishes per soiree
	@Query("select avg(s.dishes.size), min(s.dishes.size), max(s.dishes.size) from Soiree s")
	Object[] avgMinMaxNumberOfDishesPerSoiree();
	
	//Ratio of diners who have at least one professional section
	@Query("select count(d)/(select count(d1) from Diner d1) from Diner d join d.businessCard bc where bc.professionalSections.size>=1")
	Integer ratioOfDinersWhoHaveAtLeastOneProfessionalSection();
	
	//Ratio of diners who have at least one social section
	@Query("select count(d)/(select count(d1) from Diner d1) from Diner d join d.businessCard bc where bc.socialSections.size>=1")
	Integer ratioOfDinersWhoHaveAtLeastOneSocialSection();
	
	//PLANTILLA
	//Entidades de administrator
	@Query("select e from Entidad e where e.admin.id=?1")
	List<Entidad> findEntidadesOfAdmin(int idAdmin);

	//El mínimo, media, desviación estándar y máximo de cursos por academia.
	/*@Query("select min(a.courses.size),avg(a.courses.size),stddev(a.courses.size),max(a.courses.size) from Academy a")
	Object[] minAvgSdMaxCoursesPerAcademy();

	//El mínimo, media, desviación estándar y máximo de solicitudes por curso.
	@Query("select min(c.applications.size),avg(c.applications.size),stddev(c.applications.size),max(c.applications.size) from Course c")
	Object[] minAvgSdMaxApplicationsPerCourse();

	//El mínimo, media y máximo de tutoriales por academia.
	@Query("select min(a.tutorials.size),avg(a.tutorials.size),max(a.tutorials.size) from Academy a")
	Object[] minAvgMaxTutorialsPerAcademy();

	//El mínimo, media y máximo de veces que un tutorial se muestra.
	@Query("select min(t.numShows),avg(t.numShows),max(t.numShows) from Tutorial t")
	Object[] minAvgMaxTutorialNumShows();

	//La lista de tutoriales, ordenados en orden descendente de acuerdo al
	//número de veces que se han visto.
	@Query("select t from Tutorial t order by t.numShows desc")
	List<Tutorial> tutorialsOrderByNumShowsDes();

	//La media de chirps por actor.
	@Query("select a.actorName, ((a.chirps.size * 1.0) / (select count(c) from Chirp c)) from Actor a")
	List<Array[]> avgChirpsPerActor();

	//La media de suscripciones por actor.
	@Query("select a.actorName, ((a.follower.size * 1.0) / (select count(c) from Actor c)) from Actor a")
	List<Array[]> avgSubscriptionPerActor();*/
}
