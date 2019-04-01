package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Courses;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Courses> getAllCourses() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Courses> courseList = session.createQuery("from Courses").list();
		return courseList;
	}

	public Courses getCourses(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Courses course = (Courses) session.get(Courses.class, new Integer(id));
		return course;
	}

	public Courses addCourses(Courses course) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(course);
		return course;
	}

	public void updateCourses(Courses course) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(course);
	}

	public void deleteCourses(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Courses p = (Courses) session.load(Courses.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
