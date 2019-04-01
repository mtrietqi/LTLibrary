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
@Table(name = "returnings")
public class Returnings implements java.io.Serializable {

	private Integer returnId;
	private Borrowers borrowers;
	private Integer librariansByCreatedby;
	private Integer librariansByUpdatedby;
	private Date returnDate;
	private Date createdDate;
	private Date updatedDate;
	private Set<Returndetails> returnDetailses = new HashSet<Returndetails>(0);

	public Returnings() {
	}

	public Returnings(Borrowers borrowers, Integer librariansByCreatedby, Integer librariansByUpdatedby) {
		this.borrowers = borrowers;
		this.librariansByCreatedby = librariansByCreatedby;
		this.librariansByUpdatedby = librariansByUpdatedby;
	}

	public Returnings(Borrowers borrowers, Integer librariansByCreatedby, Integer librariansByUpdatedby,
			Date returndate, Date createddate, Date updateddate, Set<Returndetails> returndetailses) {
		this.borrowers = borrowers;
		this.librariansByCreatedby = librariansByCreatedby;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.returnDate = returndate;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.returnDetailses = returndetailses;
	}
	public Returnings( Integer librariansByCreatedby, Integer librariansByUpdatedby,
			Date returndate, Date createddate, Date updateddate) {
		this.librariansByCreatedby = librariansByCreatedby;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.returnDate = returndate;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.returnDetailses = null;
	}
	
	public Returnings(Integer returnId, Integer librariansByCreatedby, Integer librariansByUpdatedby,
			Date returndate, Date createddate, Date updateddate) {
		this.returnId=returnId;
		this.librariansByCreatedby = librariansByCreatedby;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.returnDate = returndate;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.returnDetailses = null;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "RETURNID", unique = true, nullable = false)
	public Integer getReturnid() {
		return this.returnId;
	}

	public void setReturnid(Integer returnid) {
		this.returnId = returnid;
	}

	@ManyToOne
	@JoinColumn(name = "BORROWERID", nullable = false)
	public Borrowers getBorrowers() {
		return this.borrowers;
	}

	public void setBorrowers(Borrowers borrowers) {
		this.borrowers = borrowers;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RETURNDATE", length = 19)
	public Date getReturndate() {
		return this.returnDate;
	}

	public void setReturndate(Date returndate) {
		this.returnDate = returndate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATEDDATE", length = 10)
	public Date getCreateddate() {
		return this.createdDate;
	}

	public void setCreateddate(Date createddate) {
		this.createdDate = createddate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDDATE", length = 19)
	public Date getUpdateddate() {
		return this.updatedDate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updatedDate = updateddate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "returnings")
	public Set<Returndetails> getReturndetailses() {
		return this.returnDetailses;
	}

	public void setReturndetailses(Set<Returndetails> returndetailses) {
		this.returnDetailses = returndetailses;
	}

}
