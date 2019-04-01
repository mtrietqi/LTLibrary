package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Returnings;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReturningDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Returnings> getAllReturnings() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Returnings> returningList = session.createQuery("from Returnings r "
				+ "order by r.returnid desc").list();
		return returningList;
	}

	public Returnings getReturnings(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Returnings returning = (Returnings) session.get(Returnings.class, new Integer(id));
		return returning;
	}

	public Returnings addReturnings(Returnings returning) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(returning);
		return returning;
	}

	public void updateReturnings(Returnings returning) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(returning);
	}
	public Returnings updateReturningsREST(Returnings returning) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(returning);
		return returning;
	}

	public void deleteReturnings(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Returnings p = (Returnings) session.load(Returnings.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
