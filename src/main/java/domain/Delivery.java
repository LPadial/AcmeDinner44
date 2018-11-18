package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Delivery extends DomainEntity{
	
	// Constructors -----------------------------------------------------------

	public Delivery() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private Boolean delivered;

	public Boolean getDelivered() {
		return delivered;
	}
	
	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}
		
	// Relationships ----------------------------------------------------------
	private Item item;
	private ShoppingCart shoppingCart;
	
	@Valid
	@ManyToOne(optional=false)
	@NotNull
	public Item getItem(){
		return item;
	}
	
	public void setItem(Item item){
		this.item = item;
	}
	
	@Valid
	@ManyToOne(optional=false)
	@NotNull
	public ShoppingCart getShoppingCart(){
		return shoppingCart;
	}
	
	public void setShoppingCart(ShoppingCart shoppingCart){
		this.shoppingCart = shoppingCart;
	}

}
