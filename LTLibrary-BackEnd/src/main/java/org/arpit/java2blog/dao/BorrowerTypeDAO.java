package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Borrowertype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BorrowerTypeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Borrowertype> getAllBorrowertype() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Borrowertype> borrowerTypeList = session.createQuery("from Borrowertype bt "
				+ "order by bt.borrowertypeid desc").list();
		return borrowerTypeList;
	}

	public Borrowertype getBorrowertype(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Borrowertype borrowerType = (Borrowertype) session.get(Borrowertype.class, new Integer(id));
		return borrowerType;
	}

	public Borrowertype addBorrowertype(Borrowertype borrowerType) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(borrowerType);
		return borrowerType;
	}

	public void updateBorrowertype(Borrowertype borrowerType) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(borrowerType);
	}
	
	public Borrowertype updateBorrowertypeREST(Borrowertype borrowerType) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(borrowerType);
		return borrowerType;
	}


	public void deleteBorrowertype(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Borrowertype p = (Borrowertype) session.load(Borrowertype.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
