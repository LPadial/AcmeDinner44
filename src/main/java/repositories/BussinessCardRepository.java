package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BussinessCard;

@Repository
public interface BussinessCardRepository extends JpaRepository<BussinessCard, Integer>  {
	
	@Query("select b from BussinessCard b join b.professionalSections p where p.id=?1")
	BussinessCard findBussinessCardOfProfessionalSection(int IDProfessinalSection);
	
	@Query("select b from BussinessCard b join b.socialSections s where s.id=?1")
	BussinessCard findBussinessCardOfSocialSection(int IDSocialSection);

}
