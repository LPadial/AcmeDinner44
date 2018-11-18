package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;
import domain.Supermarket;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, Integer>  {
	
	//Top 3 best-selling supermarkets
	@Query("select s,sum(i.price) from Delivery d join d.shoppingCart sc join d.item i join i.supermarket s where sc.isOrdered = true group by s.id order by sum(i.price) desc")
	List<Object[]> bestSellingSupermarkets();
	
	//The events that a diner has organized
	@Query("select i from Item i where i.supermarket.id = ?1")
	Collection<Item> findItemsOfSupermarket(int supermarketID);

}
