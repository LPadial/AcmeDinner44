package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Soiree;

@Repository
public interface SoireeRepository extends JpaRepository<Soiree, Integer>{

}
