package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Supermarket;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, Integer>  {
	
	//Top 3 best-selling supermarkets
	@Query("select s,count(i) from ShoppingCart sc join sc.items i join i.supermarket s where sc.isOrdered = true group by s.id order by count(i) desc")
	Object[] bestSellingSupermarkets();

}
