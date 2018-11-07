package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Diner;
import domain.Soiree;

@Repository
public interface SoireeRepository extends JpaRepository<Soiree, Integer>{
	
	@Query("select o from Event e join e.soirees s join s.organizer o where e.id=?1")
	Collection<Diner> organizerOfSoireesOfEvent(int eventID);

}
