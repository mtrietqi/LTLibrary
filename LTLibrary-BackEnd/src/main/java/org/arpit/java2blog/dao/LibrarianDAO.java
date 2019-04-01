package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Librarians;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Librarians;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LibrarianDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Librarians> getAllLibrarians() {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		List<Librarians> librarianList = session.createQuery("from Librarians l "
				+ "order by l.libid desc").list();
		return librarianList;
	}

	public Librarians getLibrarians(int id) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		Librarians librarian = (Librarians) session.get(Librarians.class, new Integer(id));
		return librarian;
	}

	public Librarians addLibrarians(Librarians librarian) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		session.persist(librarian);
//		trans.commit(); 
		return librarian;
	}

	public void updateLibrarians(Librarians librarian) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		session.update(librarian);
//		trans.commit(); 
	}
	

	public Librarians updateLibrariansREST(Librarians librarian) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		session.update(librarian);
//		trans.commit(); 
		return librarian;
	}

	public void deleteLibrarians(int id) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		Librarians p = (Librarians) session.load(Librarians.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
//		trans.commit(); 
	}	
	//For Testing
	public List<Librarians> getAllLibrariansTEST() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		List<Librarians> librarianList = session.createQuery("from Librarians l "
				+ "order by l.libid desc").list();
		return librarianList;
	}

	public Librarians getLibrariansTEST(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Librarians librarian = (Librarians) session.get(Librarians.class, new Integer(id));
		return librarian;
	}

	public Librarians addLibrariansTEST(Librarians librarian) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.persist(librarian);
		trans.commit(); 
		return librarian;
	}

	public void updateLibrariansTEST(Librarians librarian) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.update(librarian);
		trans.commit(); 
	}
	

	public Librarians updateLibrariansRESTTEST(Librarians librarian) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.update(librarian);
		trans.commit(); 
		return librarian;
	}

	public void deleteLibrariansTEST(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Librarians p = (Librarians) session.load(Librarians.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		trans.commit(); 
	}	
}
