package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Coursebooks;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseBookDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Coursebooks> getAllCoursebooks() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Coursebooks> courseBookList = session.createQuery("from Coursebooks").list();
		return courseBookList;
	}

	public Coursebooks getCoursebooks(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Coursebooks courseBook = (Coursebooks) session.get(Coursebooks.class, new Integer(id));
		return courseBook;
	}

	public Coursebooks addCoursebooks(Coursebooks courseBook) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(courseBook);
		return courseBook;
	}

	public void updateCoursebooks(Coursebooks courseBook) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(courseBook);
	}

	public void deleteCoursebooks(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Coursebooks p = (Coursebooks) session.load(Coursebooks.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
