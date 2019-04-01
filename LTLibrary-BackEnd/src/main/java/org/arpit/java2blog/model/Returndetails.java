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
@Table(name = "returndetails")
public class Returndetails implements java.io.Serializable {

	private Integer returnDetailid;
	private BorrowingDetails borrowingDetails;
	private Returnings returnings;
	private String description;
	private String returnStatus;

	public Returndetails() {
	}

	public Returndetails(BorrowingDetails borrowingdetails, Returnings returnings) {
		this.borrowingDetails = borrowingdetails;
		this.returnings = returnings;
	}

	public Returndetails(BorrowingDetails borrowingdetails, Returnings returnings, String description,
			String returnstatus) {
		this.borrowingDetails = borrowingdetails;
		this.returnings = returnings;
		this.description = description;
		this.returnStatus = returnstatus;
	}
	
	public Returndetails( String description,String returnstatus) {
		this.description = description;
		this.returnStatus = returnstatus;
	}
	public Returndetails(Integer returnDetailid,String description,String returnstatus) {
		this.returnDetailid=returnDetailid;
		this.description = description;
		this.returnStatus = returnstatus;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "RETURNDETAILID", unique = true, nullable = false)
	public Integer getReturndetailid() {
		return this.returnDetailid;
	}

	public void setReturndetailid(Integer returndetailid) {
		this.returnDetailid = returndetailid;
	}

	@ManyToOne
	@JoinColumn(name = "BORDETAILID", nullable = false)
	public BorrowingDetails getBorrowingdetails() {
		return this.borrowingDetails;
	}

	public void setBorrowingdetails(BorrowingDetails borrowingdetails) {
		this.borrowingDetails = borrowingdetails;
	}

	@ManyToOne
	@JoinColumn(name = "RETURNID", nullable = false)
	public Returnings getReturnings() {
		return this.returnings;
	}

	public void setReturnings(Returnings returnings) {
		this.returnings = returnings;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "RETURNSTATUS", length = 20)
	public String getReturnstatus() {
		return this.returnStatus;
	}

	public void setReturnstatus(String returnstatus) {
		this.returnStatus = returnstatus;
	}

}
