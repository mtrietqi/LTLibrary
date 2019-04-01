package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Categories;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Categories> getAllCategories() {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		List<Categories> categoryList = session.createQuery("from Categories c "
				+ "order by c.catid desc").list();
		return categoryList;
	}

	public Categories getCategories(int id) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		Categories category = (Categories) session.get(Categories.class, new Integer(id));
		return category;
	}

	public Categories addCategories(Categories category) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		session.persist(category);
//		trans.commit();
		return category;
	}

	public void updateCategories(Categories category) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		session.update(category);
//		trans.commit();
	}

	public void deleteCategories(int id) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		Categories p = (Categories) session.load(Categories.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
//		trans.commit();
	}

	public Categories updateCategoriesREST(Categories category) {
		Session session = this.sessionFactory.getCurrentSession();
//		Transaction trans=session.beginTransaction();
		session.update(category);
//		trans.commit();
		return category;
	}	
	//For Testing
	
	public List<Categories> getAllCategoriesTEST() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		List<Categories> categoryList = session.createQuery("from Categories c "
				+ "order by c.catid desc").list();
		return categoryList;
	}

	public Categories getCategoriesTEST(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Categories category = (Categories) session.get(Categories.class, new Integer(id));
		return category;
	}

	public Categories addCategoriesTEST(Categories category) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.persist(category);
		trans.commit();
		return category;
	}

	public void updateCategoriesTEST(Categories category) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.update(category);
		trans.commit();
	}

	public void deleteCategoriesTEST(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		Categories p = (Categories) session.load(Categories.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
		trans.commit();
	}

	public Categories updateCategoriesRESTTEST(Categories category) {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction trans=session.beginTransaction();
		session.update(category);
		trans.commit();
		return category;
	}	
}
