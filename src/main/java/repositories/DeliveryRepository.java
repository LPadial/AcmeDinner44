package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer>{
	
	//Find a delivery with a determinate item and shopping card
	@Query("select d from Delivery d where d.item.id=?2 and d.shoppingCart.id=?1")
	List<Delivery> findDelivery(int idShoppingCart, int idItem);

}
