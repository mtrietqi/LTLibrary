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
@Table(name = "publishers")
public class Publishers implements java.io.Serializable {

	private Integer pubId;
	private String pubName;
	private Set<Titles> titleses = new HashSet<Titles>(0);

	public Publishers() {
	}

	public Publishers(String pubname, Set<Titles> titleses) {
		this.pubName = pubname;
		this.titleses = titleses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "PUBID", unique = true, nullable = false)
	public Integer getPubid() {
		return this.pubId;
	}

	public void setPubid(Integer pubid) {
		this.pubId = pubid;
	}

	@Column(name = "PUBNAME")
	public String getPubname() {
		return this.pubName;
	}

	public void setPubname(String pubname) {
		this.pubName = pubname;
	}

	@OneToMany(mappedBy = "publishers")
	public Set<Titles> getTitleses() {
		return this.titleses;
	}

	public void setTitleses(Set<Titles> titleses) {
		this.titleses = titleses;
	}

}
