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
@Table(name = "coursebooks")
public class Coursebooks implements java.io.Serializable {

	private Integer courseBookId;
	private Courses courses;
	private Titles titles;

	public Coursebooks() {
	}

	public Coursebooks(Courses courses, Titles titles) {
		this.courses = courses;
		this.titles = titles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "COURSEBOOKID", unique = true, nullable = false)
	public Integer getCoursebookid() {
		return this.courseBookId;
	}

	public void setCoursebookid(Integer coursebookid) {
		this.courseBookId = coursebookid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COURSEID", nullable = false)
	public Courses getCourses() {
		return this.courses;
	}

	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TITLEID", nullable = false)
	public Titles getTitles() {
		return this.titles;
	}

	public void setTitles(Titles titles) {
		this.titles = titles;
	}

}
