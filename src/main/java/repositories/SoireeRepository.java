package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Diner;
import domain.Soiree;
import domain.Sponsorship;
import domain.Vote;

@Repository
public interface SoireeRepository extends JpaRepository<Soiree, Integer>{
	
	@Query("select o from Event e join e.soirees s join s.organizer o where e.id=?1")
	Collection<Diner> organizerOfSoireesOfEvent(int eventID);
	
	@Query("select ss from Sponsorship ss join ss.soiree s where s.id=?1")
	Collection<Sponsorship> sponsorsihpOfSoiree(int soireeID);
	
	@Query("select s from Soiree s where s.organizer.id=?1")
	Collection<Soiree> soireesOfDiner(int dinerID);
	
	@Query("select v from Vote v where v.soiree.id=?1")
	Collection<Vote> votesOfSoiree(int soireeID);
	

}
