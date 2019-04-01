package org.arpit.java2blog.dao;

import java.util.ArrayList;
import java.util.List;

import org.arpit.java2blog.model.AuthorNameFromTitleId;
import org.arpit.java2blog.model.Authors;
import org.arpit.java2blog.model.BookNotYetReturn;
import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Categories;
import org.arpit.java2blog.model.TitleAuthors;
import org.arpit.java2blog.model.TitleCategories;
import org.arpit.java2blog.model.Titles;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TitleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Titles> getAllTitles() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Titles> titleList = session.createQuery("from Titles t "
				+ "order by t.titleid desc").list();
		return titleList;
	}

	public Titles getTitles(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Titles title = (Titles) session.get(Titles.class, new Integer(id));
		return title;
	}

	public Titles addTitles(Titles title) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(title);
		return title;
	}

	public void updateTitles(Titles title) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(title);
	}
	
	public Titles updateTitlesREST(Titles title) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(title);
		return title;
	}

	public void deleteTitles(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Titles p = (Titles) session.load(Titles.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}
	// get Author Name From Title Id
	public List<AuthorNameFromTitleId> getAuthorNameFromTitleId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<AuthorNameFromTitleId> list = new ArrayList();
		Query myQuery = session.createQuery("select t.titleid, a.firstname,a.lastname "
				+ "from TitleAuthors ta "
				+ "inner JOIN ta.authors a "
				+ "inner JOIN ta.titles t "
				+ "where t.titleid="+id);
		
		List l = myQuery.list();
		
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			Integer titleId = (Integer)singleRowValues[0];
			String authorFirstName = (String)singleRowValues[1];
			String authorLastName = (String)singleRowValues[2];
			list.add(new AuthorNameFromTitleId(titleId, authorFirstName, authorLastName));
		}
		return list;
	}
	//get Author From Title Id
	public List<Authors> getAuthorFromTitleId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Authors> list = new ArrayList();
		Query myQuery = session.createQuery("select a.auid,a.firstname,a.lastname,a.gender,a.country "
				+ "from TitleAuthors ta "
				+ "inner JOIN ta.authors a "
				+ "inner JOIN ta.titles t "
				+ "where t.titleid="+id);
		
		List l = myQuery.list();
		
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			Integer auId = (Integer)singleRowValues[0];
			String authorFirstName = (String)singleRowValues[1];
			String authorLastName = (String)singleRowValues[2];
			Boolean authorGender = (Boolean)singleRowValues[3];
			String authorCountry = (String)singleRowValues[4];
			list.add(new Authors(auId, authorFirstName, authorLastName,authorGender,authorCountry));
		}
		return list;
	}
	
	
	//get Title Author From Title Id
	public List<TitleAuthors> getTitleAuthorFromTitleId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<TitleAuthors> list = new ArrayList();
		Query myQuery = session.createQuery("select ta.titleauthorid, ta.authors, ta.titles "
				+ "from TitleAuthors ta "
				+ "inner JOIN ta.authors a "
				+ "where ta.titles.titleid="+id);
		
		List l = myQuery.list();
		
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			Integer titleAuthorId = (Integer)singleRowValues[0];
			Authors author = (Authors)singleRowValues[1];
			Titles title = (Titles)singleRowValues[2];
			

			list.add(new TitleAuthors(titleAuthorId, author, title));
		}
		return list;
	}
	
	//get Title Categories From Title Id
	public List<TitleCategories> getTitleCategoriesFromTitleId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<TitleCategories> list = new ArrayList();
		Query myQuery = session.createQuery("select tc.titlecatid, tc.categories, tc.titles "
				+ "from TitleCategories tc "
				+ "where tc.titles.titleid="+id);
		
		List l = myQuery.list();
		
		for (int i = 0; i<l.size(); i++) {
			Object[] singleRowValues = (Object[])l.get(i);
			Integer TitleCategoryId = (Integer)singleRowValues[0];
			Categories category = (Categories)singleRowValues[1];
			Titles title = (Titles)singleRowValues[2];
			
			list.add(new TitleCategories(TitleCategoryId, category, title));
		}
		return list;
	}
}
