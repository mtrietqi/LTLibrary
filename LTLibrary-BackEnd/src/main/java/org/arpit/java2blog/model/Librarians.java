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
@Table(name = "librarians")
public class Librarians implements java.io.Serializable {

	private Integer libId;
	private Integer librariansByUpdatedby;
	private Integer librariansByCreatedby;
	private String firstName;
	private String lastName;
	private Boolean gender;
	private String email;
	private String phoneNumber;
	private String address;
	private String avatar;
	private String username;
	private String password;
	private Boolean status;
	private Date createdDate;
	private Date updatedDate;
	private String libRole;
//	private Set<Integer> librariansesForUpdatedby = new HashSet<Integer>(0);
//	private Set<Borrowings> borrowingsesForUpdatedby = new HashSet<Borrowings>(0);
//	private Set<Borrowings> borrowingsesForCreatedby = new HashSet<Borrowings>(0);
//	private Set<Borrowers> borrowersesForCreatedby = new HashSet<Borrowers>(0);
//	private Set<Returnings> returningsesForCreatedby = new HashSet<Returnings>(0);
//	private Set<Returnings> returningsesForUpdatedby = new HashSet<Returnings>(0);
//	private Set<Books> booksesForCreatedby = new HashSet<Books>(0);
//	private Set<Borrowers> borrowersesForUpdatedby = new HashSet<Borrowers>(0);
//	private Set<Integer> librariansesForCreatedby = new HashSet<Integer>(0);
//	private Set<Books> booksesForUpdatedby = new HashSet<Books>(0);

	public Librarians() {
	}

	public Librarians(Integer librariansByUpdatedby, Integer librariansByCreatedby, String firstname,
			String lastname, Boolean gender, String email, String phonenumber, String address, String avatar,
			String username, String password, Boolean status, Date createddate, Date updateddate,
			Set<Integer> librariansesForUpdatedby, Set<Borrowings> borrowingsesForUpdatedby,
			Set<Borrowings> borrowingsesForCreatedby, Set<Borrowers> borrowersesForCreatedby,
			Set<Returnings> returningsesForCreatedby, Set<Returnings> returningsesForUpdatedby,
			Set<Books> booksesForCreatedby, Set<Borrowers> borrowersesForUpdatedby,
			Set<Integer> librariansesForCreatedby, Set<Books> booksesForUpdatedby) {
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.librariansByCreatedby = librariansByCreatedby;
		this.firstName = firstname;
		this.lastName = lastname;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phonenumber;
		this.address = address;
		this.avatar = avatar;
		this.username = username;
		this.password = password;
		this.status = status;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
//		this.librariansesForUpdatedby = librariansesForUpdatedby;
//		this.borrowingsesForUpdatedby = borrowingsesForUpdatedby;
//		this.borrowingsesForCreatedby = borrowingsesForCreatedby;
//		this.borrowersesForCreatedby = borrowersesForCreatedby;
//		this.returningsesForCreatedby = returningsesForCreatedby;
//		this.returningsesForUpdatedby = returningsesForUpdatedby;
//		this.booksesForCreatedby = booksesForCreatedby;
//		this.borrowersesForUpdatedby = borrowersesForUpdatedby;
//		this.librariansesForCreatedby = librariansesForCreatedby;
//		this.booksesForUpdatedby = booksesForUpdatedby;
	}
	public Librarians( String firstname,String lastname, Boolean gender, String email, 
			String phonenumber, String address, 
			String username, String password, Boolean status, 
			Date createddate, Date updateddate,Integer librariansByUpdatedby, Integer librariansByCreatedby,String libRole
			) {
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.librariansByCreatedby = librariansByCreatedby;
		this.firstName = firstname;
		this.lastName = lastname;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phonenumber;
		this.address = address;
		this.username = username;
		this.password = password;
		this.status = status;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.libRole =libRole;
	}
	
	public Librarians( Integer libId,String firstname,String lastname, Boolean gender, String email, 
			String phonenumber, String address, 
			String username, String password, Boolean status, 
			Date createddate, Date updateddate,Integer librariansByUpdatedby, Integer librariansByCreatedby,String libRole
			) {
		this.libId=libId;
		this.librariansByUpdatedby = librariansByUpdatedby;
		this.librariansByCreatedby = librariansByCreatedby;
		this.firstName = firstname;
		this.lastName = lastname;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phonenumber;
		this.address = address;
		this.username = username;
		this.password = password;
		this.status = status;
		this.createdDate = createddate;
		this.updatedDate = updateddate;
		this.libRole=libRole;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "LIBID", unique = true, nullable = false)
	public Integer getLibid() {
		return this.libId;
	}

