package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DishType;

@Repository
public interface DishTypeRepository extends JpaRepository<DishType, Integer>{

}
