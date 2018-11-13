package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BusinessCard;

@Repository
public interface BusinessCardRepository extends JpaRepository<BusinessCard, Integer>  {
	
	@Query("select b from BusinessCard b join b.professionalSections p where p.id=?1")
	BusinessCard findBusinessCardOfProfessionalSection(int IDProfessinalSection);
	
	@Query("select b from BusinessCard b join b.socialSections s where s.id=?1")
	BusinessCard findBusinessCardOfSocialSection(int IDSocialSection);

}
