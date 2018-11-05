package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ProfessionalSection;

@Repository
public interface ProfessionalSectionRepository extends JpaRepository<ProfessionalSection, Integer>{

}
