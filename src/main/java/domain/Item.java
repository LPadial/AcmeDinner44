package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Item extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Item() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String SKU;
	private String name;
	private String photo;
	private Double price;
	private Double VAT;
	private Boolean hint;

	// Getters

	@NotBlank
	public String getSKU() {
		return SKU;
	}

	@NotBlank
	public String getName() {
		return name;
	}

	@NotBlank
	@URL
	public String getPhoto() {
		return photo;
	}

	public Double getPrice() {
		return price;
	}

	public Double getVAT() {
		return VAT;
	}

	public Boolean getHint() {
		return hint;
	}

	// Setters
	public void setSKU(String SKU) {
		this.SKU = SKU;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setVAT(Double VAT) {
		this.VAT = VAT;
	}

	public void setHint(Boolean hint) {
		this.hint = hint;
	}
	
	// Relationships ----------------------------------------------------------
	
	private Supermarket supermarket;
	
	@ManyToOne(optional=false)
	public Supermarket getSupermarket(){
		return supermarket;
	}
	
	public void setSupermarket(Supermarket supermarket){
		this.supermarket = supermarket;
	}
}
