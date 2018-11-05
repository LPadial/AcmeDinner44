package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Finder() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String keyword;
	private String city;
	private Integer maxResults;
	private Date storageCache;

	// Getters

	public String getKeyword() {
		return keyword;
	}

	public String getCity() {
		return city;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getStorageCache() {
		return storageCache;
	}

	// Setters
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public void setStorageCache(Date storageCache) {
		this.storageCache = storageCache;
	}

}
