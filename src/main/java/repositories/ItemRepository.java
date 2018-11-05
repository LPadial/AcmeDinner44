package repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	
	//Top 3 best-selling items
	@Query("select i,count(i) from ShoppingCart sc join sc.items i where sc.isOrdered = true group by i.id order by count(i) desc")
	Object[] bestSelledItems();
	

}
