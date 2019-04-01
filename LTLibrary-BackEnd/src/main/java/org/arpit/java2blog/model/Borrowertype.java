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
@Table(name = "borrowertype")
public class Borrowertype implements java.io.Serializable {

	private Integer borrowerTypeId;
	private String typeName;
	private Integer maxBookIssue;
	private Integer maxDayIssue;
	private Float penaltyPerDay;
	private Set<Borrowers> borrowerses = new HashSet<Borrowers>(0);

	public Borrowertype() {
	}

	public Borrowertype(String typename, Integer maxbookissue, Integer maxdayissue, Float penaltyperday,
			Set<Borrowers> borrowerses) {
		this.typeName = typename;
		this.maxBookIssue = maxbookissue;
		this.maxDayIssue = maxdayissue;
		this.penaltyPerDay = penaltyperday;
		this.borrowerses = borrowerses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "BORROWERTYPEID", unique = true, nullable = false)
	public Integer getBorrowertypeid() {
		return this.borrowerTypeId;
	}

	public void setBorrowertypeid(Integer borrowertypeid) {
		this.borrowerTypeId = borrowertypeid;
	}

	@Column(name = "TYPENAME")
	public String getTypename() {
		return this.typeName;
	}

	public void setTypename(String typename) {
		this.typeName = typename;
	}

	@Column(name = "MAXBOOKISSUE")
	public Integer getMaxbookissue() {
		return this.maxBookIssue;
	}

	public void setMaxbookissue(Integer maxbookissue) {
		this.maxBookIssue = maxbookissue;
	}

	@Column(name = "MAXDAYISSUE")
	public Integer getMaxdayissue() {
		return this.maxDayIssue;
	}

	public void setMaxdayissue(Integer maxdayissue) {
		this.maxDayIssue = maxdayissue;
	}

	@Column(name = "PENALTYPERDAY", precision = 8)
	public Float getPenaltyperday() {
		return this.penaltyPerDay;
	}

	public void setPenaltyperday(Float penaltyperday) {
		this.penaltyPerDay = penaltyperday;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "borrowertype")
	public Set<Borrowers> getBorrowerses() {
		return this.borrowerses;
	}

	public void setBorrowerses(Set<Borrowers> borrowerses) {
		this.borrowerses = borrowerses;
	}

}
