package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Authors;
import org.arpit.java2blog.model.Authors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Authors> getAllAuthors() {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		List<Authors> authorList = session.createQuery("from Authors a "
				+ "order by a.auid desc").list();
		return authorList;
	}

	public Authors getAuthors(int id) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		Authors author = (Authors) session.get(Authors.class, new Integer(id));
		return author;
	}

	public Authors addAuthors(Authors author) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		session.persist(author);
//		trans.commit(); 
		return author;
	}

	public void updateAuthors(Authors author) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		session.update(author);
//		trans.commit();
	}
	
	public Authors updateAuthorsREST(Authors author) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		session.update(author);
//		trans.commit();
		return author;
	}

	public void deleteAuthors(int id) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		Authors p = (Authors) session.load(Authors.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
//		trans.commit();
	}
	//For Testing
	public List<Authors> getAllAuthorsTEST() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		List<Authors> authorList = session.createQuery("from Authors a "
				+ "order by a.auid desc").list();
		return authorList;
	}

	public Authors getAuthorsTEST(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Authors author = (Authors) session.get(Authors.class, new Integer(id));
		return author;
	}

	public Authors addAuthorsTEST(Authors author) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.persist(author);
		trans.commit(); 
		return author;
	}

	public void updateAuthorsTEST(Authors author) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.update(author);
		trans.commit();
	}
	
	public Authors updateAuthorsRESTTEST(Authors author) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.update(author);
		trans.commit();
		return author;
	}

	public void deleteAuthorsTEST(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Authors p = (Authors) session.load(Authors.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		trans.commit();
	}	
}
