package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class SpamWord extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public SpamWord() {
		super();
	}

	// Attributes -------------------------------------------------------------
	private String value;
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	

}
