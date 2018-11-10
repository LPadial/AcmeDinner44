package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer>{
	
	//Diner has a vote in soiree 
	@Query("select count(v) from Vote v join v.voter vd join v.soiree s where vd.id=?1 and s.id=?2")
	Integer dinerHasVoteInSoiree(int dinerID, int soireeID);

}
