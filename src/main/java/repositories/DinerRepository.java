package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Diner;
import domain.Event;

@Repository
public interface DinerRepository extends JpaRepository<Diner, Integer>{
	
	//Search for diners using a single keyword that must appear in their names, surnames or personalSection
	@Query("select distinct d from Diner d join d.businessCard bc join bc.personalSection pes where d.actorName like %?1% or d.surname like %?1% or pes.title like %?1% or pes.fullName like %?1%")
	Collection<Diner> findDinersByKeywordInNameOrPersonalSection(String keyword);
	
	//Search for diners using a single keyword that must appear in their likes or dislikes
	@Query("select distinct d from Diner d join d.businessCard bc join bc.personalSection pes join pes.dislikes dl join pes.likes l where dl like %?1% or l like %?1%") 
	Collection<Diner> findDinersByKeyWordInLikesDislikes(String keyword);
	
	//Search for diners using a single keyword that must appear in their social sections
	@Query("select distinct d from Diner d join d.businessCard bc join bc.socialSections ss where ss.network like %?1% or ss.nickname like %?1% or ss.title like %?1%") 
	Collection<Diner> findDinersByKeyWordSocialSections(String keyword);
	
	//Search for diners using a single keyword that must appear in their professional sections
	@Query("select distinct d from Diner d join d.businessCard bc join bc.professionalSections prs where prs.title like %?1% or prs.position like %?1%  or prs.company like %?1% ") 
	Collection<Diner> findDinersByKeyWordProfessionalSections(String keyword);
	
	//Search for diners using a single keyword that must appear in their names, surnames or any of the sections of their business cards
	@Query("select distinct d from Diner d join d.businessCard bc join bc.personalSection pes join pes.dislikes dl join pes.likes l join bc.socialSections ss join bc.professionalSections prs where d.actorName like %?1% or d.surname like %?1% or pes.title like %?1% or pes.fullName like %?1% or l like %?1%  or dl like %?1% or ss.network like %?1% or ss.nickname like %?1% or ss.title like %?1% or prs.title like %?1% or prs.position like %?1%  or prs.company like %?1% ") 
	Collection<Diner> findDinersByKeyWord(String keyword);
	
	//Top 3 best-buying diners
	@Query("select sum(sc.priceTotal) from ShoppingCart sc group by sc order by sum(sc.priceTotal) desc")
	Object[] bestBoughtDiners();
	
	//The events that a diner has organized
	@Query("select e from Event e where e.organizer.id = ?1")
	Collection<Event> findEventsOfDiner(int dinerID);
	
	//The events that a diner has organized
	@Query("select e from Event e join e.soirees s where s.date<CURRENT_DATE and e.organizer.id = ?1")
	Collection<Event> findEventsPastOfDiner(int dinerID);
	

}
