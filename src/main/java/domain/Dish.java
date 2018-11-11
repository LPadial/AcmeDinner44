package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Dish extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Dish() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private String description;
	private Collection<String> ingredients;
	private Integer orderServed;

	// Getters

	@NotBlank
	public String getName() {
		return name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getIngredients() {
		return ingredients;
	}
	
	@Min(1)
	public Integer getOrderServed(){
		return orderServed;
	}


	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIngredients(Collection<String> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void addIngredient(String ingredient) {
		this.ingredients.add(ingredient);
	}

	public void removeIngredient(String ingredient) {
		this.ingredients.remove(ingredient);
	}
	
	public void setOrderServed(Integer orderServed){
		this.orderServed = orderServed;
	}

	
	
	// Relationships ----------------------------------------------------------
	private Soiree soiree;
	private DishType dishType;
	
	@NotNull	
	@Valid
	@ManyToOne(optional = false)
	public Soiree getSoiree() {
		return soiree;
	}

	public void setSoiree(Soiree soiree) {
		this.soiree = soiree;
	}
	
	@NotNull
	@ManyToOne(optional = false)
	public DishType getDishType() {
		return dishType;
	}

	public void setDishType(DishType dishType) {
		this.dishType = dishType;
	}

}
