package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chirp;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer>{
	
	//The average, the minimum, and the maximum number of chirps per actor
	@Query("select avg(a.chirps.size), min(a.chirps.size), max(a.chirps.size) from Actor a")
	Object[] avgMinMaxChirpsPerActor();

	//Chirps de un actor ordenados en orden descendente por mommentWritten
	@Query("select c from Actor a join a.chirps c where a.id=?1 order by c.dateCreation DESC")
	Collection<Chirp> chirpsOfActor(int ActorId);

	//Todos los chirps ordenados en orden descendente.
	@Query("select c from Chirp c order by c.dateCreation DESC")
	Collection<Chirp> allChirpsOrderByMommentDesc();

	//lista de chirps de los suscripto de ese actor
	@Query("select c from Actor a join a.followers f join f.chirps c where a.id = ?1 order by c.dateCreation DESC")
	Collection<Chirp> listChirpByFollower(int followerId);
	
	

}
