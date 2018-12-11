
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Entidad;

@Repository
public interface EntidadRepository extends JpaRepository<Entidad, Integer> {

	//A query that returns the ratio of diner with at least one entidad.
	@Query("select count(d)/(select count(diner) from Diner diner) from Diner d where d.entidades.size>=1")
	Integer ratioAdminwith1oMoreEntidades();

	//A query that returns the admin who has/have created more entidades
	@Query("select a.actorName, a.entidades.size from Administrator a order by a.entidades.size desc")
	Object[] adminwhoHaveCreatedMoreEntities();
}
