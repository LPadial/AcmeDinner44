package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Delivery;
import domain.Item;
import domain.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>{
	
	//Devuelve los objetos de un carro de compra
	@Query("select i from Delivery d join d.item i where d.shoppingCart.id=?1")
	List<Item> listItemsOfShoppingCart(int idShoppingCart);
	
	//Devuelve el precio de un carro de compra
	@Query("select sum(i.price) from Delivery d join d.item i where d.shoppingCart.id=?1")
	Double priceOfShoppingCart(int idShoppingCart);
	
	//Devuelve los registros del carro de compra
	@Query("select d from Delivery d where d.shoppingCart.id=?1")
	List<Delivery> deliveriesOfShoppingCart(int idShoppingCart);

}
