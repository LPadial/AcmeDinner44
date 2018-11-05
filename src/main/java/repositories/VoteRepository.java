package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer>{

}
