package org.arpit.java2blog.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "titles")
public class Titles implements java.io.Serializable {

	private Integer titleId;
	private Publishers publishers;
	private String isbn;
	private String bookTitle;
	private String edition;
	private Date pubdate;
	private Float price;
	private String bookImage;
	private String tableContent;
	private String description;
	private Set<TitleAuthors> titleAuthorses = new HashSet<TitleAuthors>(0);
	private Set<Books> bookses = new HashSet<Books>(0);
	private Set<TitleCategories> titleCategorieses = new HashSet<TitleCategories>(0);
	private Set<Coursebooks> courseBookses = new HashSet<Coursebooks>(0);

	public Titles() {
	}

	public Titles(Publishers publishers, String isbn) {
		this.publishers = publishers;
		this.isbn = isbn;
	}
	
	public Titles( String isbn, String booktitle, String edition, Date pubdate, Float price, String description) {

		this.isbn = isbn;
		this.bookTitle = booktitle;
		this.edition = edition;
		this.pubdate = pubdate;
		this.price = price;
		this.description = description;
		this.titleAuthorses = null;
		this.bookses = null;
		this.titleCategorieses = null;
		this.courseBookses = null;
	}
	public Titles(Integer titleId, String isbn, String booktitle, String edition, Date pubdate, Float price, String description) {
		this.titleId=titleId;

		this.isbn = isbn;
		this.bookTitle = booktitle;
		this.edition = edition;
		this.pubdate = pubdate;
		this.price = price;
		this.description = description;
		this.titleAuthorses = null;
		this.bookses = null;
		this.titleCategorieses = null;
		this.courseBookses = null;
	}
	public Titles(Publishers publishers, String isbn, String booktitle, String edition, Date pubdate, Float price,
			String bookimage, String tablecontent, String description, Set<TitleAuthors> titleauthorses,
			Set<Books> bookses, Set<TitleCategories> titlecategorieses, Set<Coursebooks> coursebookses) {
		this.publishers = publishers;
		this.isbn = isbn;
		this.bookTitle = booktitle;
		this.edition = edition;
		this.pubdate = pubdate;
		this.price = price;
		this.bookImage = bookimage;
		this.tableContent = tablecontent;
		this.description = description;
		this.titleAuthorses = titleauthorses;
		this.bookses = bookses;
		this.titleCategorieses = titlecategorieses;
		this.courseBookses = coursebookses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "TITLEID", unique = true, nullable = false)
	public Integer getTitleid() {
		return this.titleId;
	}

	public void setTitleid(Integer titleid) {
		this.titleId = titleid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PUBID", nullable = false)
	public Publishers getPublishers() {
		return this.publishers;
	}

	public void setPublishers(Publishers publishers) {
		this.publishers = publishers;
	}

	@Column(name = "ISBN", nullable = false)
	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Column(name = "BOOKTITLE")
	public String getBooktitle() {
		return this.bookTitle;
	}

	public void setBooktitle(String booktitle) {
		this.bookTitle = booktitle;
	}

	@Column(name = "EDITION", length = 50)
	public String getEdition() {
		return this.edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PUBDATE", length = 10)
	public Date getPubdate() {
		return this.pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	@Column(name = "PRICE", precision = 8)
	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Column(name = "BOOKIMAGE")
	public String getBookimage() {
		return this.bookImage;
	}

	public void setBookimage(String bookimage) {
		this.bookImage = bookimage;
	}

	@Column(name = "TABLECONTENT")
	public String getTablecontent() {
		return this.tableContent;
	}

	public void setTablecontent(String tablecontent) {
		this.tableContent = tablecontent;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "titles")
	public Set<TitleAuthors> getTitleauthorses() {
		return this.titleAuthorses;
	}

	public void setTitleauthorses(Set<TitleAuthors> titleauthorses) {
		this.titleAuthorses = titleauthorses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "titles")
	public Set<Books> getBookses() {
		return this.bookses;
	}

	public void setBookses(Set<Books> bookses) {
		this.bookses = bookses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "titles")
	public Set<TitleCategories> getTitlecategorieses() {
		return this.titleCategorieses;
	}

	public void setTitlecategorieses(Set<TitleCategories> titlecategorieses) {
		this.titleCategorieses = titlecategorieses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "titles")
	public Set<Coursebooks> getCoursebookses() {
		return this.courseBookses;
	}

	public void setCoursebookses(Set<Coursebooks> coursebookses) {
		this.courseBookses = coursebookses;
	}

}
