package org.arpit.java2blog.dao;

import java.util.List;

import org.arpit.java2blog.model.Borrowers;
import org.arpit.java2blog.model.Returndetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReturnDetailDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public List<Returndetails> getAllReturndetails() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Returndetails> returnDetailList = session.createQuery("from Returndetails rd "
				+ "order by rd.returndetailid desc").list();
		return returnDetailList;
	}

	public Returndetails getReturndetails(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Returndetails returnDetail = (Returndetails) session.get(Returndetails.class, new Integer(id));
		return returnDetail;
	}

	public Returndetails addReturndetails(Returndetails returnDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(returnDetail);
		return returnDetail;
	}

	public void updateReturndetails(Returndetails returnDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(returnDetail);
	}
	public Returndetails updateReturndetailsREST(Returndetails returnDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(returnDetail);
		return returnDetail;
	}

	public void deleteReturndetails(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Returndetails p = (Returndetails) session.load(Returndetails.class, new Integer(id));
		if (null != p) {
			session.delete(p);
		}
	}
	//get Returndetails From Returning Id
	public List<Returndetails> getReturndetailsFromReturningId(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Returndetails> returnDetailList = session.createQuery("from Returndetails rd "
				+ "where rd.returnings.returnid="+id).list();
		return returnDetailList;
	}
}
