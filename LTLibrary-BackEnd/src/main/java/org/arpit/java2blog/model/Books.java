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
@Table(name = "books")
public class Books implements java.io.Serializable {

	private Integer bookId;
	private Integer librariansByCreatedby;
	private Integer librariansByUpdatedby;
	private Titles titles;
	private String barCode;
	private String bookStatus;
	private Float realPrice;
	private String note;
	private Date createdDate;
	private Date updatedDate;
	private Set<BorrowingDetails> borrowingDetails = new HashSet<BorrowingDetails>(0);

	public Books() {
	}

	public Books(Integer librariansByCreatedby, Integer librariansByUpdatedby, Titles titles) {
		this.librariansByCreatedby = librariansByCreatedby;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.titles = titles;
	}

	public Books(Integer librariansByCreatedby, Integer librariansByUpdatedby, Titles titles, String barcode,
			String bookstatus, Float realprice, String note, Date createddate, Date updateddate,
			Set<BorrowingDetails> borrowingdetailses) {
		this.librariansByCreatedby = librariansByCreatedby;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.titles = titles;
		this.barCode = barcode;
		this.bookStatus = bookstatus;
		this.realPrice = realprice;
		this.note = note;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.borrowingDetails = borrowingdetailses;
	}
	public Books(Integer bookId,Integer librariansByCreatedby, Integer librariansByUpdatedby, String barcode,
			String bookstatus, Float realprice, String note, Date createddate, Date updateddate) {
		this.bookId=bookId;
		this.librariansByCreatedby = librariansByCreatedby;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.barCode = barcode;
		this.bookStatus = bookstatus;
		this.realPrice = realprice;
		this.note = note;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.borrowingDetails = null;
	}
	public Books(Integer librariansByCreatedby, Integer librariansByUpdatedby, String barcode,
			String bookstatus, Float realprice, String note, Date createddate, Date updateddate) {
		this.librariansByCreatedby = librariansByCreatedby;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.barCode = barcode;
		this.bookStatus = bookstatus;
		this.realPrice = realprice;
		this.note = note;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.borrowingDetails = null;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "BOOKID", unique = true, nullable = false)
	public Integer getBookid() {
		return this.bookId;
	}

	public void setBookid(Integer bookid) {
		this.bookId = bookid;
	}

	@Column(name="CreatedBy")
	public Integer getLibrariansByCreatedby() {
		return this.librariansByCreatedby;
	}

	public void setLibrariansByCreatedby(Integer librariansByCreatedby) {
		this.librariansByCreatedby = librariansByCreatedby;
	}

	@Column(name="UpdatedBy")
	public Integer getLibrariansByUpdatedby() {
		return this.librariansByUpdatedby;
	}

	public void setLibrariansByUpdatedby(Integer librariansByUpdatedby) {
		this.librariansByUpdatedby = librariansByUpdatedby;
	}

	@ManyToOne
	@JoinColumn(name = "TITLEID", nullable = false)
	public Titles getTitles() {
		return this.titles;
	}

	public void setTitles(Titles titles) {
		this.titles = titles;
	}

	@Column(name = "BARCODE")
	public String getBarcode() {
		return this.barCode;
	}

	public void setBarcode(String barcode) {
		this.barCode = barcode;
	}

	@Column(name = "BOOKSTATUS", length = 20)
	public String getBookstatus() {
		return this.bookStatus;
	}

	public void setBookstatus(String bookstatus) {
		this.bookStatus = bookstatus;
	}

	@Column(name = "REALPRICE", precision = 8)
	public Float getRealprice() {
		return this.realPrice;
	}

	public void setRealprice(Float realprice) {
		this.realPrice = realprice;
	}

	@Column(name = "NOTE")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDDATE", length = 10)
	public Date getCreateddate() {
		return this.createdDate;
	}

	public void setCreateddate(Date createddate) {
		this.createdDate = createddate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDDATE", length = 10)
	public Date getUpdateddate() {
		return this.updatedDate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updatedDate = updateddate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "books")
	public Set<BorrowingDetails> getBorrowingdetailses() {
		return this.borrowingDetails;
	}

	public void setBorrowingdetailses(Set<BorrowingDetails> borrowingdetailses) {
		this.borrowingDetails = borrowingdetailses;
	}

}
