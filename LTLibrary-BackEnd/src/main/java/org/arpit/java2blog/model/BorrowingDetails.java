package org.arpit.java2blog.model;


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


@Entity
@Table(name = "borrowingdetails")
public class BorrowingDetails implements java.io.Serializable {

	private Integer bordetailId;
	private Books books;
	private Borrowings borrowings;
	private Boolean isReturn;
	private Set<Returndetails> returnDetailses = new HashSet<Returndetails>(0);

	public BorrowingDetails() {
	}

	public BorrowingDetails(Books books, Borrowings borrowings) {
		this.books = books;
		this.borrowings = borrowings;
	}

	public BorrowingDetails(Books books, Borrowings borrowings, Boolean isreturn, Set<Returndetails> returndetailses) {
		this.books = books;
		this.borrowings = borrowings;
		this.isReturn = isreturn;
		this.returnDetailses = returndetailses;
	}
	
	public BorrowingDetails( Boolean isreturn) {
		this.isReturn = isreturn;
		this.returnDetailses = null;
	}
	
	public BorrowingDetails(Integer bordetailId, Boolean isreturn) {
		this.bordetailId=bordetailId;
		this.isReturn = isreturn;
		this.returnDetailses = null;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "BORDETAILID", unique = true, nullable = false)
	public Integer getBordetailid() {
		return this.bordetailId;
	}

	public void setBordetailid(Integer bordetailid) {
		this.bordetailId = bordetailid;
	}

	@ManyToOne
	@JoinColumn(name = "BOOKID", nullable = false)
	public Books getBooks() {
		return this.books;
	}

	public void setBooks(Books books) {
		this.books = books;
	}

	@ManyToOne
	@JoinColumn(name = "BORROWINGID", nullable = false)
	public Borrowings getBorrowings() {
		return this.borrowings;
	}

	public void setBorrowings(Borrowings borrowings) {
		this.borrowings = borrowings;
	}

	@Column(name = "ISRETURN")
	public Boolean getIsreturn() {
		return this.isReturn;
	}

	public void setIsreturn(Boolean isreturn) {
		this.isReturn = isreturn;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "borrowingdetails")
	public Set<Returndetails> getReturndetailses() {
		return this.returnDetailses;
	}

	public void setReturndetailses(Set<Returndetails> returndetailses) {
		this.returnDetailses = returndetailses;
	}

}
