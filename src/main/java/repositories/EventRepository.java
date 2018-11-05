package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Diner;
import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	
	//Search a event using a keyword that must appear in its title or its description 
	@Query("select e from Event e where e.title like %?1% or e.description like %?1%")
	List<Event> findEventsByKeyWord(String keyword);
	
	//Average,minimum and maximum number of events organised of the diners
	@Query("select count(e), e.organizer from Event e group by e.organizer")
	Object[] numEventForDinner();
	
	@Query("select d from Diner d join d.events e where e.id = ?1")
	Collection<Diner> findRegisteredDinerInEvents(int eventID);

}
