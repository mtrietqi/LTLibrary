package org.arpit.java2blog.dao;

import java.util.ArrayList;
import java.util.List;

import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.BorrowingDetails;
import org.arpit.java2blog.model.Categories;
import org.arpit.java2blog.model.TitleCategories;
import org.arpit.java2blog.model.Titles;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BorrowingDetailDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<BorrowingDetails> getAllBorrowingDetails() {
		Session session = this.sessionFactory.getCurrentSession();
		List<BorrowingDetails> borrowingDetailList = session.createQuery("from BorrowingDetails bd "
				+ "order by bd.bordetailid desc").list();
		return borrowingDetailList;
	}

	public BorrowingDetails getBorrowingDetails(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		BorrowingDetails borrowingDetail = (BorrowingDetails) session.get(BorrowingDetails.class, new Integer(id));
		return borrowingDetail;
	}

	public BorrowingDetails addBorrowingDetails(BorrowingDetails borrowingDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(borrowingDetail);
		return borrowingDetail;
	}

	public void updateBorrowingDetails(BorrowingDetails borrowingDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(borrowingDetail);
	}
	public BorrowingDetails updateBorrowingDetailsREST(BorrowingDetails borrowingDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(borrowingDetail);
		return borrowingDetail;
	}

	public void deleteBorrowingDetails(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		BorrowingDetails p = (BorrowingDetails) session.load(BorrowingDetails.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}
	//get BorrowingDetails From Borrowing Id
	public List<BorrowingDetails> getBorrowingDetailsFromBorrowingId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<BorrowingDetails> borrowingDetailList = session.createQuery("from BorrowingDetails bd "
				+ "where bd.borrowings.borrowingid="+id).list();
		return borrowingDetailList;
//		List<BorrowingDetails> list = new ArrayList();
//		Query myQuery = session.createQuery( "from BorrowingDetails bd "
//				+ "where bd.borrowings.borrowingid="+id);
//		
//		List l = myQuery.list();
//		return list;
	}
}
