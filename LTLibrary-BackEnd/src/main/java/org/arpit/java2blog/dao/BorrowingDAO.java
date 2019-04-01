package org.arpit.java2blog.dao;


import java.util.List;

import org.arpit.java2blog.model.Borrowings;
import org.arpit.java2blog.model.Borrowings;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class BorrowingDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Borrowings> getAllBorrowings() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Borrowings> borrowingList = session.createQuery("from Borrowings b "
				+ "order by b.borrowingid desc").list();
		return borrowingList;
	}
	

	public Borrowings getBorrowings(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Borrowings borrowing = (Borrowings) session.get(Borrowings.class, new Integer(id));
		return borrowing;
	}

	public Borrowings addBorrowings(Borrowings borrowing) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(borrowing);
		return borrowing;
	}
//	public Borrowings addBorrowingsREST(Integer borrowerId, Date borDate,Integer librariansByUpdatedby,Integer librariansByCreatedby ) {
//		Session session = this.sessionFactory.getCurrentSession();
//		Borrowings borrowing = new Borrowings();
//		Borrowings borrower = new Borrowings();
//		borrower.setBorrowerid(borrowerId);
//		
//		Date createDate = new Date();
//		borrowing.setCreateddate(createDate);
//		borrowing.setUpdateddate(createDate);
//		borrowing.setBorrowings(borrower);
//		borrowing.setLibrariansByCreatedby(librariansByCreatedby);
//		borrowing.setLibrariansByUpdatedby(librariansByUpdatedby);
//		borrowing.setBordate(borDate);
//		
//		session.persist(borrowing);
//		return borrowing;
//	}
	

	public void updateBorrowings(Borrowings borrowing) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(borrowing);
	}
	
	public Borrowings updateBorrowingsREST(Borrowings borrowing) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(borrowing);
		return borrowing;
	}

	public void deleteBorrowings(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Borrowings p = (Borrowings) session.load(Borrowings.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}	
}
