package org.arpit.java2blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "titlecategories")
public class TitleCategories implements java.io.Serializable {

	private Integer titleCatid;
	private Categories categories;
	private Titles titles;

	public TitleCategories() {
	}

	public TitleCategories(Integer titleCatid) {
		this.titleCatid = titleCatid;
	}

	public TitleCategories(Categories categories, Titles titles) {
		this.categories = categories;
		this.titles = titles;
	}
	public TitleCategories(Integer titleCatid, Categories categories, Titles titles) {
		this.titleCatid=titleCatid;
		this.categories = categories;
		this.titles = titles;
		this.categories.setTitlecategorieses(null);
		this.titles.setBookses(null);
		this.titles.setTitleauthorses(null);
		this.titles.setCoursebookses(null);
		this.titles.setTitlecategorieses(null);
		this.titles.getPublishers().setTitleses(null);
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "TITLECATID", unique = true, nullable = false)
	public Integer getTitlecatid() {
		return this.titleCatid;
	}

	public void setTitlecatid(Integer titlecatid) {
		this.titleCatid = titlecatid;
	}

	@ManyToOne
	@JoinColumn(name = "CATID", nullable = false)
	public Categories getCategories() {
		return this.categories;
	}

	public void setCategories(Categories categories) {
		this.categories = categories;
	}

	@ManyToOne
	@JoinColumn(name = "TITLEID", nullable = false)
	public Titles getTitles() {
		return this.titles;
	}

	public void setTitles(Titles titles) {
		this.titles = titles;
	}

}
