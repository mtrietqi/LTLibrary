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
@Table(name = "categories")
public class Categories implements java.io.Serializable {

	private Integer catId;
	private String catName;
	private Set<TitleCategories> titleCategorieses = new HashSet<TitleCategories>(0);

	public Categories() {
	}

	public Categories(String catname, Set<TitleCategories> titlecategorieses) {
		this.catName = catname;
		this.titleCategorieses = titlecategorieses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "CATID", unique = true, nullable = false)
	public Integer getCatid() {
		return this.catId;
	}

	public void setCatid(Integer catid) {
		this.catId = catid;
	}

	@Column(name = "CATNAME")
	public String getCatname() {
		return this.catName;
	}

	public void setCatname(String catname) {
		this.catName = catname;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categories")
	public Set<TitleCategories> getTitlecategorieses() {
		return this.titleCategorieses;
	}

	public void setTitlecategorieses(Set<TitleCategories> titlecategorieses) {
		this.titleCategorieses = titlecategorieses;
	}

}