	public void setLibid(Integer libid) {
		this.libId = libid;
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

	@Column(name = "FIRSTNAME")
	public String getFirstname() {
		return this.firstName;
	}

	public void setFirstname(String firstname) {
		this.firstName = firstname;
	}

	@Column(name = "LASTNAME")
	public String getLastname() {
		return this.lastName;
	}

	public void setLastname(String lastname) {
		this.lastName = lastname;
	}

	@Column(name = "GENDER")
	public Boolean getGender() {
		return this.gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PHONENUMBER", length = 50)
	public String getPhonenumber() {
		return this.phoneNumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phoneNumber = phonenumber;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "AVATAR")
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name = "USERNAME")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "STATUS")
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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
	@Column(name = "LIBROLE", nullable=true)
	public String getLibRole() {
		return this.libRole;
	}

	public void setLibRole(String libRole) {
		this.libRole = libRole;
	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByUpdatedby")
//	public Set<Integer> getLibrariansesForUpdatedby() {
//		return this.librariansesForUpdatedby;
//	}
//
//	public void setLibrariansesForUpdatedby(Set<Integer> librariansesForUpdatedby) {
//		this.librariansesForUpdatedby = librariansesForUpdatedby;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByUpdatedby")
//	public Set<Borrowings> getBorrowingsesForUpdatedby() {
//		return this.borrowingsesForUpdatedby;
//	}
//
//	public void setBorrowingsesForUpdatedby(Set<Borrowings> borrowingsesForUpdatedby) {
//		this.borrowingsesForUpdatedby = borrowingsesForUpdatedby;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByCreatedby")
//	public Set<Borrowings> getBorrowingsesForCreatedby() {
//		return this.borrowingsesForCreatedby;
//	}
//
//	public void setBorrowingsesForCreatedby(Set<Borrowings> borrowingsesForCreatedby) {
//		this.borrowingsesForCreatedby = borrowingsesForCreatedby;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByCreatedby")
//	public Set<Borrowers> getBorrowersesForCreatedby() {
//		return this.borrowersesForCreatedby;
//	}
//
//	public void setBorrowersesForCreatedby(Set<Borrowers> borrowersesForCreatedby) {
//		this.borrowersesForCreatedby = borrowersesForCreatedby;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByCreatedby")
//	public Set<Returnings> getReturningsesForCreatedby() {
//		return this.returningsesForCreatedby;
//	}
//
//	public void setReturningsesForCreatedby(Set<Returnings> returningsesForCreatedby) {
//		this.returningsesForCreatedby = returningsesForCreatedby;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByUpdatedby")
//	public Set<Returnings> getReturningsesForUpdatedby() {
//		return this.returningsesForUpdatedby;
//	}
//
//	public void setReturningsesForUpdatedby(Set<Returnings> returningsesForUpdatedby) {
//		this.returningsesForUpdatedby = returningsesForUpdatedby;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByCreatedby")
//	public Set<Books> getBooksesForCreatedby() {
//		return this.booksesForCreatedby;
//	}
//
//	public void setBooksesForCreatedby(Set<Books> booksesForCreatedby) {
//		this.booksesForCreatedby = booksesForCreatedby;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByUpdatedby")
//	public Set<Borrowers> getBorrowersesForUpdatedby() {
//		return this.borrowersesForUpdatedby;
//	}
//
//	public void setBorrowersesForUpdatedby(Set<Borrowers> borrowersesForUpdatedby) {
//		this.borrowersesForUpdatedby = borrowersesForUpdatedby;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByCreatedby")
//	public Set<Integer> getLibrariansesForCreatedby() {
//		return this.librariansesForCreatedby;
//	}
//
//	public void setLibrariansesForCreatedby(Set<Integer> librariansesForCreatedby) {
//		this.librariansesForCreatedby = librariansesForCreatedby;
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "librariansByUpdatedby")
//	public Set<Books> getBooksesForUpdatedby() {
//		return this.booksesForUpdatedby;
//	}
//
//	public void setBooksesForUpdatedby(Set<Books> booksesForUpdatedby) {
//		this.booksesForUpdatedby = booksesForUpdatedby;
//	}

}
