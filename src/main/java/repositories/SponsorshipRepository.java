package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer>{
	
	//The average,the minimum, and the maximum number of request for sponsorship per sponsor
	@Query("select avg(s.sponsorships.size), min(s.sponsorships.size), max(s.sponsorships.size) from Sponsor s")
	Object[] avgMinMaxRequestSponsorshipPerSponsor();
	
	//The average,the minimum, and the maximum number of the ratio of accepted requests for sponsorship per sponsor
	@Query("select avg(s.sponsorships.size), min(s.sponsorships.size), max(s.sponsorships.size) from Sponsor s join s.sponsorships ss where ss.decision='ACCEPTED'")
	Object[] avgMinMaxAcceptedSponsorshipPerSponsor();

}
