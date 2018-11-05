package domain;



import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class DishType extends DomainEntity{

	// Constructors -----------------------------------------------------------

	public DishType() {
		super();
	}

	// Attributes -------------------------------------------------------------
	
	private String value;
	
	@NotBlank
	public String getValue() {
		return value;
	}
	
	public void setValue(String value){
		this.value=value;
	}

}
