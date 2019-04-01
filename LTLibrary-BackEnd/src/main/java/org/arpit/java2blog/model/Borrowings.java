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
@Table(name = "borrowings")
public class Borrowings implements java.io.Serializable {

	private Integer borrowingId;
	private Borrowers borrowers;
	private Integer librariansByUpdatedby;
	private Integer librariansByCreatedby;
	private Date bordate;
	private Date createdDate;
	private Date updatedDate;
	private Set<BorrowingDetails> borrowingDetailses = new HashSet<BorrowingDetails>(0);

	public Borrowings() {
	}

	public Borrowings(Borrowers borrowers, Integer librariansByUpdatedby, Integer librariansByCreatedby) {
		this.borrowers = borrowers;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.librariansByCreatedby = librariansByCreatedby;
	}

	public Borrowings(Borrowers borrowers, Integer librariansByUpdatedby, Integer librariansByCreatedby,
			Date bordate, Date createddate, Date updateddate, Set<BorrowingDetails> borrowingdetailses) {
		this.borrowers = borrowers;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.librariansByCreatedby = librariansByCreatedby;
		this.bordate = bordate;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.borrowingDetailses = borrowingdetailses;
	}
	
	public Borrowings(Integer librariansByUpdatedby, Integer librariansByCreatedby,
			Date bordate, Date createddate, Date updateddate) {
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.librariansByCreatedby = librariansByCreatedby;
		this.bordate = bordate;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.borrowingDetailses = null;
	}
	
	public Borrowings(Integer borrowingId,Integer librariansByUpdatedby, Integer librariansByCreatedby,
			Date bordate, Date createddate, Date updateddate) {
		this.borrowingId=borrowingId;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.librariansByCreatedby = librariansByCreatedby;
		this.bordate = bordate;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.borrowingDetailses = null;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "BORROWINGID", unique = true, nullable = false)
	public Integer getBorrowingid() {
		return this.borrowingId;
	}

	public void setBorrowingid(Integer borrowingid) {
		this.borrowingId = borrowingid;
	}

	@ManyToOne
	@JoinColumn(name = "BORROWERID", nullable = false)
	public Borrowers getBorrowers() {
		return this.borrowers;
	}

	public void setBorrowers(Borrowers borrowers) {
		this.borrowers = borrowers;
	}

	@Column(name="UpdatedBy")
	public Integer getLibrariansByUpdatedby() {
		return this.librariansByUpdatedby;
	}

	public void setLibrariansByUpdatedby(Integer librariansByUpdatedby) {
		this.librariansByUpdatedby = librariansByUpdatedby;
	}

	@Column(name="CreatedBy")
	public Integer getLibrariansByCreatedby() {
		return this.librariansByCreatedby;
	}

	public void setLibrariansByCreatedby(Integer librariansByCreatedby) {
		this.librariansByCreatedby = librariansByCreatedby;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BORDATE", length = 10)
	public Date getBordate() {
		return this.bordate;
	}

	public void setBordate(Date bordate) {
		this.bordate = bordate;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "borrowings")
	public Set<BorrowingDetails> getBorrowingdetailses() {
		return this.borrowingDetailses;
	}

	public void setBorrowingdetailses(Set<BorrowingDetails> borrowingdetailses) {
		this.borrowingDetailses = borrowingdetailses;
	}

}
