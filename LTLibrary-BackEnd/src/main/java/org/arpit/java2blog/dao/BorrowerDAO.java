package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Borrowertype;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BorrowerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	public List<Borrowers> getAllBorrowers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Borrowers> borrowerList = session.createQuery("from Borrowers b "
				+ "order by b.borrowerid desc").list();
		return borrowerList;
	}

	public Borrowers getBorrowers(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Borrowers borrower = (Borrowers) session.get(Borrowers.class, new Integer(id));
		return borrower;
	}

	public Borrowers addBorrowers(Borrowers borrower) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(borrower);
		return borrower;
	}

	public void updateBorrowers(Borrowers borrower) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(borrower);
	}
	
	public Borrowers updateBorrowersREST(Borrowers borrower) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(borrower);
		return borrower;
	}

	public void deleteBorrowers(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Borrowers p = (Borrowers) session.load(Borrowers.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
