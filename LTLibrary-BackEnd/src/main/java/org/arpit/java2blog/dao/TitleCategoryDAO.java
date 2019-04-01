package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.TitleCategories;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TitleCategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<TitleCategories> getAllTitleCategories() {
		Session session = this.sessionFactory.getCurrentSession();
		List<TitleCategories> titleCategoryList = session.createQuery("from TitleCategories").list();
		return titleCategoryList;
	}

	public TitleCategories getTitleCategories(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TitleCategories titleCategory = (TitleCategories) session.get(TitleCategories.class, new Integer(id));
		return titleCategory;
	}

	public TitleCategories addTitleCategories(TitleCategories titleCategory) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(titleCategory);
		return titleCategory;
	}

	public void updateTitleCategories(TitleCategories titleCategory) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(titleCategory);
	}
	public TitleCategories updateTitleCategoriesREST(TitleCategories titleCategory) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(titleCategory);
		return titleCategory;
	}

	public void deleteTitleCategories(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		TitleCategories p = (TitleCategories) session.load(TitleCategories.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
