package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.TitleAuthors;
import org.arpit.java2blog.model.TitleAuthors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TitleAuthorDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<TitleAuthors> getAllTitleAuthors() {
		Session session = this.sessionFactory.getCurrentSession();
		List<TitleAuthors> titleAuthorList = session.createQuery("from TitleAuthors").list();
		return titleAuthorList;
	}

	public TitleAuthors getTitleAuthors(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TitleAuthors titleAuthor = (TitleAuthors) session.get(TitleAuthors.class, new Integer(id));
		return titleAuthor;
	}

	public TitleAuthors addTitleAuthors(TitleAuthors titleAuthor) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(titleAuthor);
		return titleAuthor;
	}

	public void updateTitleAuthors(TitleAuthors titleAuthor) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(titleAuthor);
	}
	public TitleAuthors updateTitleAuthorsREST(TitleAuthors titleAuthors) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(titleAuthors);
		return titleAuthors;
	}

	public void deleteTitleAuthors(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TitleAuthors p = (TitleAuthors) session.load(TitleAuthors.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
