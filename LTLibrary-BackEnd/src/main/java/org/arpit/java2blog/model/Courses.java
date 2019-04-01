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
@Table(name = "courses")
public class Courses implements java.io.Serializable {

	private Integer courseId;
	private String courseCode;
	private String courseName;
	private Integer credit;
	private Set<Coursebooks> coursebookses = new HashSet<Coursebooks>(0);

	public Courses() {
	}

	public Courses(String coursecode, String coursename, Integer credit, Set<Coursebooks> coursebookses) {
		this.courseCode = coursecode;
		this.courseName = coursename;
		this.credit = credit;
		this.coursebookses = coursebookses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "COURSEID", unique = true, nullable = false)
	public Integer getCourseid() {
		return this.courseId;
	}

	public void setCourseid(Integer courseid) {
		this.courseId = courseid;
	}

	@Column(name = "COURSECODE", length = 50)
	public String getCoursecode() {
		return this.courseCode;
	}

	public void setCoursecode(String coursecode) {
		this.courseCode = coursecode;
	}

	@Column(name = "COURSENAME")
	public String getCoursename() {
		return this.courseName;
	}

	public void setCoursename(String coursename) {
		this.courseName = coursename;
	}

	@Column(name = "CREDIT")
	public Integer getCredit() {
		return this.credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "courses")
	public Set<Coursebooks> getCoursebookses() {
		return this.coursebookses;
	}

	public void setCoursebookses(Set<Coursebooks> coursebookses) {
		this.coursebookses = coursebookses;
	}

}
