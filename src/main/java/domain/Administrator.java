package domain;



import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	// Constructors -----------------------------------------------------------

	public Administrator() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private Collection<Entidad> entidades;
	@Valid
	@OneToMany
	public Collection<Entidad> getEntidades() {
		return this.entidades;
	}

	public void setEntidades(final Collection<Entidad> entidades) {
		this.entidades = entidades;
	}



	

}
