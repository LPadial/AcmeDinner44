package repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Delivery;
import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{
	
	//Top 3 best-selling items
	@Query("select i,count(i) from Delivery d join d.shoppingCart sc join d.item i where sc.isOrdered = true group by i.id order by count(i) desc")
	List<Object[]> bestSelledItems();
	
	//Items that they have to deliver grouped by delivery address
	@Query("select distinct sc.deliveryAddress,i,count(i) from Delivery d join d.shoppingCart sc join d.item i where d.delivered=false and i.supermarket.id = ?1 and sc.isOrdered=1 group by i,sc.deliveryAddress order by sc.deliveryAddress")
	List<Object[]> itemsOfSupermarketNotDeliveredGroupByDeliveredAddress(int idSupermarket);
	
	//Items that they have delivered grouped by delivery address
	@Query("select distinct sc.deliveryAddress,i,count(i) from Delivery d join d.shoppingCart sc join d.item i where d.delivered=true and i.supermarket.id = ?1 and sc.isOrdered=1 group by i,sc.deliveryAddress order by sc.deliveryAddress")
	List<Object[]> itemsOfSupermarketDeliveredGroupByDeliveredAddress(int idSupermarket);
	
	//Items que están en venta
	@Query("select i from Item i where i.retailed=true")
	List<Item> itemsRetailed();
	
	//Items that a supermarket have to deliver in address
	@Query("select d from Delivery d join d.item i join d.shoppingCart sc where sc.isOrdered=1 and sc.deliveryAddress = ?1 and i.supermarket.id = ?2  and d.delivered=false")
	List<Delivery> itemsNotDeliveredInAddress(String address, int idSupermarket);
	

}
