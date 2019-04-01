package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Publishers;
import org.arpit.java2blog.model.Publishers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PublisherDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Publishers> getAllPublishers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Publishers> publisherList = session.createQuery("from Publishers p "
				+ "order by p.pubid desc").list();
		return publisherList;
	}

	public Publishers getPublishers(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Publishers publisher = (Publishers) session.get(Publishers.class, new Integer(id));
		return publisher;
	}

	public Publishers addPublishers(Publishers publisher) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(publisher);
		return publisher;
	}

	public void updatePublishers(Publishers publisher) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(publisher);
	}
	public Publishers updatePublishersREST(Publishers publisher) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(publisher);
		return publisher;
	}

	public void deletePublishers(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Publishers p = (Publishers) session.load(Publishers.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
