package org.arpit.java2blog.model;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "authors")
public class Authors implements java.io.Serializable {

	private Integer auId;
	private String firstName;
	private String lastName;
	private Boolean gender;
	private String country;
	private Set<TitleAuthors> titleAuthorses = new HashSet<TitleAuthors>(0);

	public Authors() {
	}

	public Authors(String firstname, String lastname, Boolean gender, String country,
			Set<TitleAuthors> titleauthorses) {
		this.firstName = firstname;
		this.lastName = lastname;
		this.gender = gender;
		this.country = country;
		this.titleAuthorses = titleauthorses;
	}
	
	public Authors(Integer auId, String firstname, String lastname, Boolean gender, String country) {
		this.auId=auId;
		this.firstName = firstname;
		this.lastName = lastname;
		this.gender = gender;
		this.country = country;
		this.titleAuthorses = null;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "AUID", unique = true, nullable = false)
	public Integer getAuid() {
		return this.auId;
	}

	public void setAuid(Integer auid) {
		this.auId = auid;
	}

	@Column(name = "FIRSTNAME", length = 50)
	public String getFirstname() {
		return this.firstName;
	}

	public void setFirstname(String firstname) {
		this.firstName = firstname;
	}

	@Column(name = "LASTNAME", length = 50)
	public String getLastname() {
		return this.lastName;
	}

	public void setLastname(String lastname) {
		this.lastName = lastname;
	}

	@Column(name = "GENDER")
	public Boolean getGender() {
		return this.gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	@Column(name = "COUNTRY", length = 100)
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "authors")
	public Set<TitleAuthors> getTitleauthorses() {
		return this.titleAuthorses;
	}

	public void setTitleauthorses(Set<TitleAuthors> titleauthorses) {
		this.titleAuthorses = titleauthorses;
	}

}
