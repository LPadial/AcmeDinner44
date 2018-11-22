package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	//Password
	@Query("select u.password from Actor a join a.userAccount u where a=?1")
	String encryptPassword(Actor a);
}
